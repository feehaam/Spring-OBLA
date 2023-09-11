package com.feeham.obla.model.userdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserCreateDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String role;
}
