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
import com.epam.service.interafaces.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(QuestionRestApi.class)
@WithMockUser(value = "spring")
class QuestionRestApiTest {
	@MockBean
	private QuestionService questionService;
	@Autowired
	private MockMvc mockMvc;
	private QuestionDTO dto;

	@BeforeEach
	void setData() {
		Map<Character, String> options = new HashMap<>();
		int numOpt = 2, i = 0;
		char ch = 'a';
		while (i < numOpt) {
			options.put(ch, "Yes");
			options.put(ch, "No");
			ch++;
			i++;
		}
		dto = new QuestionDTO("Is java complete oops", "easy", options, "java", "a".charAt(0));
		dto.setQuestionNum(1);
	}

	
	@Test
	void createSuccess() throws Exception {
		Mockito.when(questionService.create(any(QuestionDTO.class))).thenReturn(dto);
		mockMvc.perform(post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)).with(csrf())).andExpect(status().isCreated());
	}

	@Test
	void view() throws Exception {
		List<QuestionDTO> list = new ArrayList<>();
		list.add(dto);
		Mockito.when(questionService.viewQuestion(1, 2)).thenReturn(list);
		mockMvc.perform(get("/questions").param("pagenum", "1").param("size", "2")).andExpect(status().isOk());

	}

	@Test
	void deleteQuestion() throws Exception {
		Mockito.doNothing().when(questionService).delete(1);
		mockMvc.perform(delete("/questions/{id}", 1).with(csrf())).andExpect(status().isNoContent());
	}

	@Test
	void viewQuestionById() throws Exception {
		Mockito.when(questionService.viewQuestionById(1)).thenReturn(dto);
		mockMvc.perform(get("/questions/{id}", 1)).andExpect(status().isAccepted());
	}

	@Test
	void updateQuestion() throws Exception {
		Mockito.when(questionService.update(any(QuestionDTO.class))).thenReturn(dto);
		mockMvc.perform(put("/questions/{id}", 1).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(dto)).with(csrf())).andExpect(status().isOk());
	}

}
