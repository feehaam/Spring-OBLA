package com.feeham.obla.controller;

import com.feeham.obla.model.auths.LoginRequestModel;
import com.feeham.obla.model.userdto.UserCreateDTO;
import com.feeham.obla.model.userdto.UserUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody UserCreateDTO userCreateDTO){
        return ResponseEntity.ok(userCreateDTO);
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestModel loginRequest){
        return ResponseEntity.ok(loginRequest);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/users/{userId}/books")
    public ResponseEntity<?> getBooksBorrowedByUser(@PathVariable Long userId){
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/users/{userId}/borrowed-books")
    public ResponseEntity<?> getBooksBorrowedCurrentlyByUser(@PathVariable Long userId){
        return ResponseEntity.ok(userId);
    }

    // Optional
    @GetMapping("/users/{userId}/history")
    public ResponseEntity<?> getUserBorrowingHistory(@PathVariable Long userId){
        return ResponseEntity.ok(userId);
    }

    // Partial

    @PutMapping("/user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserUpdateDTO userUpdateDTO){
        return ResponseEntity.ok(userId);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        return ResponseEntity.ok(userId);
    }
}
