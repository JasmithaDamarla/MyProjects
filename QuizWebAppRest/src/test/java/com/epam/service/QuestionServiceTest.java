package com.epam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.epam.customexceptions.QuestionLibException;
import com.epam.dto.QuestionDTO;
import com.epam.entities.Question;
import com.epam.repository.QuestionRepository;
import com.epam.service.implementation.QuestionServiceImpl;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

	@Mock(lenient = true)
	private QuestionRepository repository;
	@Mock
	private ModelMapper modelMapper;
	@InjectMocks
	private QuestionServiceImpl serviceImpl;

	@Test
	void createQuestionService() throws QuestionLibException {
		Map<Character, String> options = new HashMap<>();
		int numOpt = 2, i = 0;
		char ch = 'a';
		while (i < numOpt) {
			options.put(ch, "Yes");
			options.put(ch, "No");
			ch++;
			i++;
		}
		Question question = new Question("Is java complete oops", "easy", options, "java", 'b');
		QuestionDTO dto = new QuestionDTO("Is java complete oops", "easy", options, "java", 'b');
		dto.setQuestionNum(1);
		Mockito.when(modelMapper.map(dto, Question.class)).thenReturn(question);
		Mockito.when(repository.save(question)).thenReturn(question);
		QuestionDTO result = serviceImpl.create(dto);
		assertNotNull(result);
	}

	@Test
	void testCreateThrowsException() throws QuestionLibException {
		Map<Character, String> options = new HashMap<>();
		int numOpt = 2, i = 0;
		char ch = 'a';
		while (i < numOpt) {
			options.put(ch, "Yes");
			options.put(ch, "No");
			ch++;
			i++;
		}
		Question question = new Question("Is java complete oops", "easy", options, "java", 'e');
		QuestionDTO dto = new QuestionDTO("Is java complete oops", "easy", options, "java", 'e');

		Mockito.when(repository.save(question)).thenReturn(null);
		assertThrows(QuestionLibException.class, () -> {
			serviceImpl.create(dto);
		});
	}
	
	@Test
	void viewQuestionService() {
		List<Question> expected = serviceImpl.view();
		Mockito.when(repository.findAll()).thenReturn(expected);
		List<Question> actual = serviceImpl.view();
		assertEquals(expected, actual);
	}

	@Test
	void viewPaging() {
		Map<Character, String> options = new HashMap<>();
		options.put('a', "1");
		options.put('b', "2");
		options.put('c', "3");
		QuestionDTO dto = new QuestionDTO("title", "diff", options, "topic", 'a');
		dto.setQuestionNum(1);
		Question question = new Question("title", "diff", options, "topic", 'a');
		question.setQuestionNum(1);
		List<Question> expected = new ArrayList<>();
		expected.add(question);
		Mockito.when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(expected));
		List<QuestionDTO> actual = serviceImpl.viewQuestion(1,2);
		assertEquals(expected.size(), actual.size());
	}

	@Test
	void viewQuestionById() throws QuestionLibException {
		Optional<Question> question = Optional.of(new Question("title", "diff", null, "topic", 'a'));
		Mockito.when(repository.findById(anyInt())).thenReturn(question);
		QuestionDTO dto = new QuestionDTO("title", "diff", null, "topic", 'a');
		Mockito.when(modelMapper.map(question.get(), QuestionDTO.class)).thenReturn(dto);
		QuestionDTO act = serviceImpl.viewQuestionById(anyInt());
		assertEquals(dto, act);
	}

	@Test
	void viewQuestionByIdNotFound() throws QuestionLibException {
		Mockito.when(repository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(QuestionLibException.class, () -> {
			serviceImpl.viewQuestionById(anyInt());
		});
	}

	@Test
	void updateQuestionService() throws QuestionLibException {
		Map<Character, String> options = new HashMap<>();
		int numOpt = 2, i = 0;
		char ch = 'a';
		while (i < numOpt) {
			options.put(ch, "Yes");
			options.put(ch, "No");
			ch++;
			i++;
		}
		Optional<Question> question = Optional.of(new Question("Is java complete oops", "easy", options, "java", 'b'));
		QuestionDTO dto = new QuestionDTO("Is java complete oops", "easy", options, "java", 'b');
		dto.setQuestionNum(1);
		Mockito.when(repository.findById(anyInt())).thenReturn(question);
		Mockito.when(repository.save(question.get())).thenReturn(question.get());
		Mockito.when(modelMapper.map(dto, Question.class)).thenReturn(question.get());
		QuestionDTO result = serviceImpl.update(dto);
		assertEquals(dto.displayQuestion(), result.displayQuestion());
	}

	@Test
	void updateThrowsFunctionalityException() throws QuestionLibException {
		QuestionDTO dto = new QuestionDTO("Is java complete oops", "easy", null, "java", 'b');
		dto.setQuestionNum(1);
		Optional<Question> question = Optional.of(new Question("Is java complete oops", "easy", null, "java", 'b'));
		Mockito.when(repository.findById(anyInt())).thenReturn(question);
		Mockito.when(modelMapper.map(dto, Question.class)).thenReturn(question.get());
		Mockito.when(repository.save(question.get())).thenReturn(null);
		assertThrows(QuestionLibException.class, () -> {
			serviceImpl.update(dto);
		});
	}

	@Test
	void deleteQuestionServiceSuccess() throws QuestionLibException, SQLIntegrityConstraintViolationException {
		Mockito.when(repository.existsById(anyInt())).thenReturn(true);
		Mockito.doNothing().when(repository).deleteById(anyInt());
		serviceImpl.delete(anyInt());
		Mockito.verify(repository, times(1)).deleteById(anyInt());
	}

	
	@Test
	void deleteQuestionServiceFail() throws QuestionLibException {
		Mockito.when(repository.existsById(anyInt())).thenReturn(false);
		Mockito.doNothing().when(repository).deleteById(anyInt());
		assertThrows(QuestionLibException.class, () -> {
			serviceImpl.delete(anyInt());
		});
	}

	@Test
	void checkValidId() {
		boolean exp = true;
		Mockito.when(repository.existsById(anyInt())).thenReturn(exp);
		boolean act = serviceImpl.checkValidId(anyInt());
		assertEquals(exp, act);
	}

	@Test
	void checkInValidId() {
		boolean exp = false;
		Mockito.when(repository.existsById(anyInt())).thenReturn(exp);
		boolean act = serviceImpl.checkValidId(anyInt());
		assertEquals(exp, act);
	}
}
