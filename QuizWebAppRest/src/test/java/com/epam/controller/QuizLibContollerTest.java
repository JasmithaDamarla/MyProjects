package com.epam.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.customexceptions.QuestionLibException;
import com.epam.customexceptions.QuizLibException;
import com.epam.dto.QuestionDTO;
import com.epam.dto.QuizDTO;
import com.epam.entities.Question;
import com.epam.entities.Quiz;
import com.epam.service.interafaces.QuestionService;
import com.epam.service.interafaces.QuizService;

import jakarta.servlet.http.HttpServletRequest;

@WebMvcTest(QuizLibController.class)
@WithMockUser(value = "spring")
class QuizLibContollerTest {
	@MockBean
	private QuizService quizService;
	@MockBean
	private HttpServletRequest request;
	@MockBean
	private QuestionService questionService;
	@Autowired
	private MockMvc mockMvc;

	@Test
	void testCreateQuizSuccess() throws Exception {
		int quizId = 1;
		String title = "Test Quiz";
		String[] questions = { "1", "2", "3" };

		int marks = 10;

		QuestionDTO question1 = new QuestionDTO("title1", "diff1", null, "topic1", 'a');
		QuestionDTO question2 = new QuestionDTO("title2", "diff2", null, "topic2", 'a');
		QuestionDTO question3 = new QuestionDTO("title3", "diff3", null, "topic3", 'a');
		List<QuestionDTO> questionsList = Arrays.asList(question1, question2, question3);

		QuizDTO quiz = new QuizDTO(title, questionsList, marks);
		quiz.setQuizId(quizId);

		Mockito.when(request.getParameterValues("list")).thenReturn(questions);
		Mockito.when(questionService.viewQuestionById(1)).thenReturn(question1);
		Mockito.when(questionService.viewQuestionById(2)).thenReturn(question2);
		Mockito.when(questionService.viewQuestionById(3)).thenReturn(question3);
		Mockito.when(quizService.create(quiz)).thenReturn(quiz);

		mockMvc.perform(post("/quizCreate").param("list", "1", "2", "3").param("quizId", String.valueOf(quizId))
				.param("title", title).param("marks", String.valueOf(marks)).with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("QuizHome.jsp"));

	}

	@Test
	void testCreateQuizQuestionNotFound() throws Exception {
		String title = "Test Quiz";
		int marks = 10;

		Mockito.when(questionService.viewQuestionById(10)).thenThrow(new QuestionLibException(" "));

		mockMvc.perform(post("/quizCreate").param("title", title).param("list", "10")
				.param("marks", String.valueOf(marks)).with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("FailedQuiz.jsp"));
	}

	@Test
	void testCreateQuizFailed() throws Exception {
		String title = "Test Quiz";
		String[] questions = { "20" };
		int marks = 10;

		Mockito.when(quizService.create(any(QuizDTO.class))).thenReturn(null);
		Mockito.when(questionService.viewQuestionById(20)).thenThrow(new QuestionLibException(""));
		mockMvc.perform(post("/quizCreate").param("list", questions).param("title", title)
				.param("marks", String.valueOf(marks)).with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("FailedQuiz.jsp"));
	}

	@Test
	void viewAllQuestions() throws Exception {

		List<Question> questionlist = new ArrayList<>();
		questionlist.add(new Question());
		questionlist.add(new Question());
		Mockito.when(questionService.view()).thenReturn(questionlist);

		mockMvc.perform(get("/allQuestions")).andExpect(status().isOk())
				.andExpect(model().attribute("questionsList", questionlist)).andExpect(view().name("CreateQuiz.jsp"));
	}

	@Test
	void viewAllQuiz() throws Exception {

		List<QuizDTO> quizlist = new ArrayList<>();
		quizlist.add(new QuizDTO());
		quizlist.add(new QuizDTO());
		Mockito.when(quizService.viewQuiz()).thenReturn(quizlist);

		mockMvc.perform(get("/allQuiz")).andExpect(status().isOk())
				.andExpect(model().attribute("allQuizList", quizlist)).andExpect(view().name("ViewAllQuiz.jsp"));
	}

