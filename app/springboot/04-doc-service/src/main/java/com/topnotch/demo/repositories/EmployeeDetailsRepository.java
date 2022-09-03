package com.topnotch.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.topnotch.demo.models.EmployeeDetails;

public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long> {
	EmployeeDetails findByEmail(String email);
}
