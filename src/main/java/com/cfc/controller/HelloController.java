package com.cfc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @auther fangchen.chai ON 2017/11/20
 */
@Controller
@RequestMapping("hello")
public class HelloController {

    @GetMapping("/thymeleaf")
    public String hello(Model model, @RequestParam(required = false, defaultValue = "thymeleaf") String name) {
        model.addAttribute("name", name);
        return"index";
    }

    @GetMapping("/jsp")
    public String helloJsp(Map<String,String> model, @RequestParam(required = false, defaultValue = "jsp") String name) {
        model.put("name", name);
        return "indexj";
    }


}
