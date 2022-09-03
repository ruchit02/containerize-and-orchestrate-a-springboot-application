package com.topnotch.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.topnotch.demo.models.DBUser;

public interface UserRepository extends JpaRepository<DBUser, String>{

}
