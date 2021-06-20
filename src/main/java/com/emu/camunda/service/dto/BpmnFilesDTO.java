package com.emu.camunda.service.dto;

import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.emu.camunda.domain.BpmnFiles} entity.
 */
public class BpmnFilesDTO implements Serializable {
    
    private Long id;

    private String fileName;

    private String fileContentType;

    @Lob
    private byte[] fileContent;

    private String fileContentContentType;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileContentContentType() {
        return fileContentContentType;
    }

    public void setFileContentContentType(String fileContentContentType) {
        this.fileContentContentType = fileContentContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BpmnFilesDTO)) {
            return false;
        }

        return id != null && id.equals(((BpmnFilesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BpmnFilesDTO{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            ", fileContent='" + getFileContent() + "'" +
            "}";
    }
}
