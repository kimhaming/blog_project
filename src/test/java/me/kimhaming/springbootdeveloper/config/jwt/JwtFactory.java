package me.kimhaming.springbootdeveloper.config.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static java.util.Collections.emptyMap;

// 통합테스트 목적:
// 여러 파트에서, 경우의 수가 많은
// 단위테스트가 먼저이고, 이런 것도 테스트해보고 싶다 할 때 통합테스트
// 구글 프로덕트를 만들면, 단위테스트, 통합테스트 외에도 더 많은 테스트가 있다
// 많은 테스트가 있을수록 프로덕트 안정성이 높아진다
// 테스트가 쌓일수록 안정성이 높아진다
// 단위와 통합은 시나리오를 다르게 하는 것 (포스트맨 없이도 가능)

@Getter
public class JwtFactory {
    private String subject = "test@email.com";
    private Date issuedAt = new Date();
    private Date expiration = new Date(new Date().getTime() + Duration.ofDays(14).
            toMillis());
    private Map<String, Object> claims = emptyMap();

    // 빌더 패턴을 사용해 설정이 필요한 데이터만 선택 결정
    @Builder
    public JwtFactory(String subject, Date issuedAt, Date expiration,
                      Map<String, Object> claims) {
        this.subject = subject != null ? subject : this.subject;
        this.issuedAt = issuedAt != null ? issuedAt : this.issuedAt;
        this.expiration = expiration != null ? expiration : this.expiration;
        this.claims = claims != null ? claims : this.claims;
    }

    public static JwtFactory withDefaultValues() {
        return JwtFactory.builder().build();
    }

    // jjwt 라이브러리를 사용해 JWT 토큰 생성
    public String createToken(JwtProperties jwtProperties) {
        return Jwts.builder()
                .setSubject(subject)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }
}
