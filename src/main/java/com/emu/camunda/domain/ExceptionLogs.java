package com.emu.camunda.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A ExceptionLogs.
 */
@Entity
@Table(name = "exception_logs")
public class ExceptionLogs extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exception_logs_seq")
    @SequenceGenerator(name = "exception_logs_seq",allocationSize = 1)
    private Long id;

    @Column(name = "exception_message")
    private String exceptionMessage;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public ExceptionLogs exceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        return this;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExceptionLogs)) {
            return false;
        }
        return id != null && id.equals(((ExceptionLogs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExceptionLogs{" +
            "id=" + getId() +
            ", exceptionMessage='" + getExceptionMessage() + "'" +
            "}";
    }
}
