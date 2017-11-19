package com.gianfranco.trabajoparcial.web.controller;

import com.gianfranco.trabajoparcial.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @RequestMapping(path = "/login")
    public String loginForm(Model model, HttpServletRequest request) {
        model.addAttribute("user", new User());
        try {
            Object flash = request.getSession().getAttribute("error");
            model.addAttribute("error", flash);
            request.getSession().removeAttribute("error");
        } catch (Exception ex) {

        }
        return "login";
    }
}
