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
//게시판 검색,조회,생성,수정 등을 관리하는 클래스

@Controller  //이것은 컨트롤러 라고 선언
@RequestMapping("/board") //경로를 지정
public class BoardController {

    @Autowired // Board(게시글) 객체를 받아온다
    private BoardRepository boardRepository;

    //예외처리를 위한 클래스
    @Autowired
    private Board_Validator board_validator;
    //검색 및 조회
    @GetMapping("/list")  //URL요청 연결 "접속할 url 주소를 넣어줘야함" /hi 라고 넣으면 매핑된 html파일을 반환
    public String list(Model model, @PageableDefault(size = 4) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) { //현재 페이지의 정보
        //searchText 값 지정
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);

        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards",boards); //게시글 수
        return "board/list"; //보여줄 페이지의 이름 / 해당 리턴값을 통해 뷰 페이지를 보여준다

    }//글생성
    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        model.addAttribute("board",new Board());
        //가져온 게시글의 id 값이 존재하지 않을경우 (생성시 작동 )
        if(id == null){
            model.addAttribute("board", new Board());
        //id값이 이미 존재 할 경우 ( 수정시 작동 )
        }else {
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board",board);
        }
        return "board/form";
    }
    //게시글을 저장하는 기능
    @PostMapping("/form")
    //예외처리기능 달성 여부 검사도 같이 검사 (Valid)
    public String greetingSubmit(@Valid Board board, BindingResult bindingResult) {
        board_validator.validate(board,bindingResult); //예외처리 (bindingResult = 오류내용 보관
        if (bindingResult.hasErrors()) {
            return "board/form";
        }
        //저장 메소드 ( board 에 id 값이 존재할경우 업데이트 없을경우 인서트)
        boardRepository.save(board);
        //완료할 시 리턴되는 페이지로 이동
        return "redirect:/board/list";
    }
}
