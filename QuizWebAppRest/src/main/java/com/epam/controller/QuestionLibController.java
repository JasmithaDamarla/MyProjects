package com.epam.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.customexceptions.QuestionLibException;
import com.epam.dto.QuestionDTO;
import com.epam.service.interafaces.QuestionService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class QuestionLibController {
	@Autowired
	private QuestionService questionService;

	private static final String FAILED = "FailedQuestion.jsp";

	@PostMapping("/createQ")
	public ModelAndView create(String title, String difficulty, String opts, String topic, String answer) {
		ModelAndView modelAndView = new ModelAndView();
		String str = FAILED;
		try {
			Map<Character, String> options = new HashMap<>();
			String[] optStrings = opts.split(",");
			char ch = 'a';
			for (String opt : optStrings) {
				options.put(ch, opt);
				ch++;

			}
			QuestionDTO newQuestion = new QuestionDTO(title, difficulty, options, topic, answer.charAt(0));

			questionService.create(newQuestion);
			str = "CreateQuestion.jsp";

		} catch (QuestionLibException e) {
			log.error(e.getMessage());
		} catch (RuntimeException excep) {
			log.error(excep.toString());
		}
		modelAndView.setViewName(str);
		return modelAndView;
	}

	@RequestMapping("viewQ")
	public ModelAndView readQuestion() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ViewQuestion.jsp");
		modelAndView.addObject("viewQuestions", questionService.view());
		return modelAndView;
	}

	@GetMapping("/updateQuestionsGetOpts")
	public ModelAndView setoptsInUpdate(int id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (questionService.checkValidId(id)) {
				QuestionDTO question = questionService.viewQuestionById(id);
				String opts;
				List<String> idArray = new ArrayList<>();
				Map<Character, String> options = question.getOptions();
				for (Entry<Character, String> ch : options.entrySet()) {
					idArray.add(ch.getValue());
				}
				String[] qIdArray = idArray.toArray(new String[idArray.size()]);
				opts = String.join(",", qIdArray);
				modelAndView.addObject("question", question);
				modelAndView.addObject("opts", opts);
				modelAndView.addObject("num", id);
				modelAndView.setViewName("UpdateQuestionHere.jsp");
			} else {
				modelAndView.setViewName("UpdateQuestion.jsp");
				log.info("Not a valid Id");
			}
		} catch (QuestionLibException quizNotFound) {
			log.error(quizNotFound.getMessage());
		}
		return modelAndView;
	}

	@PostMapping("/updateQ")
	public ModelAndView updateQuestion(int id, QuestionDTO questionDto, String opts) {
		ModelAndView modelAndView = new ModelAndView();
		String str = FAILED;
		try {
			QuestionDTO question = questionService.viewQuestionById(id);

			question.setAnswer(questionDto.getAnswer());
			question.setDifficulty(questionDto.getDifficulty());
			question.setTitle(questionDto.getTitle());
			question.setTopic(questionDto.getTopic());
			Map<Character, String> newOpts = new HashMap<>();
			String[] optStrings = opts.split(",");
			char ch = 'a';
			for (String opt : optStrings) {
				newOpts.put(ch, opt);
				ch++;

			}
			question.setOptions(newOpts);
			QuestionDTO dto = new QuestionDTO(question.getTitle(), question.getDifficulty(), question.getOptions(),
					question.getTopic(), question.getAnswer());
			dto.setQuestionNum(id);
			questionService.update(dto);
			str = "viewQ";
		} catch (QuestionLibException questNotFound) {
			str = "UpdateQuestionHere.jsp";
			log.error(questNotFound.getMessage());
		} catch (RuntimeException e) {
			log.error(e.getMessage());
		}
		modelAndView.setViewName(str);
		return modelAndView;
	}

	@RequestMapping("deleteQ")
	public ModelAndView delete(int id) {
		ModelAndView modelAndView = new ModelAndView();
		String str = FAILED;
		try {
			if (questionService.checkValidId(id)) {
				questionService.delete(id);
				str = "viewQ";
			} else {
				throw new QuestionLibException("Not a valid Id " + id);
			}

		} catch (QuestionLibException invalidId) {
			log.error(invalidId.getMessage());
			str = "DeleteQuestion.jsp";
		} catch (DataIntegrityViolationException e) {
			log.info("Cant delete beacuse this id is being refered as foreign key");
		} catch (RuntimeException e) {
			log.error(e.getMessage());
		} 
		modelAndView.setViewName(str);
		return modelAndView;
	}

}
