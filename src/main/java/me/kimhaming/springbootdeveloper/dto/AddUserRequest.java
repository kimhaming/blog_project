package me.kimhaming.springbootdeveloper.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 회원가입 request dto
public class AddUserRequest {
    private String email;
    private String password;
}
