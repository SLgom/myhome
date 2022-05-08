package com.SLgom.myhome.controller;

import com.SLgom.myhome.model.Board;
import com.SLgom.myhome.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller  //이것은 컨트롤러 라고 선언
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/list")  //URL요청 연결 "접속할 url 주소를 넣어줘야함" /hi 라고 넣으면 매핑된 html파일을 반환
    public String list(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards",boards);
        return "board/list"; //보여줄 페이지의 이름 / 해당 리턴값을 통해 뷰 페이지를 보여준다
    }
    @GetMapping("/form")
    public String from(Model model){
        return "board/form";
    }
}
