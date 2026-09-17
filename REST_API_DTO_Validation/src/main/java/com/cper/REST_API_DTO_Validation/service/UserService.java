package com.cper.REST_API_DTO_Validation.service;

import com.cper.REST_API_DTO_Validation.entity.User;
import com.cper.REST_API_DTO_Validation.exceptions.DuplicateEmailException;
import com.cper.REST_API_DTO_Validation.exceptions.UnableToCreateUserException;
import com.cper.REST_API_DTO_Validation.exceptions.UnableToUpdateUserException;
import com.cper.REST_API_DTO_Validation.exceptions.UserNotFoundException;
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

    public User createUser(User newUser) {
        if (emailExists(newUser.getEmail())) {
            throw new DuplicateEmailException("User with email : " + newUser.getEmail() + " already exists");
        }

        User savedUser =  userRepository.save(newUser);
        if (savedUser == null) {
            throw new UnableToCreateUserException("Unable to create user");
        }
        return savedUser;
    }

    public User getUserbyEmail(String email) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            throw new UserNotFoundException("Unable to find user with email : " + email);
        }
        return existingUser;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User deleteUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("Unable to delete user with email : " + email);
        }
        userRepository.delete(user);
        return user;
    }

    public User updateUser(User user, String OldEmail) {
        User existingUser = userRepository.findByEmail(OldEmail);
        if (existingUser == null) {
            throw new UserNotFoundException("User not found with email : " + OldEmail);
        }

        if (emailExists(user.getEmail())) {
            throw new DuplicateEmailException("User with email : " + user.getEmail() + "already exists");
        }

        if (user.getUsername() != null) existingUser.setUsername(user.getUsername());
        if (user.getPassword() != null) existingUser.setPassword(user.getPassword());
        if (user.getEmail() != null) existingUser.setEmail(user.getEmail());

        existingUser.setUpdatedAt(LocalDateTime.now());
        User saved = userRepository.save(existingUser);

        if (saved == null) {
            throw new UnableToUpdateUserException("Unable to update user with email : " + OldEmail);
        }
        return existingUser;
    }

    private boolean  emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
