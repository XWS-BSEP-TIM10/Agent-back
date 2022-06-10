package com.agent.controller;

import com.agent.dto.NewUserRequestDTO;
import com.agent.dto.NewUserResponseDTO;
import com.agent.exception.UserAlreadyExistsException;
import com.agent.model.User;
import com.agent.service.AuthenticationService;
import com.agent.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<NewUserResponseDTO> addUser(@RequestBody @Valid NewUserRequestDTO newUserDto) {
        try {
            User newUser = userService.addNewUser(new User(newUserDto.getEmail(), newUserDto.getPassword()));
            if (newUser == null)
                return ResponseEntity.internalServerError().build();
            return new ResponseEntity<>(new NewUserResponseDTO(newUser.getId(), newUser.getEmail()), HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
