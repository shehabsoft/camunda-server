package com.emu.camunda.repository;

import com.emu.camunda.domain.ExceptionLogs;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExceptionLogs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExceptionLogsRepository extends JpaRepository<ExceptionLogs, Long> {
}
