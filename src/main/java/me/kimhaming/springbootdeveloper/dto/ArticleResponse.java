package me.kimhaming.springbootdeveloper.dto;

import lombok.Getter;
import me.kimhaming.springbootdeveloper.domain.Article;

import java.time.LocalDateTime;

@Getter
public class ArticleResponse {

    // 응답줄 때 필요한 것 -> 필드로
    private final String title;
    private final String content;
    private final String email;
    private final String author;
    private final String phoneNum;
    private final String password;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

    // 엔티티를 인수로 받아서 저장된 제목, 내용으로 dto를 초기화해주기
    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
        this.email = article.getEmail();
        this.author = article.getAuthor();
        this.phoneNum = article.getPhoneNum();
        this.password = article.getPassword();
        this.createdAt = article.getCreatedAt();
        this.updatedAt = article.getUpdatedAt();
        this.deletedAt = article.getDeletedAt();
    }
}
