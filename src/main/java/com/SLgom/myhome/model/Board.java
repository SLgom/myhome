package com.SLgom.myhome.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
//게시글 모델

//lombok 를 통해 Data 어노테이션을 하여 외부에서 쓸 수 있도록 함
@Entity //데이터베이스와의 연동을 위한 모델 클래스임을 설정
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min=2, max=30, message = "제목은 2자이상 30자 이하여야 합니다.")
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
