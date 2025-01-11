package com.rohit.springsecurityjwttokendemo2.repo;

import com.rohit.springsecurityjwttokendemo2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
