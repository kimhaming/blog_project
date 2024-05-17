package me.kimhaming.springbootdeveloper;

import me.kimhaming.springbootdeveloper.domain.Article;
import me.kimhaming.springbootdeveloper.repository.BlogRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class DataLoader implements CommandLineRunner {

    private final BlogRepository blogRepository;

    public DataLoader(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        IntStream.rangeClosed(1, 33).forEach(i -> {
            Article article = Article.builder()
                    .title(i + "번째 제목 테스트 데이터입니다.")
                    .content(i + "번째 내용 테스트 데이터입니다.")
                    .email("test" + i + "@example.com")
                    .phoneNum("01012345678")
                    .author("테스트 작성자 " + i)
                    .password("Abc123!@#")
                    .build();
            blogRepository.save(article);
        });
    }
}
