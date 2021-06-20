package com.emu.camunda.utilities;

import com.emu.camunda.service.dto.OperationsLogsDTO;

/**
 * Copyright 2021-2022 By Dirac Systems.
 *
 * Created by {@khalid.nouh on 18/3/2021}.
 */
public class LoggingUtilities {
    static OperationsLogsDTO operationsLogsDTO=null;
    public static OperationsLogsDTO createOperationsLogsDTO( String processName,String logDetails){
        operationsLogsDTO=new OperationsLogsDTO();
        operationsLogsDTO.setOperationName(processName);
        operationsLogsDTO.setLogDetails(logDetails);
        return operationsLogsDTO;
    }

}
