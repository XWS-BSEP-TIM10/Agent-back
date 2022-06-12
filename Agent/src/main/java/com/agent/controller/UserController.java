package com.agent.controller;

import com.agent.dto.ChangePasswordDTO;
import com.agent.dto.NewUserRequestDTO;
import com.agent.dto.NewUserResponseDTO;
import com.agent.exception.UserAlreadyExistsException;
import com.agent.exception.UserNotFoundException;
import com.agent.exception.WrongPasswordException;
import com.agent.model.User;
import com.agent.service.LoggerService;
import com.agent.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final LoggerService loggerService;

    public UserController(UserService userService) {
        this.userService = userService;
        this.loggerService = new LoggerService(this.getClass());
    }

    @PostMapping("/signup")
    public ResponseEntity<NewUserResponseDTO> addUser(@RequestBody @Valid NewUserRequestDTO newUserDto) {
        try {
            User newUser = userService.addNewUser(new User(newUserDto.getEmail(), newUserDto.getPassword()));
            if (newUser == null) {
                loggerService.userSigningUpFailed("Saving new user failed", newUserDto.getEmail());
                return ResponseEntity.internalServerError().build();
            }
            loggerService.userSignedUp(newUser.getEmail());
            return new ResponseEntity<>(new NewUserResponseDTO(newUser.getId(), newUser.getEmail()), HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            loggerService.userSigningUpFailed(e.getMessage(), newUserDto.getEmail());
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
            if(changedUser == null) {
                loggerService.passwordChangingFailed("Saving new password failed", SecurityContextHolder.getContext().getAuthentication().getName());
                return ResponseEntity.internalServerError().build();
            }
            loggerService.passwordChanged(SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            loggerService.passwordChangingFailed(e.getMessage(), SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.notFound().build();
        } catch (WrongPasswordException e) {
            loggerService.passwordChangingFailed(e.getMessage(), SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.badRequest().build();
        }
    }
}
