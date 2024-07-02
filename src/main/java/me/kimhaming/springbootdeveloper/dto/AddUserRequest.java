package me.kimhaming.springbootdeveloper.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 회원가입 request dto
public class AddUserRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9].*[0-9].*[0-9].*[0-9].*[0-9])(?=.*[*!@#$%].*[*!@#$%]).*$",
            message = "비밀번호는 대소문자, 숫자 5개 이상, 특수문자 *!@#$% 중 2개 이상을 포함해야 합니다."
    )
    private String password;
}
