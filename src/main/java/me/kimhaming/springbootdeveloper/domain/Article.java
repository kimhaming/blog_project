package me.kimhaming.springbootdeveloper.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // id 라는 열에 매핑한다 (실제 필드값과 일치하지 않을 때는 필수적으로 써준다)
    // 기본 키나 생성일시 같은 한 번 설정되면 변경되지 않아야 하는 필드에 updatable = false를 사용한다
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)   // title 이라는 notnull 컬럼과 매핑
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    // 롬복 어노테이션
    // 빌더를 생성하는 기능 제공
    // 소스 레벨에서만 유지, 컴파일 후에는 제거
    // 컴파일 시에 코드 생성하고 생성된 코드가 클래스 파일에 포함
    @Builder
    public Article(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
