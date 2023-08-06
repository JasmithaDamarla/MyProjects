package com.epam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;

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

import com.epam.customexceptions.QuizLibException;
import com.epam.dto.QuestionDTO;
import com.epam.dto.QuizDTO;
import com.epam.entities.Question;
import com.epam.entities.Quiz;
import com.epam.repository.QuizRepository;
import com.epam.service.implementation.QuizServiceImpl;
import com.epam.service.interafaces.QuestionService;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {
	@Mock(lenient = true)
	private QuizRepository quizRepository;
	@Mock
	private ModelMapper modelMapper;
	@Mock
	private QuestionService questionService;
	@InjectMocks
	private QuizServiceImpl serviceImpl;
	
	
	@Test
	void createQuizSuccess() throws QuizLibException {
		List<QuestionDTO> list = new ArrayList<>();
		list.add(new QuestionDTO());
		list.add(new QuestionDTO());
		List<Question> enlist = new ArrayList<>();
		enlist.add(new Question());
		enlist.add(new Question());
		
		QuizDTO quizDto = new QuizDTO("Java I",list,10);
		Quiz quiz = new Quiz("Java I",enlist,10);
		Mockito.when(modelMapper.map(quizDto, Quiz.class)).thenReturn(quiz);
		
		Mockito.when(quizRepository.save(quiz)).thenReturn(quiz);
		QuizDTO act = serviceImpl.create(quizDto);
	    assertEquals(quizDto,act);
	}
	
	@Test
	void createQuizFail() throws QuizLibException {
		QuizDTO quizDto = new QuizDTO();
		Quiz quiz = new Quiz();
		Mockito.when(modelMapper.map(quizDto, Quiz.class)).thenReturn(quiz);
		Mockito.when(quizRepository.save(quiz)).thenReturn(null);
		assertThrows(QuizLibException.class,()->{
			serviceImpl.create(quizDto);
		});
	}
	
	@Test
	void viewAllQuiz() throws QuizLibException {
		List<QuizDTO> quizList = new ArrayList<>();
		quizList.add(new QuizDTO());
		quizList.add(new QuizDTO());
		List<Quiz> list = new ArrayList<>();
		list.add(new Quiz());
		list.add(new Quiz());
		Mockito.when(quizRepository.findAll()).thenReturn(list);
		List<QuizDTO> actual = serviceImpl.viewQuiz();
		assertEquals(quizList.size(),actual.size());	
	}
	
	@Test
	void viewPaging() {
		List<Question> list = new ArrayList<>();
		Map<Character, String> options = new HashMap<>();
		options.put('a', "1");
		options.put('b', "2");
		options.put('c', "3");
		Question question1 = new Question("title1","easy",options,"topic1",'a');
		question1.setQuestionNum(1);
		Question question2 = new Question("title2","easy",options,"topic2",'b');
		question2.setQuestionNum(2);
		list.add(question1);
		list.add(question2);
		List<Quiz> quizlist = new ArrayList<>();
		quizlist.add(new Quiz());
		quizlist.add(new Quiz());
		Mockito.when(quizRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(quizlist));
		List<Quiz> actual = serviceImpl.viewQuiz(1,2);
		assertEquals(quizlist, actual);
	}
	
	@Test
	void updateQuizSuccess() throws QuizLibException {
		List<QuestionDTO> list = new ArrayList<>();
		list.add(new QuestionDTO());
		list.add(new QuestionDTO());
		QuizDTO quizDto = new QuizDTO("Java I",list,20);
		quizDto.setQuizId(1);
		List<Question> enlist = new ArrayList<>();
		enlist.add(new Question());
		enlist.add(new Question());
		Optional<Quiz> quizz = Optional.of(new Quiz("Java I",enlist,20));
		quizz.ifPresent(id->id.setQuizId(1));
		Mockito.when(modelMapper.map(quizDto, Quiz.class)).thenReturn(quizz.get());
		Mockito.when(quizRepository.save(quizz.get())).thenReturn(quizz.get());
		QuizDTO act = serviceImpl.update(quizDto);
	    assertEquals(quizDto,act);
	}
	
	@Test
	void updateQuizFail() throws QuizLibException {
		QuizDTO quizDto = new QuizDTO("title",null,10);
		quizDto.setQuizId(1);
		Optional<Quiz> quiz = Optional.of(new Quiz("title",null,10));
		Mockito.when(modelMapper.map(quizDto, Quiz.class)).thenReturn(quiz.get());
		Mockito.when(quizRepository.save(quiz.get())).thenReturn(null);
		assertThrows(QuizLibException.class,()->{
			serviceImpl.update(quizDto);
		});
	}
	
	@Test
	void deleteExistingQuiz() throws QuizLibException {
		Mockito.when(quizRepository.existsById(anyInt())).thenReturn(true);
		Mockito.doNothing().when(quizRepository).deleteById(anyInt());
		serviceImpl.delete(anyInt());
		Mockito.verify(quizRepository,times(1)).deleteById(anyInt());
	}
	
	@Test
	void deleteQuestionServiceFail(){
		Mockito.when(quizRepository.existsById(anyInt())).thenReturn(false);
		Mockito.doNothing().when(quizRepository).deleteById(anyInt());
		assertThrows(QuizLibException.class, ()->{
			serviceImpl.delete(anyInt());
		});	
	}
	
	@Test
	void viewQuizById() throws QuizLibException {
		Optional<Quiz> quiz = Optional.of(new Quiz("title", null,20));
		QuizDTO dto = new QuizDTO("title",null,20);
		dto.setQuizId(1);
		Mockito.when(quizRepository.findById(anyInt())).thenReturn(quiz);
		Mockito.when(modelMapper.map(quiz.get(), QuizDTO.class)).thenReturn(dto);
		
		QuizDTO act = serviceImpl.viewById(1);
		assertEquals(dto, act);
	}
	
	@Test
	void viewQuestionByIdNotFound() throws QuizLibException {
		Mockito.when(quizRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(QuizLibException.class,()->{
			serviceImpl.viewById(anyInt());
		});
	}
	
	@Test
	void checkValidId() {
		Mockito.when(quizRepository.existsById(anyInt())).thenReturn(true);
		boolean act = serviceImpl.checkValidQuizId(anyInt());
		assertTrue(act);
	}
	
	@Test
	void checkInValidId() {
		Mockito.when(quizRepository.existsById(anyInt())).thenReturn(false);
		boolean act = serviceImpl.checkValidQuizId(anyInt());
		assertFalse(act);
	}
}
