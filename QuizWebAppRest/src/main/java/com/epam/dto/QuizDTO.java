package com.epam.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class QuizDTO {
	
	private int quizId;
	@Size(min=3,max=90,message="Min 3 letters and max of 20 letters")
	private String quizTitle;
	@NotNull(message = "add atleast one questions")
	private List<QuestionDTO> questions;
	@Min(value=1,message="marks should be assigned")
	private int marks;
	
	public QuizDTO( String quizTitle, List<QuestionDTO> questions, int marks) {
		super();
		this.quizTitle = quizTitle;
		this.questions = questions;
		this.marks = marks;
	}



}
