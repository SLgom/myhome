package com.SLgom.myhome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String index(){

        return "index"; //뷰 템플릿을 연결 (템플릿의 이름을 리턴하게 하기)
    }

}
