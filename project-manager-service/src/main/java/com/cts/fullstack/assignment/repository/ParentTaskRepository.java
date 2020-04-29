package com.cts.fullstack.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.fullstack.assignment.entities.ParentTask;

@Repository
public interface ParentTaskRepository extends JpaRepository<ParentTask, Integer> {

}
