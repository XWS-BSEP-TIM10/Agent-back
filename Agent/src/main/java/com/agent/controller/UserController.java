package com.agent.controller;

import com.agent.dto.ChangePasswordDTO;
import com.agent.dto.NewUserRequestDTO;
import com.agent.dto.NewUserResponseDTO;
import com.agent.exception.UserAlreadyExistsException;
import com.agent.exception.UserNotFoundException;
import com.agent.exception.WrongPasswordException;
import com.agent.model.User;
import com.agent.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO) {
        try {
            User changedUser = userService.changePassword(changePasswordDTO);
            if(changedUser == null)
                return ResponseEntity.internalServerError().build();
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (WrongPasswordException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
