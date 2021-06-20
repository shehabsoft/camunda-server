package com.emu.camunda.service.impl;

import com.emu.camunda.service.BpmnFilesService;
import com.emu.camunda.domain.BpmnFiles;
import com.emu.camunda.repository.BpmnFilesRepository;
import com.emu.camunda.service.dto.BpmnFilesDTO;
import com.emu.camunda.service.mapper.BpmnFilesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BpmnFiles}.
 */
@Service
@Transactional
public class BpmnFilesServiceImpl implements BpmnFilesService {

    private final Logger log = LoggerFactory.getLogger(BpmnFilesServiceImpl.class);

    private final BpmnFilesRepository bpmnFilesRepository;

    private final BpmnFilesMapper bpmnFilesMapper;

    public BpmnFilesServiceImpl(BpmnFilesRepository bpmnFilesRepository, BpmnFilesMapper bpmnFilesMapper) {
        this.bpmnFilesRepository = bpmnFilesRepository;
        this.bpmnFilesMapper = bpmnFilesMapper;
    }

    @Override
    public BpmnFilesDTO save(BpmnFilesDTO bpmnFilesDTO) {
        log.debug("Request to save BpmnFiles : {}", bpmnFilesDTO);
        BpmnFiles bpmnFiles = bpmnFilesMapper.toEntity(bpmnFilesDTO);
        bpmnFiles = bpmnFilesRepository.save(bpmnFiles);
        return bpmnFilesMapper.toDto(bpmnFiles);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BpmnFilesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BpmnFiles");
        return bpmnFilesRepository.findAll(pageable)
            .map(bpmnFilesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BpmnFilesDTO> findOne(Long id) {
        log.debug("Request to get BpmnFiles : {}", id);
        return bpmnFilesRepository.findById(id)
            .map(bpmnFilesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BpmnFiles : {}", id);
        bpmnFilesRepository.deleteById(id);
    }
}
