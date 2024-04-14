package me.kimhaming.springbootdeveloper.dto;

import lombok.Getter;
import me.kimhaming.springbootdeveloper.domain.Article;

@Getter
public class ArticleResponse {

    // 응답줄 때 필요한 것 -> 필드로
    private final String title;
    private final String content;

    // 엔티티를 인수로 받아서 저장된 제목, 내용으로 dto를 초기화해주기
    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
