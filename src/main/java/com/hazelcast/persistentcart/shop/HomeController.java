package com.hazelcast.persistentcart.shop;

import com.hazelcast.persistentcart.authentication.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@SessionAttributes("user")
public class HomeController {

    private final ProductRepository productRepo;
    private final CartService cartService;

    public HomeController(ProductRepository productRepo, CartService cartService) {
        this.productRepo = productRepo;
        this.cartService = cartService;
    }

    @ModelAttribute("products")
    public Iterable<Product> products() {
        return productRepo.findAll();
    }

    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @PostMapping("/add")
    public String add(@RequestParam("id") Long id,
                      @RequestParam("quantity") Integer quantity,
                      @ModelAttribute("user") User user) {
        Optional<Product> product = productRepo.findById(id);
        product.ifPresent(it -> cartService.add(user, it, quantity));
        return "redirect:/";
    }

    @PostMapping("/remove")
    public String add(@RequestParam("id") Long id,
                      @ModelAttribute("user") User user) {
        Product product = new Product();
        product.setId(id);
        cartService.remove(user, product);
        return "redirect:/";
    }
}