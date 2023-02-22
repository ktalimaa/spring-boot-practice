package com.sda.study.springbootpractice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * School model
 */

@Entity
@Data
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
