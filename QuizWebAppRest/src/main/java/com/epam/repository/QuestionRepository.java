package com.epam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.entities.Question;

public interface QuestionRepository extends JpaRepository<Question,Integer>{

}
