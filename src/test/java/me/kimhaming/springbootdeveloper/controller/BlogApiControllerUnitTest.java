package me.kimhaming.springbootdeveloper.controller;

import me.kimhaming.springbootdeveloper.domain.Article;
import me.kimhaming.springbootdeveloper.dto.AddArticleRequest;
import me.kimhaming.springbootdeveloper.repository.BlogRepository;
import me.kimhaming.springbootdeveloper.service.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BlogApiControllerUnitTest {

    private BlogApiController blogApiController;
    private BlogService blogServiceMock;

    @BeforeEach
    public void setUp() {
        // 가짜 서비스 객체 생성
        blogServiceMock = mock(BlogService.class);
        blogApiController = new BlogApiController(blogServiceMock);
    }

    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() {
        // given
        // dto 객체에 요청값 담기
        AddArticleRequest userRequest = new AddArticleRequest("title", "content");
        Article expectedArticle = new Article("title", "content");

        // when
        when(blogServiceMock.save(userRequest)).thenReturn(expectedArticle);
        ResponseEntity<Article> responseEntity = blogApiController.addArticle(userRequest);
        Article actualArticle = responseEntity.getBody();

        // then
        // 상태코드 같은지 비교, 기대값과 실제값 같은지 비교
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedArticle, actualArticle);
    }
}
