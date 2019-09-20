package com.cg.trainingsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.trainingsite.entity.Courses;

public interface CoursesDao extends JpaRepository<Courses, Integer> {

}
