package com.epam.restapi;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.dto.AdminDTO;
import com.epam.dto.QuestionDTO;
import com.epam.dto.QuizDTO;
import com.epam.dto.UserDTO;
import com.epam.entities.Admin;
import com.epam.entities.User;
import com.epam.service.interafaces.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserRestApi.class)
@WithMockUser(value = "spring")
class UserRestApiTest {
	@MockBean
	private UserService userService;
	@Autowired
	private MockMvc mockMvc;
	private UserDTO dto;
	private User user;
	private AdminDTO admindto;
	private Admin admin;

	@BeforeEach
	void setData() {
		List<QuestionDTO> list = new ArrayList<>();
		Map<Character, String> options = new HashMap<>();
		options.put('a', "1");
		options.put('b', "2");
		options.put('c', "3");
		QuestionDTO question1 = new QuestionDTO("title1", "easy", options, "topic1", 'a');
		question1.setQuestionNum(1);
		QuestionDTO question2 = new QuestionDTO("title2", "easy", options, "topic2", 'b');
		question2.setQuestionNum(2);
		list.add(question1);
		list.add(question2);
		QuizDTO quizdto = new QuizDTO("title", list, 10);
		quizdto.setQuizId(1);
		dto = new UserDTO("User1", "pass1", quizdto, 0);
		dto.setUserId(1);
		QuizDTO quiz = new QuizDTO("title", list, 10);
		quiz.setQuizId(1);

		admindto = new AdminDTO("admin", "epam");
		admindto.setUserId(1);
		admin = new Admin("admin", "epam");
		admin.setUserId(1);
	}

	@Test
	void signUp() throws JsonProcessingException, Exception {
		Mockito.when(userService.signUpUser(any(UserDTO.class))).thenReturn(dto);
		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(dto)).with(csrf())).andExpect(status().isCreated())
				.andReturn();
	}

	@Test
	void updateScoreById() throws JsonProcessingException, Exception {
		Mockito.when(userService.update(any(UserDTO.class))).thenReturn(dto);
		mockMvc.perform(put("/users/{id}", 1).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(dto)).with(csrf())).andExpect(status().isAccepted())
				.andReturn();
	}

	@Test
	void getUsers() throws Exception {
		List<User> userList = new ArrayList<>();
		userList.add(user);
		Mockito.when(userService.getUsers()).thenReturn(userList);
		mockMvc.perform(get("/users")).andExpect(status().isOk());
	}

	@Test
	void viewUserByUserName() throws Exception {
		Mockito.when(userService.viewUserByUserName("user1")).thenReturn(dto);
		mockMvc.perform(get("/users/userName/{userName}", "user1")).andExpect(status().isAccepted());
	}

	@Test
	void validUser() throws Exception {
		Mockito.when(userService.isValidUser(dto.getUserName(), dto.getPassword())).thenReturn(true);
		mockMvc.perform(post("/users/{username}", "user1").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(dto)).with(csrf())).andExpect(status().isAccepted());
	}

	@Test
	void validAdmin() throws Exception {
		Mockito.when(userService.isValidUser(admindto.getUserName(), admindto.getPassword())).thenReturn(true);
		mockMvc.perform(post("/admins").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(admindto)).with(csrf()))
				.andExpect(status().isAccepted());
	}

}
