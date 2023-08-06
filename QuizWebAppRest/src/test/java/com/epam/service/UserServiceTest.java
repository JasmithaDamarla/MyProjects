package com.epam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.epam.customexceptions.FunctionalityException;
import com.epam.customexceptions.InvalidInputException;
import com.epam.customexceptions.SignUpFailedException;
import com.epam.customexceptions.UserNotFoundException;
import com.epam.dto.QuestionDTO;
import com.epam.dto.QuizDTO;
import com.epam.dto.UserDTO;
import com.epam.entities.Question;
import com.epam.entities.Quiz;
import com.epam.entities.Role;
import com.epam.entities.User;
import com.epam.repository.AdminRepository;
import com.epam.repository.UserRepository;
import com.epam.service.implementation.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private AdminRepository adminRepository;
	@Mock
	private ModelMapper modelMapper;
	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Test
	void signUpUserSuccess() throws SignUpFailedException {
		UserDTO newUser = new UserDTO("137", "epam", null, 0);
		User user = new User("137", "epam", List.of(new Role("USER")),null, 0);
		user.setUserId(1);
		Mockito.when(modelMapper.map(newUser, User.class)).thenReturn(user);
		Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
		UserDTO actual = userServiceImpl.signUpUser(newUser);
		assertEquals(newUser, actual);
	}

	@Test
	void signUpUserFail() throws SignUpFailedException {
		UserDTO dto = new UserDTO();
		User user = new User();
		Mockito.when(modelMapper.map(dto, User.class)).thenReturn(user);
		Mockito.when(userRepository.save(user)).thenReturn(null);
		assertThrows(SignUpFailedException.class, () -> {
			userServiceImpl.signUpUser(dto);
		});
	}
	
	@Test
	void signUpAdminFail() throws SignUpFailedException {
		UserDTO dto = new UserDTO();
		User admin = new User();
		Mockito.when(modelMapper.map(dto, User.class)).thenReturn(admin);
		Mockito.when(userRepository.save(admin)).thenReturn(null);
		assertThrows(SignUpFailedException.class, () -> {
			userServiceImpl.signUpAdmin(dto);
		});
	}
	
	@Test
	void signUpAdminSuccess() throws SignUpFailedException {
		UserDTO newAdmin = new UserDTO();
		newAdmin.setUserName("137");
		newAdmin.setPassword("epam");
		User admin = new User();
		admin.setUsername("137");
		admin.setPassword("epam");
		Mockito.when(modelMapper.map(newAdmin, User.class)).thenReturn(admin);
		Mockito.when(userRepository.save(any(User.class))).thenReturn(admin);
		UserDTO actual = userServiceImpl.signUpAdmin(newAdmin);
		assertEquals(newAdmin, actual);
	}
	

	@Test
	void updateSuccess() throws UserNotFoundException {
		List<QuestionDTO> list = new ArrayList<>();
		list.add(new QuestionDTO());
		list.add(new QuestionDTO());
		List<Question> enlist = new ArrayList<>();
		enlist.add(new Question());
		enlist.add(new Question());
		
		QuizDTO quizDto = new QuizDTO("Java I",list,10);
		quizDto.setQuizId(1);
		Quiz quiz = new Quiz("Java I",enlist,10);
		quiz.setQuizId(1);
		UserDTO dto = new UserDTO("userName", "pass", quizDto, 10);
		dto.setUserId(10);
		Optional<User> user = Optional.ofNullable(new User("userName","pass",List.of(new Role("USER")),quiz,10));
		Mockito.when(modelMapper.map(dto, User.class)).thenReturn(user.get());
		Mockito.when(userRepository.save(user.get())).thenReturn(user.get());
		UserDTO actual = userServiceImpl.update(dto);
		assertEquals(dto, actual);
	}

	@Test
	void updateUserNotFound() throws FunctionalityException, UserNotFoundException  {
		UserDTO updateUser = new UserDTO("137", "epam", null, 0);
		updateUser.setUserId(1);
		Optional<User> user = Optional.ofNullable(new User("137", "epam",List.of(new Role("USER")), null, 0));
		Mockito.when(modelMapper.map(updateUser, User.class)).thenReturn(user.get());
		Mockito.when(userRepository.save(any(User.class))).thenReturn(null);
		assertThrows(UserNotFoundException.class,()->{
			userServiceImpl.update(updateUser);
		});
	}
	
