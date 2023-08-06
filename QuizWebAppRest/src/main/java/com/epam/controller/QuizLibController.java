package com.epam.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.customexceptions.InvalidInputException;
import com.epam.customexceptions.QuestionLibException;
import com.epam.customexceptions.QuizLibException;
import com.epam.dto.QuestionDTO;
import com.epam.dto.QuizDTO;
import com.epam.entities.Question;
import com.epam.service.interafaces.QuestionService;
import com.epam.service.interafaces.QuizService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class QuizLibController {
	@Autowired
	private QuizService quizService;
	@Autowired
	private QuestionService questionService;
	private static final  String QUIZHOME ="QuizHome.jsp";

	@PostMapping(value = "/quizCreate")
	public ModelAndView createQuiz(String title, int marks, HttpServletRequest request) throws QuizLibException {
		String str = "FailedQuiz.jsp";
		ModelAndView modelAndView = new ModelAndView();
		try {
			String[] questionStrings = request.getParameterValues("list");

			if (questionStrings == null || questionStrings.length == 0) {
				throw new InvalidInputException("Please select at least one question.");
			}
			List<QuestionDTO> questionsList = new ArrayList<>();
			for (String questionString : questionStrings) {
				int questionId = Integer.parseInt(questionString);
				QuestionDTO question = questionService.viewQuestionById(questionId);
				questionsList.add(question);
			}

			QuizDTO quiz = new QuizDTO(title, questionsList, marks);
			quizService.create(quiz);
			log.info("Created quiz successfully!!");
			str = QUIZHOME;
		} catch (QuizLibException | InvalidInputException e) {
			log.error(e.getMessage());
		} catch (QuestionLibException questionNotFound) {
			log.error(questionNotFound.getMessage());
		} 
		modelAndView.setViewName(str);
		return modelAndView;
	}

	@GetMapping("/allQuestions")
	public ModelAndView getAllQuestions() {
		ModelAndView modelAndView = new ModelAndView();
		List<Question> questionList = questionService.view();
		modelAndView.addObject("questionsList", questionList);
		modelAndView.setViewName("CreateQuiz.jsp");
		return modelAndView;
	}
	
	@GetMapping("/displayAllIdAndSelectId")
	public ModelAndView viewQuiz() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("displayIds", quizService.viewQuiz());
		modelAndView.setViewName("ViewQuiz.jsp");
		return modelAndView;
	}

	@GetMapping("/displayAllIdAndSelectIdUpdate")
	public ModelAndView updateQuizId() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("displayIdsAndSelect", quizService.viewQuiz());
		modelAndView.setViewName("UpdateQuiz.jsp");
		return modelAndView;
	}
	
	@GetMapping("/allQuiz")
	public ModelAndView viewAllQuiz() {
		ModelAndView modelAndView = new ModelAndView();
		List<QuizDTO> quizList = quizService.viewQuiz();
		modelAndView.addObject("allQuizList", quizList);
		modelAndView.setViewName("ViewAllQuiz.jsp");
		return modelAndView;
	}

	@GetMapping("/quizView")
	public ModelAndView readQuiz(int id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (quizService.checkValidQuizId(id)) {
				QuizDTO view = quizService.viewById(id);
				modelAndView.addObject("viewQuiz", view);
				modelAndView.setViewName("DisplayQuiz.jsp");
			} else {
				modelAndView.setViewName("ViewQuiz.jsp");
				log.info("Not a valid Id");
			}
		} catch (QuizLibException quizNotFound) {
			log.error(quizNotFound.getMessage());
		}
		return modelAndView;

	}

	@GetMapping("/updateQuizGetId")
	public ModelAndView setQuestionInUpdate(int id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (quizService.checkValidQuizId(id)) {
				QuizDTO quiz = quizService.viewById(id);
				modelAndView.addObject("quiz", quiz);
				modelAndView.addObject("questionsList", questionService.view());
				modelAndView.setViewName("UpdateQuizHere.jsp");
			} else {
				modelAndView.setViewName("UpdateQuiz.jsp");
				log.info("Not a valid Id");
			}
		} catch (QuizLibException quizNotFound) {
			log.error(quizNotFound.getMessage());
		}
		return modelAndView;
	}

	@GetMapping("/quizUpdate")
	public ModelAndView updateQuiz(int quizId, String title, int marks, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String str = "FailedQuiz.jsp";
		try {
			if (quizService.checkValidQuizId(quizId)) {

				String[] questionStrings = request.getParameterValues("list");
				if (questionStrings == null || questionStrings.length == 0) {
					throw new InvalidInputException("Please select at least one question.");
				}
				List<QuestionDTO> questionsList = new ArrayList<>();
				for (String questionString : questionStrings) {
					int questionId = Integer.parseInt(questionString);
					QuestionDTO question = questionService.viewQuestionById(questionId);
					questionsList.add(question);

				}
				log.info("done getting data");
				QuizDTO quiz = quizService.viewById(quizId);
				quiz.setMarks(marks);
				quiz.setQuestions(questionsList);
				quiz.setQuizTitle(title);
				quiz.setQuizId(quizId);
				log.info("done {}",quiz);
				quizService.update(quiz);
				log.info("updated data");
					str = "allQuiz";

			} else {
				str = QUIZHOME;
				log.warn("Not a valid quizId");
			}

		} catch (InvalidInputException e) {
			log.error(e.getMessage());
		} catch (QuizLibException quizNotFound) {
			log.error(quizNotFound.getMessage());
		} catch (QuestionLibException questionNotFound) {
			log.error(questionNotFound.getMessage());
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		modelAndView.setViewName(str);
		return modelAndView;
	}
	
	@GetMapping("/quizIdsForDelete")
	public ModelAndView getIdsForDeleteQuiz() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("displayIds", quizService.viewQuiz());
		modelAndView.setViewName("DeleteQuiz.jsp");
		return modelAndView;
	}

	@GetMapping("/quizDelete")
	public ModelAndView delete(int id) {
		ModelAndView modelAndView = new ModelAndView();
		String str;
		try {
			if (quizService.checkValidQuizId(id)) {
				quizService.delete(id);
				str = "allQuiz";

			} else {
				str = QUIZHOME;
			}
		} catch (Exception e) {
			log.error(e.toString());
			str = "Failed.jsp";
		}
		modelAndView.setViewName(str);
		return modelAndView;
	}

}
