package com.epam.restapi;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.epam.dto.QuestionDTO;
import com.epam.dto.QuizDTO;
import com.epam.entities.Question;
import com.epam.entities.Quiz;
import com.epam.service.interafaces.QuizService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(QuizRestApi.class)
@WithMockUser(value = "spring")
class QuizRestApiTest {
	@MockBean
	private QuizService quizService;
	@Autowired
	private MockMvc mockMvc;
	private QuizDTO dto;
	private Quiz quiz;

	@BeforeEach
	void setData() {
		List<Question> list = new ArrayList<>();
		Map<Character, String> options = new HashMap<>();
		options.put('a', "1");
		options.put('b', "2");
		options.put('c', "3");
		Question question1 = new Question("title1", "easy", options, "topic1", 'a');
		question1.setQuestionNum(1);
		Question question2 = new Question("title2", "easy", options, "topic2", 'b');
		question2.setQuestionNum(2);
		list.add(question1);
		list.add(question2);

		List<QuestionDTO> dtolist = new ArrayList<>();
		QuestionDTO dtoques1 = new QuestionDTO("title1", "easy", options, "topic1", 'a');
		dtoques1.setQuestionNum(1);
		QuestionDTO dtoques2 = new QuestionDTO("title2", "easy", options, "topic2", 'b');
		dtoques2.setQuestionNum(2);
		dtolist.add(dtoques1);
		dtolist.add(dtoques2);

		dto = new QuizDTO("title", dtolist, 10);
		dto.setQuizId(1);
		quiz = new Quiz("title", list, 10);
		quiz.setQuizId(1);
	}

	@Test
	void createQuiz() throws JsonProcessingException, Exception {

		Mockito.when(quizService.create(any(QuizDTO.class))).thenReturn(dto);
		mockMvc.perform(post("/quiz").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(dto)).with(csrf())).andExpect(status().isCreated())
				.andReturn();
	}

	@Test
	void view() throws Exception {
		List<Quiz> list = new ArrayList<>();
		list.add(quiz);
		Mockito.when(quizService.viewQuiz(1, 2)).thenReturn(list);
		mockMvc.perform(get("/quiz").param("pagenum", "1").param("size", "2")).andExpect(status().isOk());
	}

	@Test
	void deleteQuiz() throws Exception {
		Mockito.doNothing().when(quizService).delete(1);
		mockMvc.perform(delete("/quiz/{id}", 1).with(csrf())).andExpect(status().isNoContent());
	}

	@Test
	void viewQuizById() throws Exception {
		Mockito.when(quizService.viewById(1)).thenReturn(dto);
		mockMvc.perform(get("/quiz/{id}", 1)).andExpect(status().isAccepted());
	}

	@Test
	void updateQuiz() throws Exception {
		Mockito.when(quizService.update(any(QuizDTO.class))).thenReturn(dto);
		mockMvc.perform(put("/quiz/{id}", 1).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(dto)).with(csrf())).andExpect(status().isAccepted())
				.andReturn();
	}

}
