package me.kimhaming.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.kimhaming.springbootdeveloper.domain.Article;
import me.kimhaming.springbootdeveloper.dto.AddArticleRequest;
import me.kimhaming.springbootdeveloper.dto.UpdateArticleRequest;
import me.kimhaming.springbootdeveloper.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@RequiredArgsConstructor    // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    // 블로그 글 추가 메소드
    public Article createArticle(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    // 블로그 글 전체 조회 메소드
    public List<Article> getAllArticles() {
        return blogRepository.findAll();
    }

    // createdAt 기준 페이지네이션
    @Transactional
    public Page<Article> getNonDeletedArticlesSortedByCreatedAt(Pageable pageable) {
        return blogRepository.findByDeletedAtIsNull(pageable);
    }

    // 블로그 글 단일 조회 메소드
    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    // 글 deletedAt 생성 메소드
    public void softDeleteArticleById(long id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        // deletedAt 생성하기
        article.setDeletedAt(LocalDateTime.now());

        blogRepository.save(article);
    }

    // 블로그 글 DB 삭제 메소드
    public void hardDeleteArticleById(long id) {
        blogRepository.deleteById(id);
    }

    // 블로그 글 수정 메소드
    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        // 현재 시간 불러오기
        LocalDate today = LocalDate.now();
        // 생성 일자 불러오기
        LocalDate createdDate = article.getCreatedAt().toLocalDate();
        // 년/월/일자 간격 불러오기
        Period period = Period.between(createdDate, today);
        int daysSinceCreated = period.getDays();

        // 저장되어있던 글의 생성일자가 9일 이하라면 수정 진행
        if (daysSinceCreated <= 9) {
            article.update(request.getTitle(), request.getContent());
            // 9일 전이라면 1일 후 수정을 못한다는 메시지 함께 반환
        }

        if (daysSinceCreated == 9) {
            throw new IllegalArgumentException("하루가 지나면 이 게시물을 편집할 수 없습니다.");

            // 10일 전이라면 수정 불가하도록
        } else if(daysSinceCreated > 9) {
            throw new IllegalArgumentException("생성일자로부터 10일이 경과되어 편집이 불가능합니다.");
        }

        return article;
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
