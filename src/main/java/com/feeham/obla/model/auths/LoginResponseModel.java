package com.feeham.obla.model.auths;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseModel {
    private String userId;
    private String username;
    private String bearerToken;
}
