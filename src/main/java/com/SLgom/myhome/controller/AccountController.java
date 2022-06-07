package com.SLgom.myhome.controller;

import com.SLgom.myhome.model.User;
import com.SLgom.myhome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {
    //사용자 계정과 관련된 매핑
    @Autowired
    private UserService userService;
    //로그인 페이지 지정
    @GetMapping("/login")
    public String login(){
        return "account/login";
    }

    //회원가입 페이지 지정
    @GetMapping("/register")
    public String register(){

        return "account/register";
    }
    //회원가입 정보 보내기
    @PostMapping("/register")
    public String register(User user){
        userService.save(user); //
        return "redirect:/"; // 인덱스 메소드 호출과 같은 기능
    }
}
