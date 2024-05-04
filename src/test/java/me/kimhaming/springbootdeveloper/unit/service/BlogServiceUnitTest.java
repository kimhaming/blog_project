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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlogServiceUnitTest {

    @Mock
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
            // 빌더 패턴을 사용하여 테스트할 내용만 생성
            // @Builder -> @SuperBuilder 변경해서 builder().createdAt() 불러오기 가능
            // 목 객체를 생성하는 것은 아님
            // findById() 메서드를 호출할 때 반환될 객체 만드는 것
            Optional<Article> originalArticle = Optional.of(Article.builder()
                    .createdAt(LocalDate.now().atStartOfDay().minusDays(8))
                    .build());

            UpdateArticleRequest updateArticleRequest = UpdateArticleRequest.builder()
                    .content("updatedContent")
                    .build();

            // when: 1번을 찾으면 무조건 오리지널 게시글이 반환되도록
            when(blogRepository.findById(1L)).thenReturn(originalArticle);

            // 1번 글을 수정하고싶은 내용으로 update() 시켜서 Article 타입의 객체에 담기
            Article updatedArticle = blogService.update(1L, updateArticleRequest);

            // then: 해당 메서드를 통해 업데이트된 값과 업데이트하고자 했던 값이 같은지 검증하기
            assertThat(updatedArticle.getContent()).isEqualTo("updatedContent");
            verify(blogRepository).findById(1L);
        }

        @DisplayName("생성일자로부터 10일 이상 됐을 때")
        @Test
        public void testUpdateFail() {
            // given: 오리지널 객체와 수정 요청 객체 만들기
            Optional<Article> originalArticle = Optional.of(Article.builder()
                    .createdAt(LocalDate.now().atStartOfDay().minusDays(10))
                    .build());

            UpdateArticleRequest updateArticleRequest = UpdateArticleRequest.builder()
                    .content("updatedContent")
                    .build();

            // when: 레파지토리에서 1번 찾으면 만들어둔 오리지널 객체 반환하도록 정하기
            when(blogRepository.findById(1L)).thenReturn(originalArticle);

            // assertThat(updatedArticle.getContent()).isEqualTo("updatedContent");
            // 같은지 검증하면 실패가 뜨기 때문에, 발생할 예외가 나타나면 성공하도록 해야한다
            // 1번 객체를 update() 시키려고 할 때 예측 가능한 예외 IllegalArgumentException 발생하는지 검증하기
            assertThrows(IllegalArgumentException.class, () -> blogService.update(1L, updateArticleRequest));

        }
    }

}
