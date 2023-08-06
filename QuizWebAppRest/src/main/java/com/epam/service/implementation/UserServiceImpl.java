package com.epam.service.implementation;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.customexceptions.InvalidInputException;
import com.epam.customexceptions.SignUpFailedException;
import com.epam.customexceptions.UserNotFoundException;
import com.epam.dto.UserDTO;
import com.epam.entities.Role;
import com.epam.entities.User;
import com.epam.repository.UserRepository;
import com.epam.service.interafaces.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public UserDTO signUpUser(UserDTO newUserDto) throws SignUpFailedException {
		newUserDto.setRoles(List.of(new Role("USER")));
		log.info("user is getting created");
		return Optional.of(modelMapper.map(newUserDto, User.class)).map(userRepository::save).map(user -> newUserDto)
				.orElseThrow(() -> new SignUpFailedException("Unable to create user"));
	}

	@Override
	public UserDTO signUpAdmin(UserDTO newAdminDto) throws SignUpFailedException {
		newAdminDto.setRoles(List.of(new Role("ADMIN")));
		log.info("admin is getting created");
		return Optional.of(modelMapper.map(newAdminDto, User.class)).map(userRepository::save)
				.map(admin -> newAdminDto).orElseThrow(() -> new SignUpFailedException("Unable to create admin"));
	}

	@Override
	public UserDTO update(UserDTO updateUserDto) throws UserNotFoundException {
		log.info("user is getting updated for score");
		return Optional.of(modelMapper.map(updateUserDto, User.class)).map(userRepository::save)
				.map(user -> updateUserDto).orElseThrow(() -> new UserNotFoundException("Unable to update user"));
	}

	@Override
	public UserDTO viewUserById(Long id) throws UserNotFoundException {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("No user found with id " + id));
		log.info("user of id {} is {}",id,user);
		return modelMapper.map(user, UserDTO.class);
	}

	@Override
	public UserDTO viewUserByUserName(String userName) throws UserNotFoundException {
		User user = userRepository.findByUsername(userName)
				.orElseThrow(() -> new UserNotFoundException("No user found of username = " + userName));
		log.info("user getting viewd by username {}",userName);
		return modelMapper.map(user, UserDTO.class);
	}

	@Override
	public List<User> getUsers() {
		log.info("Fetching all users");
		return userRepository.findAll();
	}

	@Override
	public boolean isValidAdmin(String userName, String password) throws InvalidInputException {
		log.info("user is getting verified");
		return userRepository.findByUsername(userName).map(admin -> (admin.getPassword().equals(password)))
				.orElseThrow(() -> new InvalidInputException("No admin found"));
	}

	@Override
	public boolean isValidUser(String userName, String password) throws UserNotFoundException {
		log.info("admin is getting verified");
		return userRepository.findByUsername(userName).map(checkUser -> checkUser.getPassword().equals(password))
				.orElseThrow(() -> new UserNotFoundException("No user found"));

	}

}