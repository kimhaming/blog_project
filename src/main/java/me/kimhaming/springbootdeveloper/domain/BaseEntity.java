package me.kimhaming.springbootdeveloper.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
// 모든 엔티티에 의해 확장될 기본 클래스 지정
@MappedSuperclass
public class BaseEntity {
//    @CreatedDate
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
