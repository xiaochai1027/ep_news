package com.cfc.admin.controller;



import com.cfc.admin.service.FileService;
import com.cfc.util.controller.BaseApiController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * author fangchen
 * date  2018/6/25 下午2:08
 */
@Controller
@RequestMapping("/admin/file")
public class FileApiController extends BaseApiController {

    /**
     * 目前只考虑图片上传
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(@RequestParam MultipartFile file) {
        return genReturnJSON(FileService.uploadImage(file));
    }

}
