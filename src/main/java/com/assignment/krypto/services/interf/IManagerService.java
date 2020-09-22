package com.assignment.krypto.services.interf;

import java.util.List;

import com.assignment.krypto.entity.UserProfile;

public interface IManagerService {
	
     UserProfile save(UserProfile manager);
     
     UserProfile update(UserProfile manager);
     
     void delete(Long managerId);

	UserProfile getManagerByEmail(String email);
	
	UserProfile getManager(Long id);

	List<UserProfile> getAllUsers();
}
