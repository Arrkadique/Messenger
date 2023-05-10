package com.arrkadique.springmessenger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/login?logout")
    public String login2(){
        return "login";
    }
}
