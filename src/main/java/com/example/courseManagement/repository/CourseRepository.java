package com.example.courseManagement.repository;

import com.example.courseManagement.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByInstructor(String instructor);
}
