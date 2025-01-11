package com.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.entity.UserEntity;

import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {

	public Optional<UserEntity> findByEmail(String email);
	
	
}
