package com.SLgom.myhome.controller;

import com.SLgom.myhome.model.Board;
import com.SLgom.myhome.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
//RESTful API
class BoardApiController {
    //데이터 조회 json.
    @Autowired
    private BoardRepository repository;
    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
        @RequestParam(required = false, defaultValue = "") String content) {
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
            return repository.findAll();
        } else {
            return repository.findByTitleOrContent(title, content);
        }

    }
    //데이터 추가
    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    // Single item

    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {

        return repository.findById(id).orElse(null);
    }
    //데이터 삽입
    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }
    //데이터 삭제
    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) { repository.deleteById(id); }
}