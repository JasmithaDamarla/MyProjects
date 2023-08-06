package com.epam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.customexceptions.FunctionalityException;
import com.epam.customexceptions.InvalidInputException;
import com.epam.customexceptions.SignUpFailedException;
import com.epam.customexceptions.UserNotFoundException;
import com.epam.dto.QuestionDTO;
import com.epam.dto.QuizDTO;
import com.epam.dto.UserDTO;
import com.epam.entities.Role;
import com.epam.service.interafaces.QuizService;
import com.epam.service.interafaces.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private QuizService quizService;
	@Autowired
	private Random random;

	private static final String USERHOME = "UserHome.jsp";

	@RequestMapping("/home")
	public String goHome() {
		return USERHOME;
	}

	@PostMapping("/registerUser")
	public ModelAndView registerUser(String username, String password, String confirm) {
		ModelAndView modelAndView = new ModelAndView();
		String str;
		UserDTO user = new UserDTO(username, confirm,List.of(new Role("USER")),null,0.0);
		try {
			userService.signUpUser(user);
			str = USERHOME;
		} catch (SignUpFailedException signUpFail) {
			log.error(signUpFail.getMessage());
			str = "RegisterUser.jsp";
		}
		modelAndView.setViewName(str);
		return modelAndView;
	}

	@PostMapping("/userLogin")
	public ModelAndView userVerify(String user, String username, String password) {
		ModelAndView modelAndView = new ModelAndView();
		String str = USERHOME;
		try {
			if (user.equals("admin")) {
				if (userService.isValidAdmin(username, password)) {
					str = "AdminHome.jsp";
				}
			} else {
				userService.isValidUser(username, password);
				str = "UserOperations.jsp";
				UserDTO nowUser = userService.viewUserByUserName(username);
				modelAndView.addObject("user", nowUser);
			}
		} catch (UserNotFoundException e) {
			log.error(e.getMessage());
		} catch (InvalidInputException adminExcep) {
			log.error(adminExcep.getMessage());
		}
		modelAndView.setViewName(str);
		return modelAndView;
	}

	@PostMapping("/addAdmin")
	public ModelAndView addAdmin(String username, String password, String confirm) {
		ModelAndView modelAndView = new ModelAndView();
		String str;
		UserDTO newAdmin = new UserDTO(username, confirm,List.of(new Role("ADMIN")),null,0.0);
		try {
			userService.signUpAdmin(newAdmin);
			str = "AdminHome.jsp";
		} catch (SignUpFailedException signUpFail) {
			log.error(signUpFail.getMessage());
			str = "AddAdmin.jsp";
		}
		modelAndView.setViewName(str);
		return modelAndView;
	}

	@GetMapping("/takeQuiz")
	public ModelAndView takeQuiz(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String str;
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("user");
		str = "StartQuiz.jsp";
		modelAndView.addObject("user", user);
		modelAndView.setViewName(str);
		return modelAndView;
	}

	@GetMapping("/viewMyScore")
	public ModelAndView myScore(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("user");
		if (user.getScore() == 0) {
			modelAndView.addObject("scoreValue", "You haven't taken any quiz so your score is 0");
		} else {
			modelAndView.addObject("scoreValue", "Congratualtions!!You have scored " + user.getScore());
		}
		modelAndView.setViewName("ViewMyScore.jsp");
		return modelAndView;
	}

	@GetMapping("/viewAllUsers")
	public ModelAndView allScores() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userList", userService.getUsers());
		modelAndView.setViewName("ViewAllScore.jsp");
		return modelAndView;
	}

	@GetMapping("/viewAllUsersAdmin")
	public ModelAndView allScoresAdmin() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userList", userService.getUsers());
		modelAndView.setViewName("AdminViewScores.jsp");
		return modelAndView;
	}

	@PostMapping("/startQuiz")
	public ModelAndView startQuiz(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		List<QuizDTO> list = quizService.viewQuiz();
		QuizDTO assignQuiz = list.get(random.nextInt(list.size()));
		Map<Integer, Character> answerMarkedList = new HashMap<>();
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("user");
		modelAndView.addObject("user", user);
		modelAndView.addObject("quiz", assignQuiz);
		modelAndView.addObject("answersMarked", answerMarkedList);
		modelAndView.setViewName("TakeQuiz.jsp");
		return modelAndView;

	}

	@PostMapping("/updateScore")
	protected ModelAndView doPostScore(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String str = "Failed.jsp";
		double score = 0.0;
		try {
			HttpSession session;
			session = request.getSession();
			QuizDTO quiz = (QuizDTO) session.getAttribute("quiz");
			UserDTO user = (UserDTO) session.getAttribute("user");
			double marksForEachQ = (double) quiz.getMarks() / (quiz.getQuestions().size());
			for (QuestionDTO question : quiz.getQuestions()) {
				String paramName = String.valueOf(question.getQuestionNum());
				Character selectedOption = request.getParameter(paramName).charAt(0);
				Character ans = question.getAnswer();
				if (selectedOption.equals(ans)) {
					score += marksForEachQ;
				}
			}
			user.setScore((score/quiz.getMarks())*100);
			user.setQuiz(quiz);
			UserDTO updateUser = new UserDTO(user.getUserName(), user.getPassword(), user.getQuiz(), user.getScore());
			updateUser.setUserId(user.getUserId());
			userService.update(updateUser);
			str = "UserOperations.jsp";

		} catch (FunctionalityException func) {
			log.error(func.getMessage());
		} catch (UserNotFoundException notfound) {
			log.error(notfound.getMessage());
		} catch (RuntimeException e) {
			log.error(e.getMessage());
		}
		modelAndView.setViewName(str);
		return modelAndView;
	}

}
