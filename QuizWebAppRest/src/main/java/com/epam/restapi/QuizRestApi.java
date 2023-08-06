package com.epam.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.customexceptions.QuizLibException;
import com.epam.dto.QuizDTO;
import com.epam.entities.Quiz;
import com.epam.service.interafaces.QuizService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@Slf4j
public class QuizRestApi {
	@Autowired
	private QuizService quizService;

	@PostMapping("quiz")
	public ResponseEntity<QuizDTO> createQuiz(@Valid @RequestBody QuizDTO quizDto) throws QuizLibException {
		QuizDTO dto = quizService.create(quizDto);
		log.info("Quiz is created");
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}

	@GetMapping("quiz/{id}")
	public ResponseEntity<QuizDTO> viewQuiz(@PathVariable int id) throws QuizLibException{
		log.info("Quiz Rest Controller : getting quiz of id {}",id);
		return new ResponseEntity<>(quizService.viewById(id),HttpStatus.ACCEPTED);
	}

	@GetMapping("quiz")
	public List<Quiz> viewAllQuiz(@RequestParam int pagenum,@RequestParam int size) {
		log.info("Quiz Rest Controller : retrieving all quizes");
		return quizService.viewQuiz(pagenum,size);
	}

	@PutMapping("quiz/{id}")
	public ResponseEntity<QuizDTO> updateQuiz(@PathVariable int id,@Valid @RequestBody QuizDTO dto) throws QuizLibException {
		dto.setQuizId(id);
		dto = quizService.update(dto);
		log.info("quiz is updated");
		return new ResponseEntity<>(dto,HttpStatus.ACCEPTED);
	}

	@DeleteMapping("quiz/{id}")
	public ResponseEntity<String> deleteQuiz(@PathVariable int id) throws QuizLibException {
		quizService.delete(id);
		log.info("quiz of id {} is deleted",id);
		return new ResponseEntity<>("Quiz with id " + id + " deleted successfully!!",HttpStatus.NO_CONTENT);
	}
}
