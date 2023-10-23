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
import org.springframework.security.crypto.password.PasswordEncoder;
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

    /**
     * Register a new user account by providing user details.
     *
     * @param userCreateDTO The details of the user to register.
     * @return A ResponseEntity with a success message and HTTP status 201 (Created).
     */
    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody UserCreateDTO userCreateDTO) {
        userCreateDTO.setRole("CUSTOMER");
        userService.create(userCreateDTO);
        return new ResponseEntity<>("Account successfully registered", HttpStatus.CREATED);
    }

    @PostMapping("/user/register/admin")
    public ResponseEntity<?> registerAsAdmin(@RequestBody UserCreateDTO userCreateDTO, @RequestParam String permissionToken) {
        if(permissionToken == null || !permissionToken.equals("12345"))
            return new ResponseEntity<>("Invalid permission token!", HttpStatus.BAD_REQUEST);
        userCreateDTO.setRole("ADMIN");
        userService.create(userCreateDTO);
        return new ResponseEntity<>("Account successfully registered", HttpStatus.CREATED);
    }

//    /**
//     * Log in a user by providing email and password for authentication.
//     *
//     * @param userDto The login request model containing email and password.
//     * @return A ResponseEntity containing a login response model with a bearer token.
//     * @throws Exception If authentication fails.
//     */
//    @PostMapping("/user/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequestModel userDto) throws Exception {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            userDto.getEmail(),
//                            userDto.getPassword()
//                    )
//            );
//            var user = userService.readByEmail(userDto.getEmail());
//            List<String> roles = new ArrayList<String>();
//            roles.add("ROLE_" + user.getRole().getRoleName());
//            var jwtToken = JWTUtils.generateToken(user.getEmail(), roles);
//            return new ResponseEntity<>(LoginResponseModel.builder()
//                    .username(user.getEmail())
//                    .bearerToken(jwtToken)
//                    .build(), HttpStatus.OK);
//        } catch (BadCredentialsException e) {
//            return new ResponseEntity<>("Invalid email or password", HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to authenticate", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    /**
     * Get user details by specifying the user ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return A ResponseEntity containing user details.
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.readById(userId), HttpStatus.OK);
    }
    // Get by email
    @GetMapping("/users/profile")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        return new ResponseEntity<>(userService.readByEmail(email), HttpStatus.OK);
    }

    /**
     * Get the list of books borrowed by a user by specifying the user ID.
     *
     * @param userId The ID of the user to get borrowed books for.
     * @return A ResponseEntity containing a list of borrowed books.
     */
    @GetMapping("/users/{userId}/books")
    public ResponseEntity<?> getBorrowedBooks(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getBorrowed(userId));
    }

    /**
     * Get the list of books currently borrowed by a user by specifying the user ID.
     *
     * @param userId The ID of the user to get currently borrowed books for.
     * @return A ResponseEntity containing a list of currently borrowed books.
     */
    @GetMapping("/users/{userId}/borrowed-books")
    public ResponseEntity<?> getBorrowedBooksCurrent(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getBorrowedCurrently(userId));
    }

    /**
     * Get the borrowing history of a user by specifying the user ID.
     *
     * @param userId The ID of the user to get borrowing history for.
     * @return A ResponseEntity containing a list of borrowed book details.
     */
    @GetMapping("/users/{userId}/history")
    public ResponseEntity<?> getHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getHistory(userId));
    }

    /**
     * Update user information by specifying the user ID and providing updated user details.
     *
     * @param userId        The ID of the user to update.
     * @param userUpdateDTO The updated user details.
     * @return A ResponseEntity with a success message.
     */
    @PutMapping("/user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserUpdateDTO userUpdateDTO) {
        userUpdateDTO.setUserId(userId);
        userService.update(userUpdateDTO);
        return new ResponseEntity<>("User information updated.", HttpStatus.OK);
    }

    /**
     * Delete a user account by specifying the user ID.
     *
     * @param userId The ID of the user to delete.
     * @return A ResponseEntity with a success message.
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return new ResponseEntity<>("User removed", HttpStatus.OK);
    }
}
