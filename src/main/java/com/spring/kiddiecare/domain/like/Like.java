package com.spring.kiddiecare.domain.like;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "like")
@Entity
public class Like {
    @Id
    private int no;
    private int user_no;
    private String ykiho;

    public Like(LikeRequestDto likeRequestDto) {
        this.no = likeRequestDto.getNo();
        this.user_no = likeRequestDto.getUser_no();
        this.ykiho = likeRequestDto.getYkiho();
    }
}
