package me.kimhaming.springbootdeveloper.search.service;

import me.kimhaming.springbootdeveloper.domain.Article;
import me.kimhaming.springbootdeveloper.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    private final BlogRepository blogRepository;

    @Autowired
    public SearchService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Page<Article> findByTitle(Pageable pageable, String keyword) {
        Page<Article> articles = blogRepository.findByTitleContaining(keyword, pageable);

        // 검색 결과가 없다면
        if (articles.isEmpty()) {
            throw new IllegalArgumentException("검색 결과가 없습니다.");
        }

        return articles;
    }
}
