package me.kimhaming.springbootdeveloper.search.controller;

import lombok.AllArgsConstructor;
import me.kimhaming.springbootdeveloper.domain.Article;
import me.kimhaming.springbootdeveloper.search.service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SearchController {
    private final SearchService searchService;

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

        Page<Article> articlePage = searchService.findByTitle(pageable, keyword);

        List<Article> articles = articlePage.getContent();
        return ResponseEntity.ok()
                .body(articles);
    }
}
