package com.emu.camunda.delegate;


import com.emu.camunda.feign.ProxyFeign;
import com.emu.camunda.web.rest.CamundaResource;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import org.emu.common.dto.MemberDto;
import org.emu.common.dto.MemberStatus;
import org.emu.common.status.MemberApprovalStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
    public class RollBackMemberAdapter implements JavaDelegate {


    @Autowired
    ProxyFeign proxyFeign;
    @Autowired
    CamundaResource camundaResource;
    private final Logger log = LoggerFactory.getLogger(RollBackMemberAdapter.class);
    	  @Override
    	  public void execute(DelegateExecution context) throws Exception {


    		 //
    		 Object id = (Object) context.getVariable("memberId");
    		 //
    		 log.error("Member Id=" + id);
              String idString = String.valueOf(id);
              Long convertedLong = Long.parseLong(idString);
    		 //

              MemberDto memberDto=new MemberDto();
              memberDto.setId(convertedLong);
            //status 1  for deletion
            proxyFeign.raiseMemberEvent(memberDto, MemberStatus.REJECTED);

    	  }

}
