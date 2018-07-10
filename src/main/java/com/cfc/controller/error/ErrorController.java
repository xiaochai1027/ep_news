package com.cfc.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @auther fangchen.chai ON 2017/11/20
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/error")
    public String error() {
        return"error/error";
    }



}
