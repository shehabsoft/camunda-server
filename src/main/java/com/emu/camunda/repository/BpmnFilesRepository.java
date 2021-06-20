package com.emu.camunda.repository;

import com.emu.camunda.domain.BpmnFiles;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BpmnFiles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BpmnFilesRepository extends JpaRepository<BpmnFiles, Long> {
}
