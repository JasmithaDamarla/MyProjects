package com.epam.service.interafaces;

import com.epam.customexceptions.FunctionalityException;
import com.epam.customexceptions.InvalidInputException;
import com.epam.customexceptions.SignUpFailedException;
import com.epam.customexceptions.UserNotFoundException;
import com.epam.dto.UserDTO;
import com.epam.entities.User;

public interface UserService {
	UserDTO signUpUser(UserDTO user) throws SignUpFailedException;
	UserDTO update(UserDTO updateUser) throws FunctionalityException, UserNotFoundException;
	UserDTO viewUserById(Long id) throws UserNotFoundException;
	Iterable<User> getUsers();
	boolean isValidAdmin(String user, String password) throws InvalidInputException;
	boolean isValidUser(String userName, String password) throws UserNotFoundException;
	UserDTO viewUserByUserName(String userName) throws UserNotFoundException;
	UserDTO signUpAdmin(UserDTO newAdminDto) throws SignUpFailedException;
}
