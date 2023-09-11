package com.feeham.obla.model.userdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserUpdateDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String password;
    private String address;
    private String role;
}
