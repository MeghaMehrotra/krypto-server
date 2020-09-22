package com.assignment.krypto.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.krypto.entity.Role;
import com.assignment.krypto.repo.IRoleRepository;
import com.assignment.krypto.services.interf.IRoleService;

@Service
public class RoleService implements IRoleService {

	@Autowired
	private IRoleRepository roleRepo;

	@Override
	public List<Role> getAllRoles() {
		List<Role> roles = new ArrayList<>();
		roles = roleRepo.findAll();
		return roles;
	}

}
