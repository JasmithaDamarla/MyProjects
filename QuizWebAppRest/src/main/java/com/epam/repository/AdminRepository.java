package com.epam.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.epam.entities.Admin;

public interface AdminRepository extends CrudRepository<Admin, Integer>{
	Optional<Admin> findByUserName(String userName);

}

