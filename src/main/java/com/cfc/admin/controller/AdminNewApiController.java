package com.cfc.admin.controller;

import com.cfc.admin.service.AdminNewService;
import com.cfc.util.KCUtil;
import com.cfc.util.controller.BaseApiController;
import com.cfc.util.model.PageModel;
import com.cfc.util.spring.SpringMVCUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * author fangchen
 * date  2018/6/25 下午3:31
 */
@RequestMapping("/api/admin/new")
@Controller
public class AdminNewApiController extends BaseApiController {

    @RequestMapping("/list")
    @ResponseBody
    public Object list(PageModel pageModel) {
        return genReturnJSON(AdminNewService.list(pageModel), pageModel.getPageNum(), AdminNewService.count());
    }

    @RequestMapping("/add")
    @ResponseBody
    public Object add(){
        Map<String, Object> params = KCUtil.getParameterMap(SpringMVCUtil.getRequest());
        Map<String, Object> data = new HashMap<>();
        data.put("id", params.get("id"));
        data.put("title", params.get("title"));
        data.put("index", params.get("index"));
        data.put("content", params.get("editorValue"));
        data.put("headPic", params.get("pic"));
        data.put("type", params.get("type"));
        data.put("ctime", new Date());
        data.put("utime", new Date());
        return genReturnJSON(AdminNewService.createNew(data, Integer.valueOf(params.get("id")+"")));

    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object list(Integer id) {
        return genReturnJSON(AdminNewService.delete(id));
    }


}
