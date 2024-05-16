package me.kimhaming.springbootdeveloper.repository;

import me.kimhaming.springbootdeveloper.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Article, Long> {
    Article findByTitle(String title);
    List<Article> findByTitleContaining(String title);
    Page<Article> findAll(Pageable pageable);
}
