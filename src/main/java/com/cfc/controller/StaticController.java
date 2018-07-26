package com.cfc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * author fangchen
 * date  2018/7/26 下午5:45
 */
@Controller
public class StaticController {

    @RequestMapping("/robots.txt")
    public String getRobots(){

        return "redirect:/static/robots.txt";
    }

    @RequestMapping("/baidu_verify_VfcEcHZQKR.html")
    public ModelAndView getBaiduHtml(){
        return new ModelAndView("baidu_verify_VfcEcHZQKR");
    }
}
