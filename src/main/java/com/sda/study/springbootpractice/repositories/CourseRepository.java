package com.sda.study.springbootpractice.repositories;

import com.sda.study.springbootpractice.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to handle all Course related DB operations
 * @author Kristel Talimaa
 */

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
