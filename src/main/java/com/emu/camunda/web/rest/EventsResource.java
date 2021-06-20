package com.emu.camunda.web.rest;

import com.emu.camunda.utilities.CamundaUtils;
import lombok.extern.log4j.Log4j2;
import org.emu.common.events.GenericEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


/**
 * Copyright 2021-2022 By Dirac Systems.
 * <p>
 * Created by khalid.nouh on 4/4/2021.
 */
@Log4j2
@RestController
@RequestMapping("/api/process/")
public class EventsResource {

    @Autowired
    CamundaUtils camundaUtils;
    @Value("${bpm.token}")
    private String bpmToken;

    @PostMapping("/startProcessByMessageEvent/{token}")
    public void startProcessByMessageEvent(@RequestBody GenericEvent genericEvent, @PathVariable("token")String token) {
        if (bpmToken.equals(token)){
            camundaUtils.startProcessByMessageEvent(genericEvent);
        }
        else
            System.out.println("Invalid token, you are not authorized");
    }



}
