package me.kimhaming.springbootdeveloper.unit.service;

import me.kimhaming.springbootdeveloper.domain.Article;
import me.kimhaming.springbootdeveloper.domain.BaseEntity;
import me.kimhaming.springbootdeveloper.dto.AddArticleRequest;
import me.kimhaming.springbootdeveloper.dto.ArticleResponse;
import me.kimhaming.springbootdeveloper.dto.UpdateArticleRequest;
import me.kimhaming.springbootdeveloper.repository.BlogRepository;
import me.kimhaming.springbootdeveloper.service.BlogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

//@SpringBootTest
@ExtendWith(SpringExtension.class)
class BlogServiceUnitTest {

    @MockBean
    private BlogRepository blogRepository;

    @InjectMocks
    private BlogService blogService;

    @Nested
    @DisplayName("update() 테스트")
    class UpdateTest {
        @DisplayName("9일 이하면 수정 성공")
        @Test
        public void testUpdateSuccess() throws Exception {
            // given
            long id = 1L;

            // 수정 테스트에 사용할 가짜 글 생성
            Article originalArticle = new Article("originalTitle",
                    "originalContent",
                    "google@gmail.com",
                    "010-1234-5678",
                    "홍길동",
                    "asdf!@12345");

            // 가짜 글 객체의 생성일자를 8일 전으로 설정
            LocalDate createdDate = LocalDate.now().minusDays(8);

            // Reflection 사용하여 createdAt 필드에 접근하여 8일 전 생성일자 넣기
            Field createdAtField = Article.class.getDeclaredField("createdAt");
            createdAtField.setAccessible(true);
            createdAtField.set(originalArticle, createdDate);

            // 수정할 내용을 담은 객체 생성
            UpdateArticleRequest updateRequest = new UpdateArticleRequest(
                    "updateTitle",
                    "updateContent"
            );

            // 기대하는 값
            Article expectedArticle = new Article("updateTitle",
                    "updatedContent",
                    "google@gmail.com",
                    "010-1234-5678",
                    "홍길동",
                    "asdf!@12345");

            // 글 수정 요청에 대한 가짜 응답
            // 특정 고유 식별자가 입력된 경우 테스트 케이스에서 생성한 객체를 리턴한다
            // id를 찾으면 무조건 originalArticle을 반환한다
            given(blogRepository.findById(id)).willReturn(Optional.of(originalArticle));
            given(blogService.update(id, updateRequest)).willReturn(expectedArticle);

            // when: 글 수정 요청 보내기
            Article updatedArticle = blogService.update(id, updateRequest);

            // then: 저장된 아이디에 수정된 객체로 업데이트됐는지 확인하기
            assertThat(updatedArticle.getTitle()).isEqualTo(expectedArticle.getTitle());
            assertThat(updatedArticle.getContent()).isEqualTo(expectedArticle.getContent());
        }

        @DisplayName("생성일자로부터 10일 이상 됐을 때")
        @Test
        public void testUpdateFail() {


        }
    }

}
