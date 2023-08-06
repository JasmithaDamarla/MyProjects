package com.epam.dto;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class QuestionDTO {
	private int questionNum;
	@Size(min=3,max=90,message="Invalid title! Min 3 letters and max of 20 letters")
	private String title;
	@Size(min=4,max=6,message="Invalid difficulty! should be one of easy, medium or hard")
	private String difficulty;
	@NotEmpty(message = "Invalid options! please provide atleast one option")
	private Map<Character, String> options;
	@NotBlank(message = "Invalid topic! please provice the topic.")
	private String topic;
	@NotNull(message = "Invalid answer! please mention the answer")
	private Character answer;
		
	public QuestionDTO(String title, String difficulty, Map<Character, String> opt, String topic,
			Character answer) {
		this.title = title;
		this.difficulty = difficulty;
		this.options = opt;
		this.topic = topic;
		this.answer = answer;
	}
	
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

}