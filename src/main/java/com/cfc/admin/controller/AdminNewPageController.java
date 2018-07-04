package com.cfc.admin.controller;

import com.cfc.util.controller.BasePageController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

/**
 * author fangchen
 * date  2018/6/25 下午3:31
 */
@RequestMapping("/admin/new")
@Controller
public class AdminNewPageController extends BasePageController {
    @Override
    protected void fillReturnInfo() {

    }

    @RequestMapping("/list")
    public Object list() {
        genReturnMap(new HashMap<>());
        return "/admin/new/list";
    }


}
