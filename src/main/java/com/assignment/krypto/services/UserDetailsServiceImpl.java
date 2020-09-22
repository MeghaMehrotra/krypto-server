package com.assignment.krypto.services;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.assignment.krypto.entity.UserProfile;
import com.assignment.krypto.repo.IManagerRepository;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IManagerRepository managerRepo;

	@Override
	public UserDetails loadUserByUsername(String username) {
		UserProfile manager = managerRepo.findByUsername(username);
		if (manager == null) {
			throw new UsernameNotFoundException(username);
		}
		return new org.springframework.security.core.userdetails.User(username, manager.getPassword(), true, true, true,
				true, getAuthorities("ROLE_USER"));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}
}
