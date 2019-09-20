package com.cg.trainingsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.trainingsite.entity.Administrator;

public interface AdministratorDao extends JpaRepository<Administrator, Integer> {

}
