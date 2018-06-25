package com.cfc.admin.controller;

import com.cfc.admin.service.AdminNewService;
import com.cfc.util.controller.BasePageController;
import com.cfc.util.model.PageModel;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

//    @RequestMapping("list")
//    public Object list(Model model, PageModel pageModel) {
//
//        return "/"
//    }


}
