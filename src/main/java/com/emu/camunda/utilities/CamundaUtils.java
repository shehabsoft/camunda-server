package com.emu.camunda.utilities;

import com.emu.camunda.feign.ProxyFeign;
import com.emu.camunda.service.BpmnFilesService;
import com.emu.camunda.service.OperationsLogsService;

import com.emu.camunda.service.dto.BpmnFilesDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.VariableMap;
import org.emu.common.dto.*;
import org.emu.common.dto.bpm.DeploymentDto;
import org.emu.common.dto.bpm.EVENTSTYPES;
import org.emu.common.dto.bpm.TaskDto;
import org.emu.common.events.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CamundaUtils {
    @Autowired
    ProcessEngine processEngine;
    @Autowired
    ProxyFeign proxyFeign;
    @Autowired
    OperationsLogsService operationsLogsService;
    @Value("${bpm.token}")
    private String bpmToken;
    @Autowired
    BpmnFilesService bpmnFilesService;
    @Autowired
    private ObjectMapper objectMapper;

    public DeploymentDto createDeployment(String deploymentName, MultipartFile multipartFile) throws IOException {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name(deploymentName);
        deploymentBuilder.addInputStream(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        Deployment deployment = deploymentBuilder.deploy();
        DeploymentDto deploymentDto = new DeploymentDto();
        if (deployment.getId() != null && !deployment.getId().equals("")) {
            BpmnFilesDTO bpmnFilesDTO = new BpmnFilesDTO();

            operationsLogsService.save(LoggingUtilities.createOperationsLogsDTO("@PostMapping(path = /createDeployment/{deploymentName},consumes = multipart/form-data)", "Saving a deployment File In the db"));
            bpmnFilesDTO.setFileContent(multipartFile.getBytes());
            bpmnFilesDTO.setFileName(multipartFile.getOriginalFilename());
            bpmnFilesDTO.setFileContentContentType(multipartFile.getContentType());
            bpmnFilesService.save(bpmnFilesDTO);

            deploymentDto.setId(deployment.getId());
            deploymentDto.setName(deployment.getName());
            deploymentDto.setDeploymentTime(deployment.getDeploymentTime().toString());
            System.out.println("deployment: " + deployment);
            return deploymentDto;
        } else {

            return null;
        }
    }

    public VariableMap CompletesATaskAndUpdatesProcessVariables(String taskId, String executionId, Map<String, Object> variables) {
        processEngine.getRuntimeService().setVariables(executionId, variables);
        VariableMap variableMap = processEngine.getTaskService().completeWithVariablesInReturn(taskId, variables, false);
        return variableMap;
    }

    public void startProcessByMessageEvent(GenericEvent genericEvent) {
        switch (genericEvent.getEventType()) {
            case EVENTSTYPES.MEMBER_EVENT:

                MemberDto memberDto2 = objectMapper.convertValue(genericEvent.getGenericDto(), MemberDto.class);
                MemberEvent memberEvent = objectMapper.convertValue(genericEvent, MemberEvent.class);
                System.out.println(memberEvent.getEventType());
                MessageCorrelationBuilder messageCorrelation = processEngine.getRuntimeService().createMessageCorrelation(genericEvent.getMessageName());
                messageCorrelation.setVariable("memberId", memberDto2.getId());
                if (genericEvent.getProcessInstanceId() != null) {
                    messageCorrelation.processInstanceId(genericEvent.getProcessInstanceId());
                }
                if (genericEvent.getVariables() != null) {
                    messageCorrelation.setVariable("eventVariables", genericEvent.getVariables());
                }

                MessageCorrelationResult messageCorrelationResult = messageCorrelation.correlateWithResult();
                break;
            case EVENTSTYPES.NOTIFICATION_EVENT:
                NotifactionDto notifactionDto = objectMapper.convertValue(genericEvent.getGenericDto(), NotifactionDto.class);
                NotificationEvent notificationEvent = objectMapper.convertValue(genericEvent, NotificationEvent.class);
                MessageCorrelationBuilder messageCorrelation2 = processEngine.getRuntimeService().createMessageCorrelation(genericEvent.getMessageName());
                if (genericEvent.getProcessInstanceId() != null) {
                    messageCorrelation2.processInstanceId(genericEvent.getProcessInstanceId());
                }
                if (genericEvent.getVariables() != null) {
                    messageCorrelation2.setVariable("eventVariables", genericEvent.getVariables());
                    messageCorrelation2.setVariable("notificationSent",genericEvent.getVariables().get("notificationSent"));
                }
                System.out.println("Process Id :"+genericEvent.getProcessInstanceId());
                messageCorrelation2.correlateWithResult();


        }

    }


    public List<org.emu.common.dto.bpm.ProcessInstance> getAllRunningProcessInstances(String processDefinitionName) {
        RuntimeService runtimeService=processEngine.getRuntimeService();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // query for latest process definition with given name
        ProcessDefinition myProcessDefinition =
            repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionName)
                .latestVersion()
                .singleResult();

        // list all running/unsuspended instances of the process

       List<ProcessInstance> instances = runtimeService.createProcessInstanceQuery()
            .processDefinitionId(myProcessDefinition.getId())
            .active() // we only want the unsuspended process instances
            .list();
        return convertList(instances);
    }

    public List<org.emu.common.dto.bpm.ProcessInstance> convertList(List<ProcessInstance> instances){
        List<org.emu.common.dto.bpm.ProcessInstance> processInstances=new ArrayList<>();
        org.emu.common.dto.bpm.ProcessInstance processTemp=null;
        for (ProcessInstance processInstance:instances){
            processTemp=new org.emu.common.dto.bpm.ProcessInstance();
            processTemp.setId(processInstance.getId());
            processTemp.setDefinitionId(processInstance.getProcessDefinitionId());
            processTemp.setBusinessKey(processInstance.getBusinessKey());
            processTemp.setTenantId(processInstance.getTenantId());
            processInstances.add(processTemp);
        }
        return processInstances;
    }

    public List<TaskDto> getTasksByAssigne(String assignee) {
        List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee(assignee).list();
        TaskDto taskDto=null;
        List<TaskDto>taskDtosList=new ArrayList<>();
        for(Task task:list) {
            taskDto = new TaskDto();
            taskDto.setId(task.getId());
            taskDto.setName(task.getName());
            taskDto.setExecutionId(task.getExecutionId());
            taskDto.setCreated(task.getCreateTime().toString());
            taskDto.setProcessDefinitionId(task.getProcessDefinitionId());
            taskDto.setOwner(task.getOwner());
            taskDto.setProcessInstanceId(task.getProcessInstanceId());
            taskDto.setTenantId(task.getTenantId());
            taskDtosList.add(taskDto);
        }
        return taskDtosList;

    }

    public List<org.emu.common.dto.bpm.VariableInstance> getTaskvariablesByExecutionId(String executionId){
        List<VariableInstance> list = processEngine.getRuntimeService().createVariableInstanceQuery().executionIdIn(executionId).list();
        List<org.emu.common.dto.bpm.VariableInstance> variableInstanceList=new ArrayList<>();
        org.emu.common.dto.bpm.VariableInstance variableInstance;
        for(VariableInstance tempVariableInstance:list){
            variableInstance =new org.emu.common.dto.bpm.VariableInstance();
            variableInstance.setName(tempVariableInstance.getName());
            variableInstance.setValue(tempVariableInstance.getValue());
            variableInstance.setVariableId(tempVariableInstance.getId());
            variableInstance.setTenantId(tempVariableInstance.getTenantId());
            variableInstance.setTaskId(tempVariableInstance.getTaskId());
            variableInstance.setCaseInstanceId(tempVariableInstance.getCaseInstanceId());
            variableInstance.setErrorMessage(tempVariableInstance.getErrorMessage());
            variableInstanceList.add(variableInstance);
        }
        return variableInstanceList;
    }
}
