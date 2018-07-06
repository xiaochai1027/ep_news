package com.cfc.admin.service;

import com.cfc.util.exception.Errors;
import com.cfc.util.exception.SysException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

/**
 * author fangchen
 * date  2018/6/25 下午2:13
 */
public class FileService {
    private static Logger logger = LoggerFactory.getLogger(FileService.class);

    private static String IMAGE_UPLOAD_PATH = "/home/news/image";

    private static String IMAGE_UPLOAD_RESULT_BASE_URL = "/image/";
    private static final char FILE_SEPERATOR = '.';



    public static Object uploadImage(MultipartFile file) {
        try {
            if (file == null && file.isEmpty()) {
                throw new SysException(Errors.ERROR_PARAMS, "上传文件为空");
            }
            String fileType = getFileType(file.getOriginalFilename());
            String fileName = UUID.randomUUID().toString().replace("-", "") + FILE_SEPERATOR + fileType;
            Path savePath = Paths.get(IMAGE_UPLOAD_PATH, fileName);
            if (Files.notExists(savePath.getParent())) {
                Files.createDirectories(savePath.getParent());
            }
            /*if (Files.notExists(savePath)) {
                Files.createFile(savePath);
            }*/
            Files.copy(file.getInputStream(), savePath);
            HashMap result = new HashMap();
            result.put("url", IMAGE_UPLOAD_RESULT_BASE_URL + fileName);
            return result;

        } catch (IOException e) {
            logger.error("upload image error", e);
            throw new SysException(Errors.ERROR_UNKNOWN, "上传出错");
        }
    }

    private static String getFileType(String s) {
        int index = s.lastIndexOf(FILE_SEPERATOR);
        String type = s.substring(index + 1, s.length());
        return type;
    }
}