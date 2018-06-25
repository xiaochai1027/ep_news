package com.cfc.admin.controller;

import com.cfc.admin.service.AdminNewService;
import com.cfc.util.KCUtil;
import com.cfc.util.controller.BaseApiController;
import com.cfc.util.model.PageModel;
import com.cfc.util.spring.SpringMVCUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * author fangchen
 * date  2018/6/25 下午3:31
 */
@RequestMapping("/api/admin/new")
@Controller
public class AdminNewApiController extends BaseApiController {

    @RequestMapping("/list")
    public Object list(PageModel pageModel) {
        return genReturnJSON(AdminNewService.list(pageModel), pageModel.getPageNum(), AdminNewService.count());
    }

    @RequestMapping("/add")
    public Object add(){
        Map<String, Object> params = KCUtil.getParameterMap(SpringMVCUtil.getRequest());
        return genReturnJSON(AdminNewService.createNew(params, (Integer)params.get("id")));

    }

    @RequestMapping("/delete")
    public Object list(Integer id) {
        return genReturnJSON(AdminNewService.delete(id));
    }


}
