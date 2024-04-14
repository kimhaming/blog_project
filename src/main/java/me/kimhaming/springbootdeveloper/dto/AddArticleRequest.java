package me.kimhaming.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimhaming.springbootdeveloper.domain.Article;

@NoArgsConstructor  //기본생성자
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자
@Getter
public class AddArticleRequest {
    private String title;
    private String content;

    // dto -> entity로 변환
    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }
}