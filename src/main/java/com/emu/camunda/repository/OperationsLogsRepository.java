package com.emu.camunda.repository;

import com.emu.camunda.domain.OperationsLogs;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OperationsLogs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationsLogsRepository extends JpaRepository<OperationsLogs, Long> {
}
