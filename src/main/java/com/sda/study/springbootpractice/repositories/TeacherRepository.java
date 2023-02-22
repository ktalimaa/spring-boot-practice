package com.sda.study.springbootpractice.repositories;

import com.sda.study.springbootpractice.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to handle all Teacher related DB operations
 * @author Kristel Talimaa
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
