package com.example.demo.service;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.model.User;
import com.example.demo.repository.Userrepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final Userrepository userrepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(Userrepository userrepository,
            PasswordEncoder passwordEncoder) {
        this.userrepository = userrepository;
        this.passwordEncoder = passwordEncoder;
    }

    @CacheEvict(value = "all_users", allEntries = true)
    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        User savedUser = userrepository.save(user);

        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    @Cacheable(value = "users", key = "#id")
    public UserResponseDTO getUserById(Long id) {
        User user = userrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }

    @Cacheable(value = "all_users")
    public List<UserResponseDTO> getAllUsers() {
        return userrepository.findAll().stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getEmail()))
                .toList();
    }

    @CachePut(value = "users", key = "#id")
    @CacheEvict(value = "all_users", allEntries = true)
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User existingUser = userrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existingUser.setName(userRequestDTO.getName());
        existingUser.setEmail(userRequestDTO.getEmail());
        existingUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        User savedUser = userrepository.save(existingUser);

        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    @Caching(evict = {
            @CacheEvict(value = "users", key = "#id"),
            @CacheEvict(value = "all_users", allEntries = true)
    })
    public UserResponseDTO deleteUser(Long id) {
        User user = userrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        userrepository.deleteById(id);

        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }
}
