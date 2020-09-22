package com.assignment.krypto.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.krypto.entity.UserProfile;

@Repository
public interface IManagerRepository extends JpaRepository<UserProfile, Long> {

	UserProfile findByUsername(String username);

}
