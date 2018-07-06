package com.cfc.admin.controller;

import com.cfc.admin.service.AdminNewService;
import com.cfc.util.controller.BasePageController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

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
//        genReturnMap(new HashMap<>());
        return "admin/new/list";
    }

    @RequestMapping("/detail")
    public Object detail(Integer id) {
        ModelAndView modelAndView = new ModelAndView("admin/new/detail");
        if (id != null) {
            Map<String, Object> map = AdminNewService.get(id);
            modelAndView.addAllObjects(genReturnMap(map));
        }
        return modelAndView;
    }


}
