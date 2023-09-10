package com.feeham.obla.model.auths;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequestModel {
    private String email;
    private String password;
}
