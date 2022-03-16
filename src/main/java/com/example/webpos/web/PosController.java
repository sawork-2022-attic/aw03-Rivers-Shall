package com.example.webpos.web;

import com.example.webpos.biz.PosService;
import com.example.webpos.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PosController {

    private PosService posService;

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @GetMapping("/")
    public String pos(Model model) {
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", posService.getCart());
        return "index";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id")String productId, Model model) {
        posService.remove(productId);
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", posService.getCart());
        return "redirect:/";
    }

    @GetMapping("/add/{id}")
    public String add(@PathVariable("id")String productId, Model model) {
        posService.add(productId, 1);
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", posService.getCart());
        return "redirect:/";
    }

    @GetMapping("/reduce/{id}")
    public String reduce(@PathVariable("id")String productId, Model model) {
        System.out.println("GET /reduce/" + productId);
        posService.reduce(productId);
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", posService.getCart());
        return "redirect:/";
    }

    @GetMapping("/empty")
    public String empty(Model model) {
        posService.checkout(posService.getCart());
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", posService.getCart());
        return "redirect:/";
    }
}
