package com.epam.service.implementation;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epam.customexceptions.QuestionLibException;
import com.epam.dto.QuestionDTO;
import com.epam.entities.Question;
import com.epam.repository.QuestionRepository;
import com.epam.service.interafaces.QuestionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public QuestionDTO create(QuestionDTO questionDto) throws QuestionLibException {
		if(!questionDto.getOptions().keySet().contains(questionDto.getAnswer())) {
			throw new QuestionLibException("Given answer should match one of the options");
		}
		log.info("Question getting created");
		return Optional.of(modelMapper.map(questionDto, Question.class))
				.map(questionRepository::save)
				.map(quuestion -> questionDto).orElseThrow(()->new QuestionLibException("Unable to create question"));
	}

	@Override
	public void delete(int id) throws QuestionLibException,DataIntegrityViolationException {
		if (checkValidId(id)) {
			questionRepository.deleteById(id);
			log.info("Question of id {} is deleted successfully",id);
		} else {
			throw new QuestionLibException("not a valid id "+id);
		}
	}

	@Override
	public QuestionDTO update(QuestionDTO questionDto) throws QuestionLibException {
		log.info("Question getting updated");
		return Optional.of(modelMapper.map(questionDto, Question.class))
				.map(questionRepository::save)
				.map(question -> questionDto).orElseThrow(()->new QuestionLibException("Unable to update question"));

	}
	
	@Override
	public List<Question> view(){
		return questionRepository.findAll();
	}

	@Override
	public List<QuestionDTO> viewQuestion(int pagenum,int size) 
	{
		Pageable pageable = PageRequest.of(pagenum, size);
		log.info("Pagination done for viewing questions");
		return questionRepository.findAll(pageable).stream().map(this::convertToDTO).toList();
	}
	
	@Override
	public QuestionDTO viewQuestionById(int id) throws QuestionLibException {
		Question question = questionRepository.findById(id)
				.orElseThrow(() -> new QuestionLibException("Could not found question with id " + id));
		log.info("Question of id {} is getting viewed as {}",id,question);
		return convertToDTO(question);
	}

	@Override
	public boolean checkValidId(int id) {
		return questionRepository.existsById(id);
	}
	
	private QuestionDTO convertToDTO(Question question) {
		return modelMapper.map(question, QuestionDTO.class);
	}

}
