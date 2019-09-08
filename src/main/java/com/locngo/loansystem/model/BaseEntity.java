package com.locngo.loansystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 9064411665738028473L;

    @CreatedDate
    @JsonProperty("created_date")
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @CreatedBy
    @JsonProperty("created_by")
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedDate
    @JsonProperty("modified_date")
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @LastModifiedBy
    @JsonProperty("modified_by")
    @Column(name = "modified_by")
    private String modifiedBy;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
