package me.kimhaming.springbootdeveloper.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kimhaming.springbootdeveloper.controller.BlogApiController;
import me.kimhaming.springbootdeveloper.domain.Article;
import me.kimhaming.springbootdeveloper.dto.AddArticleRequest;
import me.kimhaming.springbootdeveloper.service.BlogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 컨트롤러 단위 테스트일 때 사용하는 어노테이션
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

    @Nested
    @DisplayName("createArticle 테스트")
    class CreateArticleTest {

        @DisplayName("블로그 글 추가에 성공한다.")
        @Test
        @WithMockUser   // 로그인한 사용자를 위한 시뮬레이션
        public void createArticle() throws Exception {
            // given
            // 입력값(요청값, 컨트롤러의 파라미터)
            AddArticleRequest request = new AddArticleRequest(
                    "testTitle",
                    "testContent",
                    "email@google.com",
                    "010-1234-5678",
                    "testAuthor",
                    "asdR!@12345"
            );

            String requestBody = objectMapper.writeValueAsString(request);

            // 응답값 = 예상하는
            Article response = new Article(
                    "testTitle",
                    "testContent",
                    "email@google.com",
                    "010-1234-5678",
                    "testAuthor",
                    "asdR@@0123"
            );

            String responseBody = objectMapper.writeValueAsString(response);

            // BDDMockito의 given() 메소드
            // any()에 해당하는 메소드 파라미터 형식으로
            // given()에 해당하는 메소드가 실행되었을 때, willReturn() 에 해당하는 값을 반환한다
            given(blogService.createArticle(Mockito.any(AddArticleRequest.class))).willReturn(response);

            // when: 설정한 내용을 바탕으로 요청 전송
            ResultActions result = mockMvc.perform(post("/api/articles")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestBody));

            // then: 요청값을 기반으로 생성한 result의 상태가 create인지, 바디가 responseBody와 같은지 검증
            result.andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().string(responseBody));
        }

        @DisplayName("비밀번호가 잘못 입력되어 글 추가에 실패한다.")
        @Test
        public void failed() throws Exception {
            // given
            // 입력값(요청값, 컨트롤러의 파라미터)
            AddArticleRequest request = new AddArticleRequest(
                    "testTitle",
                    "testContent",
                    "email@google.com",
                    "010-1234-5678",
                    "testAuthor",
                    "asdf1234"
            );

            String requestBody = objectMapper.writeValueAsString(request);

            // when
            ResultActions result = mockMvc.perform(post("/api/articles")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestBody));

            // then
            result.andDo(print())
                    .andExpect(status().is4xxClientError());
        }
    }
}