//	@Test
//	void updateFail() throws FunctionalityException {
//		UserDTO dto = new UserDTO("137", "epam", null, 0);
//		dto.setUserId(1);
//		Optional<User> user = Optional.of(new User("137", "epam", null, 0));
//		Mockito.when(userRepository.findById(anyInt())).thenReturn(user);
//		Mockito.when(userRepository.save(user.get())).thenReturn(null);
//		assertThrows(FunctionalityException.class, () -> {
//			userServiceImpl.update(dto);
//		});
//	}

	@Test
	void getUsersTest() {
		List<User> userList = new ArrayList<>();
		userList.add(new User());
		userList.add(new User());
		Mockito.when(userRepository.findAll()).thenReturn(userList);
		List<User> actual = (List<User>) userServiceImpl.getUsers();
		assertEquals(userList, actual);
	}

	@Test
	void viewUserByIdSuccess() throws UserNotFoundException {
		Optional<User> user = Optional.of(new User("userName", "password",List.of(new Role("USER")), null, 0.0));
		UserDTO dto = new UserDTO("userName", "password", null, 0.0);
		Mockito.when(userRepository.findById(anyLong())).thenReturn(user);
		Mockito.when(modelMapper.map(user.get(), UserDTO.class)).thenReturn(dto);
		
		UserDTO act = userServiceImpl.viewUserById(anyLong());
		assertEquals(dto, act);
	}

	@Test
	void viewUserByIdFail() throws UserNotFoundException {
		Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> {
			userServiceImpl.viewUserById(anyLong());
		});
	}

	@Test
	void viewUserByUserNameSuccess() throws UserNotFoundException {
		Optional<User> user = Optional.of(new User("userName", "password",List.of(new Role("USER")), null, 0.0));
		UserDTO dto = new UserDTO("userName", "password", null, 0.0);
		Mockito.when(modelMapper.map(user.get(), UserDTO.class)).thenReturn(dto);
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);
		UserDTO act = userServiceImpl.viewUserByUserName(anyString());
		assertEquals(dto, act);
	}

	@Test
	void viewUserByUserNameFail() throws UserNotFoundException {
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> {
			userServiceImpl.viewUserByUserName(anyString());
		});
	}

	@Test
	void userValidSuccess() throws UserNotFoundException {
		Optional<User> user = Optional.of(new User("userName", "pass",List.of(new Role("USER")), null, 0.0));
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);
		boolean result = userServiceImpl.isValidUser("userName", "pass");
		assertTrue(result);
	}

	@Test
	void userValidFail() throws UserNotFoundException {
		Optional<User> user = Optional.of(new User("userName", "pass",List.of(new Role("USER")), null, 0.0));
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);
		boolean result = userServiceImpl.isValidUser("userName", "wrong");
		assertFalse(result);
	}

	@Test
	void userNotValid() {
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> {
			userServiceImpl.isValidUser("user", "pass");
		});
	}

	@Test
	void adminValidSuccess() throws InvalidInputException {
		Optional<User> admin = Optional.of(new User("userName", "pass",List.of(new Role("USER")),null,0));
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(admin);
		boolean result = userServiceImpl.isValidAdmin("userName", "pass");
		assertTrue(result);
	}

	@Test
	void adminValidFail() throws InvalidInputException {
		Optional<User> admin = Optional.of(new User("userName", "pass",List.of(new Role("USER")),null,0));
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(admin);
		boolean result = userServiceImpl.isValidAdmin("userName", "wrong");
		assertFalse(result);
	}

	@Test
	void adminNotValid() {
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
		assertThrows(InvalidInputException.class, () -> {
			userServiceImpl.isValidAdmin("userName", "pass");
		});
	}

}
