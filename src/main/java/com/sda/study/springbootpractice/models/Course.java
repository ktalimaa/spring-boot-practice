package com.sda.study.springbootpractice.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Course model
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Course extends Auditable<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private int durationInDays;

    // when using OneToMany CascadeType.ALL
    @OneToOne(cascade = CascadeType.MERGE)
    private School school;

    private boolean isActive;

}
