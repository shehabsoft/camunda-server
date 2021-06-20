package com.emu.camunda.service;

import com.emu.camunda.service.dto.BpmnFilesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.emu.camunda.domain.BpmnFiles}.
 */
public interface BpmnFilesService {

    /**
     * Save a bpmnFiles.
     *
     * @param bpmnFilesDTO the entity to save.
     * @return the persisted entity.
     */
    BpmnFilesDTO save(BpmnFilesDTO bpmnFilesDTO);

    /**
     * Get all the bpmnFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BpmnFilesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" bpmnFiles.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BpmnFilesDTO> findOne(Long id);

    /**
     * Delete the "id" bpmnFiles.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
