package com.emu.camunda.service.mapper;


import com.emu.camunda.domain.*;
import com.emu.camunda.service.dto.ExceptionLogsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExceptionLogs} and its DTO {@link ExceptionLogsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExceptionLogsMapper extends EntityMapper<ExceptionLogsDTO, ExceptionLogs> {



    default ExceptionLogs fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExceptionLogs exceptionLogs = new ExceptionLogs();
        exceptionLogs.setId(id);
        return exceptionLogs;
    }
}
