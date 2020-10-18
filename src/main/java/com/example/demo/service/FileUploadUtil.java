package com.example.demo.service;

import com.example.demo.base.FilePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.SyncFailedException;

public class FileUploadUtil {
    /**
     * 上传文件
     *
     * @param multipartFile
     * @return 文件存储路径
     */

    @CrossOrigin(origins = "*",maxAge = 3600)
    public static String upload(MultipartFile multipartFile) {
        FilePath road2 = new FilePath();
        // 文件存储位置，文件的目录要存在才行，可以先创建文件目录，然后进行存储
        String filePath = road2.getInPath()+"/" + multipartFile.getOriginalFilename();
        File file = new File(filePath);
        String data = "",type="excited";
        if (!file.exists()) {
            try {
                file.createNewFile();
                type = "excited";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 文件存储
        try {
            multipartFile.transferTo(file);
            data = "success";
        } catch (IOException e) {
            e.printStackTrace();
            data = "false";
        }
        return data;
    }
}

