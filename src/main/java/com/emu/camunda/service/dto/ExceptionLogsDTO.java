package com.emu.camunda.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.emu.camunda.domain.ExceptionLogs} entity.
 */
public class ExceptionLogsDTO implements Serializable {
    
    private Long id;

    private String exceptionMessage;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExceptionLogsDTO)) {
            return false;
        }

        return id != null && id.equals(((ExceptionLogsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExceptionLogsDTO{" +
            "id=" + getId() +
            ", exceptionMessage='" + getExceptionMessage() + "'" +
            "}";
    }
}
