package com.assignment.krypto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.krypto.config.JwtUtils;
import com.assignment.krypto.dto.MessageDTO;
import com.assignment.krypto.entity.UserProfile;
import com.assignment.krypto.exception.custom.MindbowserMessageException;
import com.assignment.krypto.services.interf.IManagerService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	IManagerService managerService;

	@PostMapping("/signup")
	public ResponseEntity<MessageDTO<UserProfile>> signup(@RequestBody UserProfile manager)
			throws MindbowserMessageException {
		if (manager != null && !manager.getUsername().isEmpty()) {
			if (!checkEmailExists(manager.getUsername())) {

				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = passwordEncoder.encode(manager.getPassword());
				manager.setPassword(hashedPassword);
				manager = managerService.save(manager);
				return new ResponseEntity<>(new MessageDTO<>(" User registration success!!", manager, true),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(new MessageDTO<>("User with email alteady exists", null, false),
						HttpStatus.OK);

			}
		}
		return new ResponseEntity<>(new MessageDTO<>(" Enter valid manager details!!", null, false), HttpStatus.OK);
	}

	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserProfile manager = managerService.getManagerByEmail(authentication.getName());
		return ResponseEntity
				.ok(new JwtResponse(jwt, manager.getId(), manager.getUsername(), manager.getFirstName(), "Bearer"));
	}

	public boolean checkEmailExists(String email) throws MindbowserMessageException {
		if (!email.isEmpty()) {
			UserProfile manager;
			manager = managerService.getManagerByEmail(email);
			if (manager != null)
				return true;
			else
				return false;
		}
		throw new MindbowserMessageException("Empty Email.Please enter email", HttpStatus.OK);
	}

	@GetMapping("/manager/{email}")
	public ResponseEntity<MessageDTO<UserProfile>> getManagerDetails(@PathVariable("email") String email) {
		UserProfile manager = null;
		if (email != null) {
			manager = managerService.getManagerByEmail(email);
		}
		if (manager != null) {
			return new ResponseEntity<>(new MessageDTO<>(" Manager Details Fetch success", manager, true),
					HttpStatus.OK);

		}
		return new ResponseEntity<>(new MessageDTO<>(" Manager not found with username", manager, false),
				HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<UserProfile>> getEmployeesByManager() {
		List<UserProfile> users = managerService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PutMapping("/profile")
	public ResponseEntity<MessageDTO<UserProfile>> updateProfile(@RequestBody UserProfile userProfile) {
		UserProfile userProfil = managerService.getManager(userProfile.getId());
		userProfil.setFirstName(userProfile.getFirstName());
		userProfil.setLastName(userProfile.getLastName());
		userProfil.setCompany(userProfile.getCompany());
		return new ResponseEntity<>(new MessageDTO<>("User updated successfuly ", userProfil, true), HttpStatus.OK);

	}
}
