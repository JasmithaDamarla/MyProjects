package com.epam.dto;

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
public class AdminDTO {
	private Integer userId;
	@Size(min = 3 ,max =8 ,message="min length of 3 and max of 8")
	private String userName;
	@Size(min=4, message="min 4 characters")
	private String password;
	
	public AdminDTO( String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

}
