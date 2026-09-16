package com.cper.REST_API_DTO_Validation.service;

import com.cper.REST_API_DTO_Validation.entity.User;
import com.cper.REST_API_DTO_Validation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> createUser(User newUser) {
        User isSaved =  userRepository.save(newUser);
        if (isSaved == null) return Optional.empty();
        return Optional.of(isSaved);
    }

    public Optional<User> getUserbyEmail(String email) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) return Optional.empty();
        return Optional.of(existingUser);
    }

    public Optional<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) return Optional.empty();
        return Optional.of(users);
    }

    public Optional<User> deleteUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) return Optional.empty();
        userRepository.delete(user);
        return Optional.of(user);
    }

    public Optional<User> updateUser(User user, String OldEmail) {
        User existingUser = userRepository.findByEmail(OldEmail);
        if (existingUser == null) return Optional.empty();

        if (user.getUsername() != null) existingUser.setUsername(user.getUsername());
        if (user.getPassword() != null) existingUser.setPassword(user.getPassword());
        if (user.getEmail() != null) existingUser.setEmail(user.getEmail());

        existingUser.setUpdatedAt(LocalDateTime.now());
        userRepository.save(existingUser);
        return Optional.of(existingUser);
    }
}
