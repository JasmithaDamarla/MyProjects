package com.epam.service.implementation;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epam.customexceptions.QuizLibException;
import com.epam.dto.QuizDTO;
import com.epam.entities.Quiz;
import com.epam.repository.QuizRepository;
import com.epam.service.interafaces.QuizService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QuizServiceImpl implements QuizService {
	@Autowired
	private QuizRepository quizRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public QuizDTO create(QuizDTO quizDto) throws QuizLibException {
		return Optional.of(modelMapper.map(quizDto, Quiz.class))
				.map(quizRepository::save)
				.map(quiz -> quizDto).orElseThrow(()->new QuizLibException("Unable to insert quiz"));
	}

	@Override
	public void delete(int id) throws QuizLibException {
		if (checkValidQuizId(id)) {
			quizRepository.deleteById(id);
			log.info("Deleted successful quiz of id {}",id);
		} else {
			throw new QuizLibException("not valid Id");
		}
	}

	@Override
	public QuizDTO update(QuizDTO quizDto) throws QuizLibException {
		log.info("before updating {}",quizDto);
		return Optional.of(modelMapper.map(quizDto, Quiz.class))
				.map(quizRepository::save)
				.map(quiz -> quizDto).orElseThrow(()->new QuizLibException("Unable to update quiz"));
	}

	@Override
	public QuizDTO viewById(int quizId) throws QuizLibException {
		Quiz quiz = quizRepository.findById(quizId)
				.orElseThrow(() -> new QuizLibException("Not found quiz with id " + quizId));
		log.info("Retrieving quiz of id {} is {}",quizId,quiz);
		return modelMapper.map(quiz, QuizDTO.class);
	}

	@Override
	public List<QuizDTO> viewQuiz() {
		log.info("all quiz are getting viewed");
		return quizRepository.findAll().stream().map(quiz->modelMapper.map(quiz, QuizDTO.class)).toList();
	}

	@Override
	public List<Quiz> viewQuiz(int pagenum,int size) {
		Pageable pageable = PageRequest.of(pagenum, size);
		Page<Quiz> list = quizRepository.findAll(pageable);
		log.info("pagination is done and quiz list is ready to view");
		return list.getContent();
	}
	
	@Override
	public boolean checkValidQuizId(int id) {
		return quizRepository.existsById(id);
	}
	
}
