package com.hazelcast.persistentcart.authentication;

import com.hazelcast.persistentcart.shop.CartRestoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AuthenticationController {

    private final UserRepository repository;
    private final CartRestoreService service;

    public AuthenticationController(UserRepository repository, CartRestoreService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/login")
    public String displayLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("login") String login, HttpSession session) {
        Optional<User> optional = repository.findByLogin(login);
        if (optional.isPresent()) {
            User user = optional.get();
            session.setAttribute("user", user);
            service.restore(user);
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
