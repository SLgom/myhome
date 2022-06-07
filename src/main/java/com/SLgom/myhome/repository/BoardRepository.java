package com.SLgom.myhome.repository;

import com.SLgom.myhome.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//Board형식의 정보들을 가져와서 이용하는 함수들이 모여있는 클래스 - CRUD 메서드 등
public interface BoardRepository extends JpaRepository<Board, Long> {

    //검색 형식
    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title, String content);

    //제목과 내용,페이지 값을 검색
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);


}
