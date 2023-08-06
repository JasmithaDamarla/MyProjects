package com.epam.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.customexceptions.FunctionalityException;
import com.epam.customexceptions.InvalidInputException;
import com.epam.customexceptions.SignUpFailedException;
import com.epam.customexceptions.UserNotFoundException;
import com.epam.dto.QuestionDTO;
import com.epam.dto.QuizDTO;
import com.epam.dto.UserDTO;
import com.epam.entities.Question;
import com.epam.entities.Quiz;
import com.epam.service.interafaces.QuizService;
import com.epam.service.interafaces.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebMvcTest(UserController.class)
@WithMockUser(value = "spring")
class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;

	@MockBean
	private QuizService quizService;
	@MockBean
	private ModelMapper modelMapper;
	@MockBean
	private HttpServletRequest request;
	@MockBean
	private HttpSession session;

	@Test
	void testGoHome() throws Exception {
		mockMvc.perform(get("/home")).andExpect(status().isOk()).andExpect(view().name("UserHome.jsp"));
	}

	@Test
	void testAddAdminSuccess() throws Exception {
		Mockito.when(userService.signUpAdmin(any(UserDTO.class))).thenReturn(any(UserDTO.class));
		mockMvc.perform(post("/addAdmin").param("username", "newAdmin").param("password", "password")
				.param("confirm", "confirmpassword").with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("AdminHome.jsp"));
	}

	@Test
	void testAddAdminFailure() throws Exception {
		Mockito.when(userService.signUpAdmin(any(UserDTO.class))).thenThrow(new SignUpFailedException(""));
		mockMvc.perform(post("/addAdmin").param("username", "123").param("password", "password")
				.param("confirm", "password").with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("AddAdmin.jsp"));
	}

	@Test
	void testRegisterUserSuccess() throws Exception {
		Mockito.when(userService.signUpUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
		mockMvc.perform(post("/registerUser").param("username", "123").param("password", "password")
				.param("confirm", "confirmpassword").with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("UserHome.jsp"));
	}

	@Test
	void testRegisterUserFailure() throws Exception {
		Mockito.when(userService.signUpUser(any(UserDTO.class))).thenThrow(new SignUpFailedException(""));
		mockMvc.perform(post("/registerUser").param("username", "123").param("password", "password")
				.param("confirm", "password").with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("RegisterUser.jsp"));
	}

	@Test
	void testUserVerifyAdminSuccess() throws Exception {
		Mockito.when(userService.isValidAdmin("Admin", "epam")).thenReturn(true);
		mockMvc.perform(post("/userLogin").param("user", "admin").param("username", "Admin").param("password", "epam")
				.with(csrf())).andExpect(status().isOk()).andExpect(view().name("AdminHome.jsp"));
	}

	@Test
	void testUserVerifyAdminFailure() throws Exception {
		Mockito.when(userService.isValidAdmin(anyString(), anyString())).thenThrow(new InvalidInputException(""));
		mockMvc.perform(post("/userLogin").param("user", "admin").param("username", "Admin")
				.param("password", "epameee").with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("UserHome.jsp"));
	}

	@Test
	void testUserVerifyUserSuccess() throws Exception {
		Mockito.when(userService.isValidUser(anyString(), anyString())).thenReturn(true);
		Mockito.when(userService.viewUserById(anyLong())).thenReturn(new UserDTO("user1", "epam", null, 0.0));
		mockMvc.perform(post("/userLogin").param("user", "user").param("username", "100").param("password", "epam")
				.with(csrf())).andExpect(status().isOk()).andExpect(view().name("UserOperations.jsp"));
	}

	@Test
	void testUserVerifyUserInvalidUser() throws Exception {
		Mockito.when(userService.isValidUser(anyString(), anyString())).thenThrow(new UserNotFoundException(""));
		mockMvc.perform(post("/userLogin").param("user", "user").param("username", "1000").param("password", "ksoas")
				.with(csrf())).andExpect(status().isOk()).andExpect(view().name("UserHome.jsp"));
	}

//	@Test
//	void takeQuizSuccess() throws Exception {
//		User nowUser = new User("user", "passs", null, 10);
//		Mockito.when(session.getAttribute("user")).thenReturn(nowUser);
//		mockMvc.perform(get("/takeQuiz").sessionAttr("user", nowUser)).andExpect(status().isOk()).andExpect(view().name("NotAccess.jsp"));
//
//	}

	@Test
	void takeQuizFail() throws Exception {
		UserDTO user = new UserDTO("user", "passs", null, 0);
		Mockito.when(session.getAttribute("user")).thenReturn(user);
		mockMvc.perform(get("/takeQuiz").sessionAttr("user", user)).andExpect(status().isOk())
				.andExpect(view().name("StartQuiz.jsp"));

	}

	@Test
	void testMyScore() throws Exception {
		UserDTO user = new UserDTO("user", "passs", new QuizDTO(), 10);
		Mockito.when(session.getAttribute("user")).thenReturn(user);
		mockMvc.perform(get("/viewMyScore").sessionAttr("user", user)).andExpect(status().isOk())
				.andExpect(view().name("ViewMyScore.jsp")).andExpect(model().attributeExists("scoreValue"));
	}

	@Test
	void testMyScoreWithZero() throws Exception {
		UserDTO user = new UserDTO("user", "passs", new QuizDTO(), 0);
		Mockito.when(session.getAttribute("user")).thenReturn(user);
		mockMvc.perform(get("/viewMyScore").sessionAttr("user", user)).andExpect(status().isOk())
				.andExpect(view().name("ViewMyScore.jsp")).andExpect(model().attributeExists("scoreValue"));
	}

	@Test
	void allScoresView() throws Exception {
		mockMvc.perform(get("/viewAllUsers")).andExpect(status().isOk()).andExpect(model().attributeExists("userList"))
				.andExpect(view().name("ViewAllScore.jsp"));
	}

	@Test
	void adminAllScoresTest() throws Exception {
		mockMvc.perform(get("/viewAllUsersAdmin")).andExpect(status().isOk())
				.andExpect(model().attributeExists("userList")).andExpect(view().name("AdminViewScores.jsp"));
	}

	@Test
	void testStartQuiz() throws Exception {
		List<QuizDTO> quizNums = new ArrayList<>();
		quizNums.add(new QuizDTO("title1", null, 10));
		quizNums.add(new QuizDTO("title2", null, 10));
		Mockito.when(quizService.viewQuiz()).thenReturn(quizNums);

		QuizDTO quiz = new QuizDTO("title", null, 10);
		Mockito.when(quizService.viewById(1)).thenReturn(quiz);
		mockMvc.perform(post("/startQuiz").with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("TakeQuiz.jsp"));
	}

	@Test
	void testDoPostScoreFail() throws Exception {
		Quiz assignQuiz = new Quiz();
		assignQuiz.setMarks(10);
		List<Question> questions = new ArrayList<>();
		Question question1 = new Question();
		question1.setAnswer('A');
		Question question2 = new Question();
		question2.setAnswer('B');
		questions.add(question1);
		questions.add(question2);
		assignQuiz.setQuestions(questions);
		mockMvc.perform(post("/updateScore").param("1", "A").param("2", "B").with(csrf()))
				.andExpect(view().name("Failed.jsp"));
	}

	@Test
	void testDoPostScoreSuccess() throws Exception {
		Map<Character, String> options = new HashMap<>();
		options.put('a', "Yes");
		options.put('b', "No");
		Question question = new Question("Is java complete oops", "easy", options, "java", 'a');
		question.setQuestionNum(1);
		QuestionDTO dto = new QuestionDTO("Is java complete oops", "easy", options, "java", 'a');
		dto.setQuestionNum(1);
		Mockito.when(modelMapper.map(dto, Question.class)).thenReturn(question);

		QuizDTO quiz = new QuizDTO("title", null, 10);
		List<QuestionDTO> questions = new ArrayList<>();
		questions.add(dto);
		quiz.setQuestions(questions);

		UserDTO userdto = new UserDTO("username", "pass", null, 0);
		userdto.setUserId(1);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("quiz")).thenReturn(quiz);
		Mockito.when(session.getAttribute("user")).thenReturn(userdto);

		mockMvc.perform(post("/updateScore").param("1", "a").sessionAttr("user", userdto).sessionAttr("quiz", quiz)
				.with(csrf())).andExpect(status().isOk()).andExpect(view().name("UserOperations.jsp"));
	}

	@Test
	void testDoPostScoreThrowsFunctionalityException() throws Exception {
		QuizDTO quiz = new QuizDTO("title", null, 10);
		List<QuestionDTO> questions = new ArrayList<>();
		QuestionDTO question1 = new QuestionDTO();
		questions.add(question1);
		QuestionDTO question2 = new QuestionDTO();
		questions.add(question2);
		quiz.setQuestions(questions);

		UserDTO dto = new UserDTO("user", "passs", quiz, 0);
		dto.setUserId(1);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("quiz")).thenReturn(quiz);
		Mockito.when(session.getAttribute("user")).thenReturn(dto);
		Mockito.doThrow(new FunctionalityException("Error updating user")).when(userService).update(dto);
		mockMvc.perform(post("/updateScore").param("1", "A").param("2", "B").with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("Failed.jsp"));
	}

}
