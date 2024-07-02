package me.kimhaming.springbootdeveloper.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimhaming.springbootdeveloper.domain.Article;
import me.kimhaming.springbootdeveloper.domain.User;

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

    // dto -> entity로 변환
    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }
}
