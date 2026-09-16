package com.cper.REST_API_DTO_Validation.controller;

import com.cper.REST_API_DTO_Validation.dto.*;
import com.cper.REST_API_DTO_Validation.entity.User;
import com.cper.REST_API_DTO_Validation.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserRepository(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    private ResponseEntity<createUserResponseDTO> create(@Valid @RequestBody createUserRequestDTO userRequestDTO) {
        User newUser = mapToEntity_CreateRequestDTO(userRequestDTO);
        Optional<User> createdUser = userService.createUser(newUser);

        if (!createdUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        createUserResponseDTO createdUserRes = mapToDTO_CreateResponseDTO(createdUser.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserRes);
    }

    @GetMapping("/get")
    private ResponseEntity<createUserResponseDTO> get(@RequestParam String email) {
        Optional<User> user = userService.getUserbyEmail(email);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        createUserResponseDTO existingUserRes = mapToDTO_CreateResponseDTO(user.get());
        return ResponseEntity.status(HttpStatus.OK).body(existingUserRes);
    }

    @GetMapping("/getAll")
    private ResponseEntity<List<createUserResponseDTO>> getAll() {
        Optional<List<User>> users = userService.getAllUsers();
        if (!users.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Optional<List<createUserResponseDTO>> allUsers = mapToDTOMultipleUsers(users.get());
        return ResponseEntity.status(HttpStatus.OK).body(allUsers.get());
    }

    @PutMapping("/update")
    private ResponseEntity<updateUserResponseDTO> update(@Valid @RequestBody updateUserRequestDTO userRequestDTO) {
        User user = mapToEntity_UpdateRequestDTO(userRequestDTO);
        Optional<User> updatedUser = userService.updateUser(user, userRequestDTO.getEmail());

        if (!updatedUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        updateUserResponseDTO updatedUserDTO = mapToDTO_UpdateResponseDTO(updatedUser.get());
        return ResponseEntity.status(HttpStatus.OK).body(updatedUserDTO);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<deleteResponseDTO> delete(@RequestParam String email) {
        Optional<User> user = userService.deleteUser(email);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        deleteResponseDTO deleteResDTO = mapToDTO_DeleteResponseDTO(user.get());
        return ResponseEntity.status(HttpStatus.OK).body(deleteResDTO);
    }

    private User mapToEntity_CreateRequestDTO(createUserRequestDTO createUserRequestDTO) {
        User user = new User();
        user.setUsername(createUserRequestDTO.getUsername());
        user.setEmail(createUserRequestDTO.getEmail());
        user.setPassword(createUserRequestDTO.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    private User mapToEntity_UpdateRequestDTO(updateUserRequestDTO updateUserRequestDTO) {
        User user = new User();
        user.setUsername(updateUserRequestDTO.getUsername());
        user.setEmail(updateUserRequestDTO.getUpdatedEmail());
        user.setPassword(updateUserRequestDTO.getPassword());
        return user;
    }

    private createUserResponseDTO mapToDTO_CreateResponseDTO(User user) {
        createUserResponseDTO createUserResDTO = new createUserResponseDTO();
        createUserResDTO.setUsername(user.getUsername());
        createUserResDTO.setEmail(user.getEmail());
        createUserResDTO.setId(user.getId());
        createUserResDTO.setCreatedAt(user.getCreatedAt());
        createUserResDTO.setUpdatedAt(user.getUpdatedAt());
        return createUserResDTO;
    }

    private updateUserResponseDTO mapToDTO_UpdateResponseDTO(User user) {
        updateUserResponseDTO updateUserResDTO = new updateUserResponseDTO();
        updateUserResDTO.setUsername(user.getUsername());
        updateUserResDTO.setEmail(user.getEmail());
        updateUserResDTO.setId(user.getId());
        updateUserResDTO.setUpdatedAt(user.getUpdatedAt());
        updateUserResDTO.setPassword(user.getPassword());
        updateUserResDTO.setMessage("User updated successfully");
        return updateUserResDTO;
    }

    private deleteResponseDTO mapToDTO_DeleteResponseDTO(User user) {
        deleteResponseDTO deleteResDTO = new deleteResponseDTO();
        deleteResDTO.setUsername(user.getUsername());
        deleteResDTO.setEmail(user.getEmail());
        deleteResDTO.setMessage("User deleted successfully");
        return deleteResDTO;
    }

    private Optional<List<createUserResponseDTO>>  mapToDTOMultipleUsers(List<User> users) {
        List<createUserResponseDTO> createUserResponseDTOList = new ArrayList<>();
        for (User user : users) {
            createUserResponseDTO createUserResponseDTO = mapToDTO_CreateResponseDTO(user);
            createUserResponseDTOList.add(createUserResponseDTO);
        }
        return  Optional.of(createUserResponseDTOList);
    }
}
