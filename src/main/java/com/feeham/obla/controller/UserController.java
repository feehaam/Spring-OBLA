package com.feeham.obla.controller;

import com.feeham.obla.model.auths.LoginRequestModel;
import com.feeham.obla.model.userdto.UserCreateDTO;
import com.feeham.obla.model.userdto.UserUpdateDTO;
import com.feeham.obla.service.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody UserCreateDTO userCreateDTO){
        userService.create(userCreateDTO);
        return new ResponseEntity<>("Account successfully registered", HttpStatus.CREATED);
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestModel loginRequest){
        return ResponseEntity.ok(loginRequest);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        return new ResponseEntity<>(userService.readById(userId), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/books")
    public ResponseEntity<?> getBorrowedBooks(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getBorrowed(userId));
    }

    @GetMapping("/users/{userId}/borrowed-books")
    public ResponseEntity<?> getBorrowedBooksCurrent(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getBorrowedCurrently(userId));
    }

    // Optional
    @GetMapping("/users/{userId}/history")
    public ResponseEntity<?> getHistory(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getHistory(userId));
    }

    // Partial
    @PutMapping("/user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserUpdateDTO userUpdateDTO){
        userUpdateDTO.setUserId(userId);
        userService.update(userUpdateDTO);
        return new ResponseEntity<>("User information updated.", HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        userService.delete(userId);
        return new ResponseEntity<>("User removed", HttpStatus.OK);
    }
}
