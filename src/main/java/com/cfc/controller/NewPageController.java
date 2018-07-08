package com.cfc.controller;

import com.cfc.service.NewService;
import com.cfc.util.controller.BasePageController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * author fangchen
 * date  2018/7/5 上午10:12
 */
@Controller
public class NewPageController extends BasePageController {
    @Override
    protected void fillReturnInfo() {

    }

    @RequestMapping("/index")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView("index");
        mv.addAllObjects(NewService.homePage());
        return mv;
    }

    @RequestMapping("")
    public ModelAndView home1(){
        ModelAndView mv = new ModelAndView("index");
        mv.addAllObjects(NewService.homePage());

//        mv.addObject("honor",NewService.homePage());
        return mv;
    }

    @RequestMapping("/detail")
    public ModelAndView detail(Integer id) {
        ModelAndView mv = new ModelAndView("news");
        mv.addObject("news", NewService.detail(id));
        return mv;
    }
}
