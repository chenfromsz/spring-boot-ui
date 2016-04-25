package com.test.webui.service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by Alan on 2016/4/15.
 */
public class FileUtil {
    public static final String FILE_PATH = "static/images/movie/";
    //取文件列表
    public static String[] getFilelist(HttpServletRequest request){
        String[] filelist = null;
        try{
            File file = new File(getRealPathHead(request));
            filelist = file.list();
        }catch (IOException e){
            e.printStackTrace();
        }

        return filelist;
    }

    public static String getRealPathHead(HttpServletRequest request) throws IOException {
        String webRoot = request.getSession().getServletContext().getRealPath("/");
        String pathhead = ClassLoader.getSystemClassLoader().getResource("").toString();
        String path = pathhead + FILE_PATH;
        return path;
    }
}
