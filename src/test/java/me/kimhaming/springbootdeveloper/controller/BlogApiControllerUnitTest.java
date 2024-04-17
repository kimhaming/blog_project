package me.kimhaming.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kimhaming.springbootdeveloper.domain.Article;
import me.kimhaming.springbootdeveloper.dto.AddArticleRequest;
import me.kimhaming.springbootdeveloper.service.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

// 단위 테스트 대상을 명시
@WebMvcTest(BlogApiController.class)
public class BlogApiControllerUnitTest {

    @Autowired
    protected ObjectMapper objectMapper;    // 직렬화, 역직렬화를 위한 클래스

    // 실제 연결 차단
    @MockBean
    BlogService blogService;

    // 단위 테스트에서는 @BeforeEach 어노테이션 붙은 설정 메소드 대신 가짜 객체 주입받아 사용하면 된다
    @Autowired
    MockMvc mockMvc;

    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        Article mockArticle = new Article("title", "content");

        String responseBody = objectMapper.writeValueAsString(mockArticle);

        // AddArticleRequest(요청 DTO) 타입의 어떤 객체든 blogService.save() 하면, mockAricle를 리턴할 것이다
        // 실제로 DB에서 가져오지 않아도 해당 값이 넘어올거라고 지정해주는 것
        given(blogService.save(Mockito.any(AddArticleRequest.class))).willReturn(mockArticle);

        // 객체 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when
        // perform(): 설정한 내용을 바탕으로 요청 전송
        // REST API 테스트를 할 수 있는 환경 구성
        // POST HTTP Request -> url 경로로
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // content(): 실제 body
        result.andDo(print()).andExpect(content().string(responseBody));
//        result.andExpect(status().isCreated());
//        assertThat(.size()).isEqualTo(1);
//        assertThat(articles.get(0).getTitle()).isEqualTo(title);
//        assertThat(articles.get(0).getContent()).isEqualTo(content);

        // then
        verify(blogService).save(Mockito.any(AddArticleRequest.class));
    }
}
