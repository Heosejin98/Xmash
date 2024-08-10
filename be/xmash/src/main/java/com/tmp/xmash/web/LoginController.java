package com.tmp.xmash.web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class LoginController {



    @GetMapping("/success-page")
    public String showSuccessPage() {
        return "login-success";
    }



    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}
