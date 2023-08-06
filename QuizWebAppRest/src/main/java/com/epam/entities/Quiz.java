package com.epam.entities;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "QuizTable")
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer quizId;
	private String quizTitle;
	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Question.class)
	private List<Question> questions;
	private Integer marks;


	public Quiz(String quizTitle, List<Question> questionNum, int marks) {
		this.quizTitle = quizTitle;
		this.questions = questionNum;
		this.marks = marks;
	}

	
}
