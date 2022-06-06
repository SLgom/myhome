package com.SLgom.myhome.controller;

import com.SLgom.myhome.model.Board;
import com.SLgom.myhome.repository.BoardRepository;
import com.SLgom.myhome.validator.Board_Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller  //이것은 컨트롤러 라고 선언
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private Board_Validator board_validator;

    @GetMapping("/list")  //URL요청 연결 "접속할 url 주소를 넣어줘야함" /hi 라고 넣으면 매핑된 html파일을 반환
    public String list(Model model, @PageableDefault(size = 4) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {
        //Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);

        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards",boards);
        return "board/list"; //보여줄 페이지의 이름 / 해당 리턴값을 통해 뷰 페이지를 보여준다

    }
    @GetMapping("/form")
    public String from(Model model, @RequestParam(required = false) Long id){
        model.addAttribute("board",new Board());
        if(id == null){
            model.addAttribute("board", new Board());
        }else {
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board",board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String greetingSubmit(@Valid Board board, BindingResult bindingResult) {
        board_validator.validate(board,bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/form";
        }
        boardRepository.save(board);
        return "redirect:/board/list";
    }
}
