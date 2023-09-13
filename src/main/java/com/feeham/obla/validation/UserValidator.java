package com.feeham.obla.validation;

import com.feeham.obla.entity.Role;
import com.feeham.obla.entity.User;
import com.feeham.obla.exception.InvalidEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class UserValidator {

    // Define a list of validation rules to explain the validation criteria.
    private static final List<String> RULES = new ArrayList<>();
    static {
        RULES.add("- User ID must be non-negative");
        RULES.add("- First name must be at least 2 characters long and no special characters are allowed except for space");
        RULES.add("- Last name must be at least 4 characters long and no special characters are allowed except for space");
        RULES.add("- The email field must be a valid email");
        RULES.add("- Password must be at least 6 characters long");
        RULES.add("- Role must be either ADMIN or CUSTOMER");
    }

    private final PasswordEncoder passwordEncoder;

    // Constructor injection of PasswordEncoder to encode passwords.
    public UserValidator(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    // This method performs validation on a User entity.
    public void validate(User user) throws InvalidEntityException{
        List<String> violations = new ArrayList<>();

        // Validate the user's attributes one by one.
        validateUserId(user.getUserId(), violations);
        validateFirstName(user.getFirstName(), violations);
        validateLastName(user.getLastName(), violations);
        validateEmail(user.getEmail(), violations);
        validatePassword(user.getPassword(), violations);
        validateRole(user.getRole(), violations);

        // If there are violations, throw an InvalidEntityException with details.
        if (!violations.isEmpty()) {
            throw new InvalidEntityException("User", "User validation failed", createValidationData(violations));
        }

        // Encode the user's password before saving it.
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    private void validateUserId(Long userId, List<String> violations) {
        if (userId != null && userId < 0) {
            violations.add(RULES.get(0));
        }
    }

    private void validateFirstName(String firstName, List<String> violations) {
        if (firstName == null || firstName.length() < 2 || !Pattern.matches("^[a-zA-Z ]*$", firstName)) {
            violations.add(RULES.get(1));
        }
    }

    private void validateLastName(String lastName, List<String> violations) {
        if (lastName == null || lastName.length() < 4 || !Pattern.matches("^[a-zA-Z ]*$", lastName)) {
            violations.add(RULES.get(2));
        }
    }

    private void validateEmail(String email, List<String> violations) {
        if (email == null || !Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) {
            violations.add(RULES.get(3));
        }
    }

    private void validatePassword(String password, List<String> violations) {
        if (password == null || password.length() < 6) {
            violations.add(RULES.get(4));
        }
    }

    private void validateRole(Role role, List<String> violations) {
        if (role == null || (!role.getRoleName().equals("ADMIN") && !role.getRoleName().equals("CUSTOMER"))) {
            violations.add(RULES.get(5));
        }
    }

    // Create a map containing validation data for reporting.
    private Map<String, List<String>> createValidationData(List<String> violations) {
        Map<String, List<String>> validationData = new HashMap<>();
        validationData.put("Rules", RULES);  // List of validation rules.
        validationData.put("Violated", violations);  // List of violated rules.
        return validationData;
    }
}
