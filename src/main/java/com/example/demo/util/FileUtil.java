package com.example.demo.util;

import com.example.demo.base.FilePath;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileUtil {


    public static void download(String filename, HttpServletResponse res) throws IOException {

        FilePath road2 = new FilePath();
        // 发送给客户端的数据
        OutputStream outputStream = res.getOutputStream();
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        // 读取filename
        bis = new BufferedInputStream(new FileInputStream(new File(road2.getOutPath() + filename)));
        int i = bis.read(buff);
        while (i != -1) {
            outputStream.write(buff, 0, buff.length);
            outputStream.flush();
            i = bis.read(buff);
        }
    }

}
