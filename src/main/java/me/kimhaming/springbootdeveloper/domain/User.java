package me.kimhaming.springbootdeveloper.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column
    private String phoneNum;

    /**
     * 값 타입 컬렉션으로 저장하기 위한 어노테이션
     * RDBMS에는 컬렉션과 같은 형태의 데이터를 컬럼에 저장할 수 없기 때문에
     * 별도의 테이블을 생성하여 컬렉션을 관리해야 한다.
     * 이 때, 해당 필드가 컬렉션 객체임을 JPA에게 알려주는 어노테이션
      */
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "USER_ROLES")
    private List<String> roles = new ArrayList<>();

    @Builder
    public User(String email, String password, List<String> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    // 사용자의 id를 반환(고유한 값)
    @Override
    public String getUsername() {
        return email;
    }

    // 사용자의 패스워드 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true;    // true -> 만료되지 않음을 나타냄
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true;
    }

    // 계정 사용 가능 여부 반환
    public boolean isEnabled() {
        return true;
    }
}
