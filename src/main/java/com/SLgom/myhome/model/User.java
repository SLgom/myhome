package com.SLgom.myhome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
//유저 정보 모델 (사용자 테이블 )
public class User {
    //id 값 가져오기
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    //권한과 유저 정보를 매핑
    private List<Role> roles = new ArrayList<>();

    //    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)//
    //    @JsonIgnore
    private List<Board> boards = new ArrayList<>();


}
