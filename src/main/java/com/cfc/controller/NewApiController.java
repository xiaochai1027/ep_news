package com.cfc.controller;

import com.cfc.admin.service.AdminNewService;
import com.cfc.service.NewService;
import com.cfc.util.controller.BaseApiController;
import com.cfc.util.model.PageModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * author fangchen
 * date  2018/7/5 上午10:12
 */
@Controller
@RequestMapping("/api/new")
public class NewApiController extends BaseApiController {


    @RequestMapping("/list")
    public Object list(PageModel pageModel){
        return genReturnJSON(AdminNewService.list(pageModel), pageModel.getPageNum(), AdminNewService.count());
    }

    @RequestMapping("/home")
    public Object homePage(Integer type) {
        return genReturnJSON(NewService.homePage());
    }


}
