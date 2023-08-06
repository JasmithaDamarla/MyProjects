package com.epam.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.customexceptions.QuestionLibException;
import com.epam.dto.QuestionDTO;
import com.epam.entities.Question;
import com.epam.service.interafaces.QuestionService;

@WebMvcTest(QuestionLibController.class)
@WithMockUser(value = "spring")
class QuestionLibControllerTest {

	@MockBean
	private QuestionService questionService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testCreateQuestionSuccess() throws Exception {
		String opts = "1,3,4";
		Map<Character, String> options = new HashMap<>();
		options.put('a', "1");
		options.put('b', "2");
		options.put('c', "3");

		int id = 1;
		QuestionDTO question = new QuestionDTO("title", "medium", options, "topic", 'a');
		Mockito.when(questionService.create(question)).thenReturn(question);

		mockMvc.perform(
				post("/createQ").param("id", Integer.toString(id)).param("title", "title").param("difficulty", "medium")
						.param("opts", opts).param("topic", "topic").param("answer", "answer").with(csrf()))
				.andExpect(status().isOk()).andExpect(view().name("CreateQuestion.jsp"));
	}

	@Test
	void testCreateQuestionFail() throws Exception {
		QuestionDTO quest1 = new QuestionDTO();
		quest1.setQuestionNum(1);
		quest1.setTitle("Test Question");
		quest1.setDifficulty("Easy");
		String opts = "1,3,4";
		Map<Character, String> options = new HashMap<>();
		options.put('a', "1");
		options.put('b', "2");
		options.put('c', "3");
		quest1.setOptions(options);
		quest1.setTopic("Test Topic");
		quest1.setAnswer('a');

		Mockito.when(questionService.create(quest1)).thenThrow(new QuestionLibException("not valid"));

		mockMvc.perform(post("/createQ").param("id", "1").param("title", "Test question").param("difficulty", "Easy")
				.param("opts", opts).param("topic", "Test topic").param("answer", "a").with(csrf()))
				.andExpect(status().isOk()).andExpect(view().name("CreateQuestion.jsp"));
	}

	@Test
	void testCreateQuestionException() throws Exception {
		int id = 1;
		QuestionDTO quest1 = new QuestionDTO();
		quest1.setQuestionNum(1);
		quest1.setTitle("Test Question");
		quest1.setDifficulty("Easy");
		Map<Character, String> options = new HashMap<>();
		quest1.setOptions(options);
		quest1.setTopic("Test Topic");
		quest1.setAnswer('a');

		Mockito.when(questionService.create(any(QuestionDTO.class))).thenThrow(new RuntimeException("Test Exception"));

		mockMvc.perform(post("/createQ").param("id", String.valueOf(id)).with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("FailedQuestion.jsp"));

	}

	@Test
	void testReadQuestion() throws Exception {
		List<Question> questions = Arrays
				.asList(new Question("Test question", "Easy", new HashMap<Character, String>(), "Test topic", 'a'));
		Mockito.when(questionService.view()).thenReturn(questions);

		mockMvc.perform(get("/viewQ")).andExpect(status().isOk()).andExpect(view().name("ViewQuestion.jsp"))
				.andExpect(model().attribute("viewQuestions", questions));
	}

	@Test
	void testSetQuestionInUpdate() throws Exception {
		int id = 123;
		QuestionDTO question = new QuestionDTO();
		String opts = "1,2,3";
		Map<Character, String> options = new HashMap<>();
		options.put('a', "1");
		options.put('b', "2");
		options.put('c', "3");
		question.setOptions(options);

		Mockito.when(questionService.checkValidId(id)).thenReturn(true);
		Mockito.when(questionService.viewQuestionById(id)).thenReturn(question);

		mockMvc.perform(get("/updateQuestionsGetOpts").param("id", Integer.toString(id))).andExpect(status().isOk())
				.andExpect(model().attribute("question", question)).andExpect(model().attribute("opts", opts))
				.andExpect(view().name("UpdateQuestionHere.jsp"));

	}

	@Test
	void testSetQuestionInUpdateInvalidId() throws Exception {
		Map<Character, String> options = new HashMap<>();
		options.put('a', "1");
		options.put('b', "2");
		options.put('c', "3");
		QuestionDTO question = new QuestionDTO("title", "Easy", options, "topic", 'a');
		int invalidId = 100;
		Mockito.when(questionService.checkValidId(invalidId)).thenReturn(false);
		Mockito.when(questionService.viewQuestionById(invalidId)).thenReturn(question);
		mockMvc.perform(get("/updateQuestionsGetOpts").param("id", String.valueOf(invalidId)))
				.andExpect(view().name("UpdateQuestion.jsp")).andExpect(status().isOk());
	}

