package me.kimhaming.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.kimhaming.springbootdeveloper.domain.Article;
import me.kimhaming.springbootdeveloper.dto.ArticleListViewResponse;
import me.kimhaming.springbootdeveloper.dto.ArticleViewResponse;
import me.kimhaming.springbootdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/articles")
    // Model: 뷰로 데이터를 넘겨주는 모델 객체
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.getAllArticles().stream()
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles", articles);   // 블로그 글 리스트 저장

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false)Long id, Model model) {
        // id가 없으면 생성
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } // id가 있으면 수정
        else {
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}
