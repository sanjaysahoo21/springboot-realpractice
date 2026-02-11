package com.example.demo.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.http.HttpStatus;

import com.example.demo.service.UserService;
import com.example.demo.dto.UserRequestDTO;
import com.example.demo.repository.Userrepository;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/users/api")
public class UserController {

    private final UserService userService;
    private final Userrepository userrepository;

    public UserController(UserService userService, Userrepository userrepository) {
        this.userService = userService;
        this.userrepository = userrepository;
    }

    @PostMapping("/add")
    public ResponseEntity<?> setUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {

        if(userrepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userService.saveUser(userRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {

        if (id == null || id <= 0 || userrepository.findById(id).isEmpty()) {
            return new ResponseEntity<>("User not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllUsers() {

        if (userrepository.findAll().isEmpty()) {
            return new ResponseEntity<>("No users found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {

        if (id == null || id <= 0 || userrepository.findById(id).isEmpty()) {
            return new ResponseEntity<>("User not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userService.updateUser(id, userRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        if (id == null || id <= 0 || userrepository.findById(id).isEmpty()) {
            return new ResponseEntity<>("User not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }
}
