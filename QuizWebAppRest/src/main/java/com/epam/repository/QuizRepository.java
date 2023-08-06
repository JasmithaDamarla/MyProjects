package com.epam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.entities.Quiz;

public interface QuizRepository  extends JpaRepository<Quiz, Integer>{

}
