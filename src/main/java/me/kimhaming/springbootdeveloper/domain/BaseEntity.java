package me.kimhaming.springbootdeveloper.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@SuperBuilder
@Getter
@EntityListeners(AuditingEntityListener.class)
// 모든 엔티티에 의해 확장될 기본 클래스 지정
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    // @CreatedDate
    // Hibernate 제공 어노테이션
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    //    @LastModifiedDate
    // Hibernate 제공 어노테이션
    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
