package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.example.demo.model.User;

@Repository
public interface Userrepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
