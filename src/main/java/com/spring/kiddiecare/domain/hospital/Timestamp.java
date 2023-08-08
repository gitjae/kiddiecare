package com.spring.kiddiecare.domain.hospital;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
// 부모인 나를 상속받은 자식 클래스에서 나의 필드값들을 -> 엔터티 컬럼으로 인식
@MappedSuperclass
@Getter
@Setter
public class Timestamp {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}

