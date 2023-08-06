package com.epam.restapi;

import java.sql.SQLIntegrityConstraintViolationException;
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

import com.epam.customexceptions.QuestionLibException;
import com.epam.dto.QuestionDTO;
import com.epam.service.interafaces.QuestionService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Slf4j
public class QuestionRestApi {
	
	@Autowired
	private QuestionService questionService;
	
	@PostMapping("questions")
	public ResponseEntity<QuestionDTO> create(@Valid @RequestBody QuestionDTO questionDto) throws QuestionLibException{
		QuestionDTO dto = questionService.create(questionDto);
		log.info("question created");
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}
	
	@GetMapping("questions/{id}")
	public ResponseEntity<QuestionDTO> view(@PathVariable int id) throws QuestionLibException{
		log.info("getting question of id {}",id);
		return new ResponseEntity<>(questionService.viewQuestionById(id),HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/questions")
	public List<QuestionDTO> view(@RequestParam int pagenum,@RequestParam int size){
		log.info("retrieving all questions");
		return questionService.viewQuestion(pagenum,size);
	}

	@PutMapping("questions/{id}")
	public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable int id,@Valid @RequestBody QuestionDTO questionDto) throws QuestionLibException {
		QuestionDTO dto = questionService.update(questionDto);
		log.info("updating question of id {} is done",id);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
	
	@DeleteMapping("questions/{id}")
	public ResponseEntity<String> deleteQuestion(@PathVariable int id) throws QuestionLibException, SQLIntegrityConstraintViolationException {
		questionService.delete(id);
		log.info("deleted question successful");
		return new ResponseEntity<>("Deleted successfully",HttpStatus.NO_CONTENT);
	}
}
