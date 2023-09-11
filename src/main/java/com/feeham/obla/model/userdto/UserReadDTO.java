package com.feeham.obla.model.userdto;
import com.feeham.obla.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserReadDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Role role;
}
