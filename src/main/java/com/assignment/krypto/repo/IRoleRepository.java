package com.assignment.krypto.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.krypto.entity.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer>{

}