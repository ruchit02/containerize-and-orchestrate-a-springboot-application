package com.topnotch.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.topnotch.demo.models.Authority;
import com.topnotch.demo.models.AuthorityKey;

public interface AuthorityRepository extends JpaRepository<Authority, AuthorityKey>{

}
