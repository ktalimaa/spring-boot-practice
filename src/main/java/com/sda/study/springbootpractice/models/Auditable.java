package com.sda.study.springbootpractice.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Auditable model
 * This is concept has automatic field creation using hibernate, date etc.
 * These four fields will be filled automatically by Spring
 *
 * @implNote This model can be extended to any entity which needs auditing
 */

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<V> {
    @CreatedBy
    @JsonIgnore
    @Column(updatable = false)
    protected V createdBy;

    @CreatedDate
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    protected LocalDateTime createDate;

    @LastModifiedBy
    @JsonIgnore
    protected V lastModifiedBy;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    // there is no colum updated false, because last modified time can be changed
    protected LocalDateTime lastModifiedDate;

}
