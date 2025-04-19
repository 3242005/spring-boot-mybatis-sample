package com.example.my_spring_app.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // ユーザー一覧ページにリダイレクト
        return "redirect:/users";
    }
}