package com.emu.camunda.service.mapper;


import com.emu.camunda.domain.*;
import com.emu.camunda.service.dto.BpmnFilesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BpmnFiles} and its DTO {@link BpmnFilesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BpmnFilesMapper extends EntityMapper<BpmnFilesDTO, BpmnFiles> {



    default BpmnFiles fromId(Long id) {
        if (id == null) {
            return null;
        }
        BpmnFiles bpmnFiles = new BpmnFiles();
        bpmnFiles.setId(id);
        return bpmnFiles;
    }
}
