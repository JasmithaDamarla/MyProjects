package com.epam;

import java.util.List;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.epam.config.RsaKeyProperties;
import com.epam.entities.Role;
import com.epam.entities.User;
import com.epam.repository.UserRepository;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class QuizWebApplication {

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	
//	@Bean
//	CommandLineRunner commandLineRunner(UserRepository users, PasswordEncoder encoder){
//		return args -> {
//			users.save(new User("user", encoder.encode("User@epam"),List.of(new Role("ROLE_USER")),null,0 ));
//			users.save(new User("admin", encoder.encode("Admin@epam"),List.of(new Role("ROLE_USER"),new Role("ROLE_ADMIN")),null,0 ));
//			};
//	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(QuizWebApplication.class, args);
	}
	
	

	@Bean
	public Random getRandom() {
		return new Random();
	}
	
}