	@Test
	void testViewQuiz() throws Exception {
		QuizDTO quiz1 = new QuizDTO("Quiz 1", Collections.emptyList(), 10);
		QuizDTO quiz2 = new QuizDTO("Quiz 2", Collections.emptyList(), 20);
		List<QuizDTO> quizList = Arrays.asList(quiz1, quiz2);

		Mockito.when(quizService.viewQuiz()).thenReturn(quizList);

		mockMvc.perform(get("/displayAllIdAndSelectId")).andExpect(status().isOk())
				.andExpect(view().name("ViewQuiz.jsp")).andExpect(model().attribute("displayIds", quizList));
	}

	@Test
	void testUpdateQuizId() throws Exception {
		QuizDTO quiz1 = new QuizDTO("Quiz 1", Collections.emptyList(), 10);
		QuizDTO quiz2 = new QuizDTO("Quiz 2", Collections.emptyList(), 20);
		List<QuizDTO> quizList = Arrays.asList(quiz1, quiz2);

		Mockito.when(quizService.viewQuiz()).thenReturn(quizList);

		mockMvc.perform(get("/displayAllIdAndSelectIdUpdate")).andExpect(status().isOk())
				.andExpect(view().name("UpdateQuiz.jsp")).andExpect(model().attribute("displayIdsAndSelect", quizList));
	}

	@Test
	void readQuizValidIdExpectDisplayQuizView() throws Exception {
		QuizDTO quiz = new QuizDTO();
		Mockito.when(quizService.checkValidQuizId(anyInt())).thenReturn(true);
		Mockito.when(quizService.viewById(anyInt())).thenReturn(quiz);
		mockMvc.perform(get("/quizView?id=123")).andExpect(status().isOk())
				.andExpect(model().attribute("viewQuiz", quiz)).andExpect(view().name("DisplayQuiz.jsp"));
	}

	@Test
	void readQuizInvalidIdExpectViewQuizView() throws Exception {
		Mockito.when(quizService.checkValidQuizId(anyInt())).thenReturn(false);
		mockMvc.perform(get("/quizView?id=12")).andExpect(status().isOk()).andExpect(view().name("ViewQuiz.jsp"));
	}

	@Test
	void testSetQuestionInUpdate() throws Exception {
		int quizId = 123;
		List<QuestionDTO> questions = new ArrayList<>();
		questions.add(new QuestionDTO("title1", "diff1", null, "topic1", 'a'));
		questions.add(new QuestionDTO("title2", "diff2", null, "topic2", 'a'));
		QuizDTO quiz = new QuizDTO("Quiz Title", questions, 10);

		Mockito.when(quizService.checkValidQuizId(quizId)).thenReturn(true);
		Mockito.when(quizService.viewById(quizId)).thenReturn(quiz);

		mockMvc.perform(get("/updateQuizGetId").param("id", String.valueOf(quizId))).andExpect(status().isOk())
				.andExpect(model().attributeExists("quiz")).andExpect(model().attributeExists("questionsList"))
				.andExpect(model().attribute("quiz", quiz)).andExpect(view().name("UpdateQuizHere.jsp"));
	}

	@Test
	void testSetQuestionInUpdateFail() throws Exception {
		int quizId = 123;

		Mockito.when(quizService.checkValidQuizId(quizId)).thenReturn(false);

		mockMvc.perform(get("/updateQuizGetId").param("id", String.valueOf(quizId))).andExpect(status().isOk())
				.andExpect(view().name("UpdateQuiz.jsp"));
	}

	@Test
	void testUpdateQuizSuccess() throws Exception {
//		int quizId = 1;
		String title = "Test Quiz";
		String[] questions = { "1", "2", "3" };

		int marks = 10;

		QuestionDTO question1 = new QuestionDTO("title1", "diff1", null, "topic1", 'a');
		QuestionDTO question2 = new QuestionDTO("title2", "diff2", null, "topic2", 'a');
		QuestionDTO question3 = new QuestionDTO("title3", "diff3", null, "topic3", 'a');
		List<QuestionDTO> questionsList = Arrays.asList(question1, question2, question3);

		QuizDTO quiz = new QuizDTO(title, questionsList, marks);

		Mockito.when(quizService.checkValidQuizId(anyInt())).thenReturn(true);
		Mockito.when(questionService.viewQuestionById(1)).thenReturn(question1);
		Mockito.when(questionService.viewQuestionById(2)).thenReturn(question2);
		Mockito.when(quizService.viewById(anyInt())).thenReturn(quiz);
		Mockito.when(quizService.update(any(QuizDTO.class))).thenReturn(quiz);

		mockMvc.perform(get("/quizUpdate").param("quizId", "1").param("title", title).param("list", questions)
				.param("marks", "20")).andExpect(status().isOk()).andExpect(view().name("allQuiz"));

	}

