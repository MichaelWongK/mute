package com.micheal.mute.commons.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/8 15:45
 * @Description
 */
public class IOUtils {

    /**
     * 输出流返回
     * @param response
     * @param inputStream
     * @throws Exception
     */
    public static void OutputResponse(HttpServletResponse response, InputStream inputStream) throws Exception {
        OutputStream outputStream = response.getOutputStream();
        // 输出资源内容
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inputStream.read(buffer, 0, 1024)) != -1 ) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.close();
    }
}
