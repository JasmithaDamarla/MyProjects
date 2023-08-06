package com.epam.dto;

import java.util.List;

import com.epam.entities.Role;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private Integer userId;
	@Size(min = 3 ,max =8 ,message="min length of 3 and max of 8")
	private String userName;
	@Size(min=4, message="min 4 characters")
	private String password;
	private List<Role> roles;
	private boolean disabled;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
	private QuizDTO quiz;
	private double score;
	
	public UserDTO(String userName, String password, QuizDTO quiz, double score) {
		super();
		this.userName = userName;
		this.password = password;
		this.quiz = quiz;
		this.score = score;
	}

	
	public UserDTO(String userName, String password, List<Role> roles, QuizDTO quiz, double score) {
		super();
		this.userName = userName;
		this.password = password;
		this.roles=roles;
		this.quiz = quiz;
		this.score = score;
	}
	
}