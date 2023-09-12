package com.feeham.obla.controller;

import com.feeham.obla.model.auths.LoginRequestModel;
import com.feeham.obla.model.auths.LoginResponseModel;
import com.feeham.obla.model.userdto.UserCreateDTO;
import com.feeham.obla.model.userdto.UserUpdateDTO;
import com.feeham.obla.service.interfaces.UserService;
import com.feeham.obla.utilities.token.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody UserCreateDTO userCreateDTO){
        userService.create(userCreateDTO);
        return new ResponseEntity<>("Account successfully registered", HttpStatus.CREATED);
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login (@RequestBody LoginRequestModel userDto) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDto.getEmail(),
                            userDto.getPassword()
                    )
            );
            var user = userService.readByEmail(userDto.getEmail());
            List<String> roles = new ArrayList<String>();
            roles.add("ROLE_" + user.getRole().getRoleName());
            var jwtToken = JWTUtils.generateToken(user.getEmail(), roles);
            return new ResponseEntity<>(LoginResponseModel.builder()
                    .username(user.getEmail())
                    .bearerToken(jwtToken)
                    .build(), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.BAD_REQUEST);
        }  catch (Exception e) {
            return new ResponseEntity<>("Failed to authenticate", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
