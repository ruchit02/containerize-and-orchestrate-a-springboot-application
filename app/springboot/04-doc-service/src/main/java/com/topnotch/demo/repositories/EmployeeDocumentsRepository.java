package com.topnotch.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.topnotch.demo.models.EmployeeDocuments;

public interface EmployeeDocumentsRepository extends JpaRepository<EmployeeDocuments, Long> {

}
