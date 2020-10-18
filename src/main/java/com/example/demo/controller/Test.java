package com.example.demo.controller;

import com.example.demo.base.FilePath;
import com.example.demo.service.FileUploadUtil;
import com.example.demo.util.FileUtil;
import com.example.demo.util.ZipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/test")
public class Test {

//    @Autowired
//    private UserMapper userMapper;
//
//    @RequestMapping("/hello")
//    @ResponseBody
//    public String hello() {
//        return userMapper.selectByPrimaryKey(1).getName();
//    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping(value = "/upload")
    @ResponseBody
    // 此处的@RequestParam中的file名应与前端upload组件中的name的值保持一致
    public String upload(@RequestParam("file") MultipartFile multipartFile) {
        // replaceAll 用来替换windows中的\\ 为 /
        return FileUploadUtil.upload(multipartFile).replaceAll("\\\\", "/");
    }
    @ResponseBody
    @RequestMapping(value = "/download")
    public void download(
            @RequestParam("fileName") String filename
    ) throws IOException {
        FilePath road2 = new FilePath();
        //压缩文件
        FileOutputStream fos1= new FileOutputStream(new File(road2.getOutPath()+"testZip.zip"));
        ZipUtils.toZip(road2.getInPath(), fos1,true);


        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        // 设置信息给客户端不解析
        Path path = Paths.get(filename);
        String type = Files.probeContentType(path);
        // 设置contenttype，即告诉客户端所发送的数据属于什么类型
        response.setHeader("Content-type",type);
        // 设置编码
        String hehe = new String(filename.getBytes("utf-8"), "iso-8859-1");
        // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
        response.setHeader("Content-Disposition", "attachment;filename=" + hehe);
        FileUtil.download(filename, response);
    }
}