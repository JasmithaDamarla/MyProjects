package com.epam.service.interafaces;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.epam.customexceptions.QuestionLibException;
import com.epam.dto.QuestionDTO;
import com.epam.entities.Question;

public interface QuestionService {
    QuestionDTO create(QuestionDTO questionDto) throws QuestionLibException;
	QuestionDTO update(QuestionDTO questionDto) throws QuestionLibException;
	QuestionDTO viewQuestionById(int id) throws QuestionLibException;
	boolean checkValidId(int id);
	void delete(int id) throws QuestionLibException, DataIntegrityViolationException;
	List<Question> view();
	List<QuestionDTO> viewQuestion(int pagenum, int size);
}
