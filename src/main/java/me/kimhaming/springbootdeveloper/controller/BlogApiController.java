package me.kimhaming.springbootdeveloper.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.kimhaming.springbootdeveloper.domain.Article;
import me.kimhaming.springbootdeveloper.dto.AddArticleRequest;
import me.kimhaming.springbootdeveloper.dto.ArticleResponse;
import me.kimhaming.springbootdeveloper.dto.UpdateArticleRequest;
import me.kimhaming.springbootdeveloper.service.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

    private final BlogService blogService;

    // 전체 조회 시
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(
            // 이러한 디폴트값을 가지고 있는 Pageable 객체를 입력받는다
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        // 사용자의 요청에 의해 오름차순 및 내림차순 선택 정렬
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size, direction, "createdAt");

        // 서비스 레이어 호출
        Page<Article> articlesPage = blogService.getNonDeletedArticlesSortedByCreatedAt(pageable);

        // 엔티티 타입의 페이지 객체를 responseDTO 타입의 리스트로 파싱한다
        List<ArticleResponse> articles = articlesPage.getContent()
                .stream()
                .map(ArticleResponse::new)  // entity 찾아서 response dto로 파싱 후 반환
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        Article article = blogService.findById(id);
        // article은 컬렉션 객체가 아니므로 스트림 사용 불가
        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody @Valid AddArticleRequest request) {
        Article savedArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id,
                                              @RequestParam(name = "softDelete", defaultValue = "true") boolean isSoftDelete) {

        if (isSoftDelete) {
            blogService.softDelete(id);
        } else {
            blogService.delete(id);
        }

        return ResponseEntity.ok()
                .build();   // .builder() 로 시작하지 않고도 .build()를 사용하여 객체를 반환할 수 있다
    }

    // 게시글 제목 기준 검색 api
    @GetMapping("/api/search")
    public ResponseEntity<List<Article>> getArticlesByTitle(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size,
                                                            @RequestParam(name = "search_keyword", required = false) String keyword) {
        Pageable pageable;

        if (keyword != null & !keyword.isEmpty()) {
            pageable = PageRequest.of(page, size);
        } else {
            // 검색어가 없는 경우
            pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        }

        Page<Article> articlePage = blogService.findByTitle(pageable, keyword);

        List<Article> articles = articlePage.getContent();
        return ResponseEntity.ok()
                .body(articles);
    }

}
