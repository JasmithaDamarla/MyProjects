package com.epam.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.customexceptions.FunctionalityException;
import com.epam.customexceptions.InvalidInputException;
import com.epam.customexceptions.SignUpFailedException;
import com.epam.customexceptions.UserNotFoundException;
import com.epam.dto.UserDTO;
import com.epam.entities.User;
import com.epam.service.interafaces.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
@Slf4j
public class UserRestApi {

	@Autowired
	private UserService userService;
	
	@PostMapping("users")
	public ResponseEntity<String> signUpUser(@Valid @RequestBody UserDTO userDto) throws SignUpFailedException {
		UserDTO newUser = userService.signUpUser(userDto);
		log.info("user is created successfully");
		String str = "User with userName " + newUser.getUserName() + " has created successfully";
		return new ResponseEntity<>(str, HttpStatus.CREATED);
		
	}

	@PutMapping("users/{id}")
	public ResponseEntity<UserDTO> updateUserScoreById(@PathVariable int id,@Valid @RequestBody UserDTO userDto) throws FunctionalityException, UserNotFoundException {
		userDto.setUserId(id);
		UserDTO user = userService.update(userDto);
		log.info("User updation done");
		return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
	}

	@GetMapping("users")
	public List<User> getAllUsers() {
		return (List<User>) userService.getUsers();
	}

	@GetMapping("users/userName/{userName}")
	public ResponseEntity<UserDTO> viewUserByName(@PathVariable String userName) throws UserNotFoundException {
		UserDTO user = userService.viewUserByUserName(userName);
		log.info("user of username {} is retrieved",user);
		return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
	}

	@PostMapping("users/{username}")
	public ResponseEntity<String> userValid(@PathVariable String username, @Valid @RequestBody UserDTO userDto) throws UserNotFoundException {
		userService.isValidUser(userDto.getUserName(), userDto.getPassword());
		log.info("user is valid and getting loggedin");
		return new ResponseEntity<>("You have logged in successfully!!", HttpStatus.ACCEPTED);
			
	}

	@PostMapping("admins")
	public ResponseEntity<String> adminValid(@RequestBody UserDTO adminDto) throws InvalidInputException {
		userService.isValidAdmin(adminDto.getUserName(), adminDto.getPassword());
		log.info("admin is valid and getting loggedin");
		return new ResponseEntity<>("You have logged in successfully!!", HttpStatus.ACCEPTED);
			
	}
}
