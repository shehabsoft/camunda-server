package com.emu.camunda.delegate;


import com.emu.camunda.feign.ProxyFeign;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.emu.common.dto.MemberDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendNotificationAdapter implements JavaDelegate {

    @Autowired
    ProxyFeign proxyFeign;

     private final Logger log = LoggerFactory.getLogger(SendNotificationAdapter.class);

     @Override
     public void execute(DelegateExecution context) throws Exception {
   	//
         Object id = (Object) context.getVariable("memberId");
         //
         log.error("Member Id=" + id);
         String idString = String.valueOf(id);
         Long convertedLong = Long.parseLong(idString);
   	//
     	log.error("Member Id=" + convertedLong);
         MemberDto memberDto=new MemberDto();
         String traceId = context.getProcessBusinessKey();
         String processInstanceId=context.getProcessInstanceId();
         System.out.println("processInstanceId--------"+processInstanceId);

         memberDto.setId(convertedLong);


         proxyFeign.raiseNotificationEvent(memberDto,processInstanceId);

     }

}
