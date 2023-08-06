package com.epam.service.interafaces;

import java.util.List;

import com.epam.customexceptions.QuizLibException;
import com.epam.dto.QuizDTO;
import com.epam.entities.Quiz;

public interface QuizService {
	QuizDTO create(QuizDTO quizDto) throws QuizLibException;
	void delete(int id) throws QuizLibException;
	List<QuizDTO> viewQuiz();
	QuizDTO update(QuizDTO quizDto) throws QuizLibException;
	QuizDTO viewById(int quizId) throws QuizLibException;
	boolean checkValidQuizId(int id);
	List<Quiz> viewQuiz(int pagenum, int size);

}
