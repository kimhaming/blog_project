package me.kimhaming.springbootdeveloper.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimhaming.springbootdeveloper.domain.Article;

@NoArgsConstructor  //기본생성자
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자
@Getter
public class AddArticleRequest {
    @Size(max = 199)
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @Size(max = 20)
    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    private String phoneNum;

    @NotBlank(message = "작성자 이름은 필수 입력 값입니다.")
    private String author;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9].*[0-9].*[0-9].*[0-9].*[0-9])(?=.*[*!@#$%].*[*!@#$%]).*$",
            message = "비밀번호는 대소문자, 숫자 5개 이상, 특수문자 *!@#$% 중 2개 이상을 포함해야 합니다."
    )
    private String password;

    // dto -> entity로 변환
    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .email(email)
                .phoneNum(phoneNum)
                .author(author)
                .password(password)
                .build();
    }
}
