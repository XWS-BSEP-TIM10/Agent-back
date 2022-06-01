package com.agent.controller;

import com.agent.dto.NewUserRequestDto;
import com.agent.dto.NewUserResponseDto;
import com.agent.exception.UserAlreadyExistsException;
import com.agent.model.User;
import com.agent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<NewUserResponseDto> addUser(@RequestBody NewUserRequestDto newUserDto) {
        try {
            User newUser = userService.save(new User(newUserDto.getEmail(), newUserDto.getPassword()));
            if(newUser == null)
                return ResponseEntity.internalServerError().build();
            NewUserResponseDto userDto = new NewUserResponseDto(newUser.getId(), newUser.getEmail());
            return new ResponseEntity<>(userDto, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