	@Test
	void testUpdateQuizInvalidQuizId() throws Exception {
		Mockito.when(quizService.checkValidQuizId(anyInt())).thenReturn(false);

		mockMvc.perform(get("/quizUpdate").param("quizId", "20").param("title", "Test Quiz").param("questions", "1,2,3")
				.param("marks", "10")).andExpect(status().isOk()).andExpect(view().name("QuizHome.jsp"));
	}

	@Test
	void testUpdateQuizNotFoundToUpdate() throws Exception {
		List<Question> questionsList = new ArrayList<>();
		Question question1 = new Question();
		Question question2 = new Question();
		questionsList.add(question1);
		questionsList.add(question2);
		Quiz quiz = new Quiz("title", questionsList, 10);
		Mockito.when(quizService.checkValidQuizId(anyInt())).thenReturn(true);
		Mockito.when(quizService.viewById(20)).thenThrow(new QuizLibException("Quiz not found"));
		mockMvc.perform(get("/quizUpdate").param("quizId", "1").param("title", quiz.getQuizTitle())
				.param("questions", "1,2,3").param("marks", String.valueOf(10))).andExpect(status().isOk())
				.andExpect(view().name("FailedQuiz.jsp"));
	}

	@Test
	void testUpdateQuizQuetsionNotFoundToUpdate() throws Exception {
		int quizId = 1;
		String title = "Test Quiz";
		String[] questions = { "1", "2" };

		int marks = 10;

		QuestionDTO question1 = new QuestionDTO("title1", "diff1", null, "topic1", 'a');
		QuestionDTO question2 = new QuestionDTO("title2", "diff2", null, "topic2", 'a');
		QuestionDTO question3 = new QuestionDTO("title3", "diff3", null, "topic3", 'a');
		List<QuestionDTO> questionsList = Arrays.asList(question1, question2, question3);

		QuizDTO quiz = new QuizDTO(title, questionsList, marks);
		quiz.setQuizId(quizId);
//		Quiz newQuiz = new Quiz(title, questionsList, marks);
		Mockito.when(questionService.viewQuestionById(2)).thenThrow(new QuestionLibException(" "));
		Mockito.when(questionService.viewQuestionById(1)).thenThrow(new QuestionLibException(" "));
		Mockito.when(quizService.checkValidQuizId(anyInt())).thenReturn(true);
		Mockito.when(questionService.viewQuestionById(anyInt())).thenThrow(new QuestionLibException("Quiz not found"));
		mockMvc.perform(
				get("/quizUpdate").param("quizId", String.valueOf(quiz.getQuizId())).param("title", quiz.getQuizTitle())
						.param("list", questions).param("marks", String.valueOf(quiz.getMarks())))
				.andExpect(status().isOk()).andExpect(view().name("FailedQuiz.jsp"));
	}

	@Test
	void testGetIdsForDeleteQuiz() throws Exception {
		List<QuizDTO> quizIds = Arrays.asList(new QuizDTO(), new QuizDTO());
		Mockito.when(quizService.viewQuiz()).thenReturn(quizIds);

		mockMvc.perform(get("/quizIdsForDelete")).andExpect(status().isOk())
				.andExpect(model().attribute("displayIds", quizIds)).andExpect(view().name("DeleteQuiz.jsp"));
	}

	@Test
	void testDeleteQuizValidId() throws Exception {
		Mockito.when(quizService.checkValidQuizId(anyInt())).thenReturn(true);
		mockMvc.perform(get("/quizDelete").param("id", String.valueOf(anyInt()))).andExpect(status().isOk())
				.andExpect(view().name("allQuiz"));
	}

	@Test
	void testDeleteQuizInvalidId() throws Exception {
		Mockito.when(quizService.checkValidQuizId(anyInt())).thenReturn(false);
		mockMvc.perform(get("/quizDelete").param("id", String.valueOf(anyInt()))).andExpect(status().isOk())
				.andExpect(view().name("QuizHome.jsp"));
	}

	@Test
	void testDeleteQuizException() throws Exception {
		Mockito.when(quizService.checkValidQuizId(anyInt())).thenThrow(new RuntimeException("error"));
		mockMvc.perform(get("/quizDelete").param("id", String.valueOf(anyInt()))).andExpect(status().isOk())
				.andExpect(view().name("Failed.jsp"));
	}
}