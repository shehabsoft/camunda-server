package com.emu.camunda.service.impl;

import com.emu.camunda.service.OperationsLogsService;
import com.emu.camunda.domain.OperationsLogs;
import com.emu.camunda.repository.OperationsLogsRepository;
import com.emu.camunda.service.dto.OperationsLogsDTO;
import com.emu.camunda.service.mapper.OperationsLogsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OperationsLogs}.
 */
@Service
@Transactional
public class OperationsLogsServiceImpl implements OperationsLogsService {

    private final Logger log = LoggerFactory.getLogger(OperationsLogsServiceImpl.class);

    private final OperationsLogsRepository operationsLogsRepository;

    private final OperationsLogsMapper operationsLogsMapper;

    public OperationsLogsServiceImpl(OperationsLogsRepository operationsLogsRepository, OperationsLogsMapper operationsLogsMapper) {
        this.operationsLogsRepository = operationsLogsRepository;
        this.operationsLogsMapper = operationsLogsMapper;
    }

    @Override
    public OperationsLogsDTO save(OperationsLogsDTO operationsLogsDTO) {
        log.debug("Request to save OperationsLogs : {}", operationsLogsDTO);
        OperationsLogs operationsLogs = operationsLogsMapper.toEntity(operationsLogsDTO);
        operationsLogs = operationsLogsRepository.save(operationsLogs);
        return operationsLogsMapper.toDto(operationsLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OperationsLogsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OperationsLogs");
        return operationsLogsRepository.findAll(pageable)
            .map(operationsLogsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OperationsLogsDTO> findOne(Long id) {
        log.debug("Request to get OperationsLogs : {}", id);
        return operationsLogsRepository.findById(id)
            .map(operationsLogsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OperationsLogs : {}", id);
        operationsLogsRepository.deleteById(id);
    }
}
