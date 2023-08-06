package com.epam.entities;

import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "questions")
public class Question {
	@Id
	@Column(name = "Qnum")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer questionNum;
	
	private String title;
	private String difficulty;

	@ElementCollection
	private Map<Character, String> options;
	private String topic;
	private Character answer;

	private String getOptionValues() {
		String optionValues = "   ";
		for (Map.Entry<Character, String> map : options.entrySet()) {
			optionValues = optionValues.concat(map.getKey() + ") " + map.getValue() + "\n   ");
		}
		return optionValues;
	}

	public String printQuestion() {
		String optionValues = getOptionValues();
		return title + " ( Difficulty : " + difficulty + " , Topic : " + topic + " )\n" + optionValues + "\n";
	}

	public String displayQuestion() {
		String ques = questionNum + ") " + title + " (difficulty : " + difficulty + ", Topic : " + topic + ", answer : "
				+ answer + " )";
		String optionValues = getOptionValues();
		ques += "\n" + optionValues + "\n";
		return ques;
	}

	public Question(String title, String difficulty, Map<Character, String> opt, String topic,
			Character answer) {
		this.title = title;
		this.difficulty = difficulty;
		this.options = opt;
		this.topic = topic;
		this.answer = answer;
	}

	public Integer getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}
}
