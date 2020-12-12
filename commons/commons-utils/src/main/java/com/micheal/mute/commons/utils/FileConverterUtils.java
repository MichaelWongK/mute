package com.micheal.mute.commons.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/23 21:59
 * @Description
 */
public class FileConverterUtils {

    public static File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若需要防止生成的临时文件重复,可以在文件名后添加随机码

        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
