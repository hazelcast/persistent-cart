package com.hazelcast.persistentcart.authentication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AuthenticationController {

    private final UserRepository repository;

    public AuthenticationController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/login")
    public String displayLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("login") String login, HttpSession session) {
        Optional<User> user = repository.findByLogin(login);
        if (user.isPresent()) {
            session.setAttribute("user", user.get());
            return "redirect:/";
        }
        return "redirect:/login";

    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
