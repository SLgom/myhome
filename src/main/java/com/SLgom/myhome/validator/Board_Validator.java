package com.SLgom.myhome.validator;

import com.SLgom.myhome.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;
//게시판 작성 시 예외 처리를 관리하는 클래스 이다
@Component
public class Board_Validator implements Validator {

    //검사할 클래스를 지정
    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }


    @Override
    public void validate(Object obj, Errors errors) {
        Board b = (Board) obj; //게시글을 받아 형변환 후 저장
        if(StringUtils.isEmpty(b.getContent())){ //비어있으면 에러메세지 띄움
            errors.rejectValue("content", "key", "내용을 입력하세요.");

        }
    }
}
