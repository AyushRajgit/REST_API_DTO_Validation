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

    @PostMapping
    private ResponseEntity<createUserResponseDTO> create(@Valid @RequestBody createUserRequestDTO userRequestDTO) {
        User newUser = mapToEntity_CreateRequestDTO(userRequestDTO);
        User createdUser = userService.createUser(newUser);

        createUserResponseDTO createdUserRes = mapToDTO_CreateResponseDTO(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserRes);
    }

    @GetMapping("/{email}")
    private ResponseEntity<createUserResponseDTO> get(@PathVariable String email) {
        User user = userService.getUserbyEmail(email);

        createUserResponseDTO existingUserRes = mapToDTO_CreateResponseDTO(user);
        return ResponseEntity.status(HttpStatus.OK).body(existingUserRes);
    }

    @GetMapping
    private ResponseEntity<List<createUserResponseDTO>> getAll() {
        List<User> users = userService.getAllUsers();

        List<createUserResponseDTO> allUsers = mapToDTOMultipleUsers(users);
        return ResponseEntity.status(HttpStatus.OK).body(allUsers);
    }

    @PutMapping
    private ResponseEntity<updateUserResponseDTO> update(@Valid @RequestBody updateUserRequestDTO userRequestDTO) {
        User user = mapToEntity_UpdateRequestDTO(userRequestDTO);
        User updatedUser = userService.updateUser(user, userRequestDTO.getEmail());

        updateUserResponseDTO updatedUserDTO = mapToDTO_UpdateResponseDTO(updatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUserDTO);
    }

    @DeleteMapping("/{email}")
    private ResponseEntity<deleteResponseDTO> delete(@PathVariable String email) {
        User user = userService.deleteUser(email);

        deleteResponseDTO deleteResDTO = mapToDTO_DeleteResponseDTO(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
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

    private List<createUserResponseDTO>  mapToDTOMultipleUsers(List<User> users) {
        List<createUserResponseDTO> createUserResponseDTOList = new ArrayList<>();
        for (User user : users) {
            createUserResponseDTO createUserResponseDTO = mapToDTO_CreateResponseDTO(user);
            createUserResponseDTOList.add(createUserResponseDTO);
        }
        return  createUserResponseDTOList;
    }
}
