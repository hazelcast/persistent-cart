package com.hazelcast.persistentcart.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@SessionAttributes("cart")
public class HomeController {

    private final ProductRepository productRepo;

    public HomeController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @ModelAttribute("products")
    public Iterable<Product> products() {
        return productRepo.findAll();
    }

    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart();
    }

    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @PostMapping("/add")
    public String add(@RequestParam("id") Long id,
                      @RequestParam("quantity") Integer quantity,
                      @ModelAttribute("cart") Cart cart) {
        Optional<Product> product = productRepo.findById(id);
        product.ifPresent(it -> cart.add(it, quantity));
        return "redirect:/";
    }

    @PostMapping("/remove")
    public String add(@RequestParam("id") Long id,
                      @ModelAttribute("cart") Cart cart) {
        Product product = new Product();
        product.setId(id);
        cart.remove(product);
        return "redirect:/";
    }
}