package com.shopPhone.shopphoneBE.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopPhone.shopphoneBE.entity.Role;
import com.shopPhone.shopphoneBE.entity.User;
import com.shopPhone.shopphoneBE.model.LoginModel;
import com.shopPhone.shopphoneBE.model.MessageResponse;
import com.shopPhone.shopphoneBE.model.SignupRequest;
import com.shopPhone.shopphoneBE.model.loginReponse;
import com.shopPhone.shopphoneBE.repository.RoleRepository;
import com.shopPhone.shopphoneBE.repository.UserRepository;
import com.shopPhone.shopphoneBE.utilities.JwtUtil;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtil jwtUtils;

    @PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginModel loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		System.out.println();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());		

		String jwt = jwtUtils.generateJwtToken(userDetails.getUsername());
			
		return ResponseEntity.ok(new loginReponse(jwt, roles.get(0)));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		User userCheck = userRepository.findByUsername(signUpRequest.getUsername());
		if(userCheck != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("username exists!"));
		}
		Role role = roleRepository.findByName("Customer");
		user.setRole(role);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
