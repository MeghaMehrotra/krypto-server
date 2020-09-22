package com.assignment.krypto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.krypto.entity.UserProfile;
import com.assignment.krypto.repo.IManagerRepository;
import com.assignment.krypto.services.interf.IManagerService;


@Service
public class ManagerService implements IManagerService {

	@Autowired
	private IManagerRepository managerRepo;

	@Override
	public UserProfile save(UserProfile manager) {
		return managerRepo.save(manager);
	}

	@Override
	public UserProfile update(UserProfile manager) {
		return managerRepo.save(manager);
	}

	@Override
	public void delete(Long managerId) {
		if (managerId != null) {
			UserProfile manager = managerRepo.getOne(managerId);
			managerRepo.delete(manager);

		}
	}

	@Override
	public UserProfile getManagerByEmail(String email) {
		UserProfile userProfile = managerRepo.findByUsername(email);
		System.out.println(userProfile);
		return userProfile;

	}

	@Override
	public UserProfile getManager(Long id) {
		return managerRepo.getOne(id);
	}

	@Override
	public List<UserProfile> getAllUsers() {
		return managerRepo.findAll();
	}

}
