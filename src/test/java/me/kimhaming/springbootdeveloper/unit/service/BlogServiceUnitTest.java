package me.kimhaming.springbootdeveloper.unit.service;

import me.kimhaming.springbootdeveloper.domain.Article;
import me.kimhaming.springbootdeveloper.dto.UpdateArticleRequest;
import me.kimhaming.springbootdeveloper.repository.BlogRepository;
import me.kimhaming.springbootdeveloper.service.BlogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

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
            UpdateArticleRequest request = new UpdateArticleRequest(
                    "updateTitle",
                    "updateContent"
            );
            LocalDate createdDate = LocalDate.now().minusDays(8);
            Article article = new Article("originalTitle",
                    "originalContent",
                    "google@gmail.com",
                    "010-1234-5678",
                    "홍길동",
                    "asdf!@12345");

            given(blogRepository.findById(anyLong())).willReturn(Optional.of(article));

            // when
            Article updatedArticle = blogService.update(id, request);

            // then
            assert updatedArticle.getTitle().equals(request.getTitle());
            assert updatedArticle.getContent().equals(request.getContent());

        }

        @DisplayName("생성일자로부터 10일 이상 됐을 때")
        @Test
        public void testUpdateFail() {

        }
    }

}
