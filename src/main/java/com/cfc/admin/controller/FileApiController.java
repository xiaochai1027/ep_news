package com.cfc.admin.controller;



import com.cfc.admin.service.FileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * author fangchen
 * date  2018/6/25 下午2:08
 */
@RestController
@RequestMapping("/admin/file")
public class FileApiController {

    /**
     * 目前只考虑图片上传
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Object upload(@RequestParam MultipartFile file) {
        return FileService.uploadImage(file);
    }

}
