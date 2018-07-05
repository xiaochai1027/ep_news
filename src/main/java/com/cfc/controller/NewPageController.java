package com.cfc.controller;

import com.cfc.util.controller.BasePageController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public Object home(){
        return "index";
    }
}
