package com.feeham.obla.model;
import com.feeham.obla.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserReadDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Role role;
}