	@Test
	void testUpdateQuestionSuccess() throws Exception {
		String opts = "1,2,3";
		Map<Character, String> options = new HashMap<>();
		options.put('a', "1");
		options.put('b', "2");
		options.put('c', "3");
		QuestionDTO dto = new QuestionDTO("title", "Easy", options, "topic", 'a');
		Mockito.when(questionService.viewQuestionById(1)).thenReturn(dto);
		Mockito.when(questionService.update(any(QuestionDTO.class))).thenReturn(dto);
		mockMvc.perform(
				post("/updateQ").param("id", "1").param("title", "New Question Title").param("difficulty", "Medium")
						.param("opts", opts).param("topic", "New topic").param("answer","a").with(csrf()))
				.andExpect(status().isOk()).andExpect(view().name("viewQ"));
	}

	@Test
	void testUpdateQuestionFail() throws Exception {
		String opts = "1,2,3";
		Map<Character, String> options = new HashMap<>();
		options.put('a', "1");
		options.put('b', "2");
		options.put('c', "3");
		QuestionDTO dto = new QuestionDTO("title", "Easy", options, "topic", 'a');
		Mockito.when(questionService.viewQuestionById(1)).thenReturn(dto);
		Mockito.when(questionService.update(any(QuestionDTO.class))).thenReturn(dto);
		mockMvc.perform(
				post("/updateQ").param("id", "1").param("title", "New Question Title").param("difficulty", "Medium")
						.param("opts", opts).param("topic", "New topic").param("answer", "a").with(csrf()))
				.andExpect(status().isOk()).andExpect(view().name("viewQ"));
	}

	@Test
	void testUpdateQuestionInvalidId() throws Exception {
		String opts = "1,2,3";
		Mockito.when(questionService.viewQuestionById(anyInt())).thenReturn(new QuestionDTO());

		mockMvc.perform(post("/updateQ").param("id", "1").param("title", "New Title").param("difficulty", "Medium")
				.param("opts", opts).param("topic", "New topic").param("answer", "a").with(csrf()))
				.andExpect(status().isOk()).andExpect(view().name("viewQ"));
	}

	@Test
	void testUpdateQuestionNotFound() throws Exception {
		Mockito.when(questionService.viewQuestionById(anyInt())).thenThrow(new QuestionLibException("not found"));

		mockMvc.perform(post("/updateQ").param("id", "1").param("title", "New Title").param("difficulty", "Medium")
				.param("optA", "New option A").param("optB", "New option B").param("optC", "New option C")
				.param("optD", "New option D").param("topic", "New topic").param("answer", "a").with(csrf()))
				.andExpect(status().isOk()).andExpect(view().name("UpdateQuestionHere.jsp"));
	}

	@Test
	void testDeleteQuestionWithInvalidId() throws Exception {
		int id = 1;
		Mockito.when(questionService.checkValidId(id)).thenReturn(false);
		mockMvc.perform(post("/deleteQ").param("id", String.valueOf(id)).with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("DeleteQuestion.jsp"));
	}

	@Test
	void testDeleteQuestion() throws Exception {
		int id = 1;
		QuestionDTO question = new QuestionDTO("What is your name?", "easy", null, "personal", 'a');

		Mockito.when(questionService.checkValidId(id)).thenReturn(true);
		Mockito.when(questionService.viewQuestionById(id)).thenReturn(question);
		Mockito.doNothing().when(questionService).delete(id);

		mockMvc.perform(post("/deleteQ").param("id", String.valueOf(id)).with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("viewQ"));
	}

	@Test
	void testDeleteQuestionNotFound() throws Exception {
		int id = 1;

		Mockito.when(questionService.checkValidId(id)).thenReturn(false);
		Mockito.doNothing().when(questionService).delete(id);
		mockMvc.perform(post("/deleteQ").param("id", String.valueOf(id)).with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("DeleteQuestion.jsp"));
	}

	@Test
	void testDeleteQuestionSQLException() throws Exception {
		int id = 1;

		Mockito.when(questionService.checkValidId(id)).thenReturn(true);
		Mockito.doThrow(new DataIntegrityViolationException("")).when(questionService).delete(id);
		mockMvc.perform(post("/deleteQ").param("id", String.valueOf(id)).with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("FailedQuestion.jsp"));
	}

	@Test
	void testDeleteQuestionException() throws Exception {
		int id = 1;

		Mockito.when(questionService.checkValidId(id)).thenThrow(new RuntimeException());
		Mockito.doNothing().when(questionService).delete(id);
		mockMvc.perform(post("/deleteQ").param("id", String.valueOf(id)).with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("FailedQuestion.jsp"));
	}

}