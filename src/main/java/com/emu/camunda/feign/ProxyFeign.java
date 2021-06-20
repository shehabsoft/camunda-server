package com.emu.camunda.feign;

import com.emu.camunda.client.AuthorizedFeignClient;


import org.emu.common.dto.MemberDto;
import org.emu.common.dto.MemberStatus;
import org.emu.common.status.MemberApprovalStatus;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Copyright 2021-2022 By Dirac Systems.
 *
 * Created by khalid.nouh on 17/3/2021.
 */

@AuthorizedFeignClient(name = "bpmProxy")
public interface ProxyFeign {


       @PostMapping("/api/raiseMemberApprovalEvent/{status}")
       void raiseMemberApprovalEvent(@RequestBody MemberDto member, @PathVariable("status") MemberApprovalStatus status );

       @PostMapping("/api/raiseMemberEvent/{status}")
       void raiseMemberEvent(@RequestBody MemberDto member, @PathVariable("status") MemberStatus status );
       @PostMapping("/api/raiseNotificationEvent/{processInstanceId}")
       void raiseNotificationEvent( @RequestBody MemberDto member , @PathVariable("processInstanceId") String processInstanceId );

        @PostMapping("/api/raiseValidationEvent")
       void raiseValidationEvent( @RequestBody MemberDto member );


}
