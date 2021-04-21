package com.micheal.mute.cloud.controller;

import com.micheal.mute.cloud.dto.FileInfo;
import com.micheal.mute.commons.dto.ResponseResult;
import com.micheal.mute.commons.utils.OkHttpClientUtil;
import okhttp3.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/23 20:15
 * @Description
 */
@RestController
@RequestMapping(value = "/upload")
public class UploadController {

    public static final String ENDPOINT = "http://www.micheal.wang:10020/mongo";

    /**
     * 文件上传
     *
     * @param multipartFile @{code MultipartFile}
     * @return {@link ResponseResult< FileInfo >} 文件上传路径
     */
    @PostMapping(value = "")
    public ResponseResult<FileInfo> upload(MultipartFile multipartFile) {

        String objectId = "";
        try {
            // 解析响应结果集并返回
            Response response = OkHttpClientUtil.getInstance().postData1(ENDPOINT + "/upload", multipartFile);
            objectId = response.body().string();
            System.out.println(objectId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseResult<FileInfo>(ResponseResult.CodeStatus.OK,"文件上传成功", new FileInfo(ENDPOINT + "/read/" + objectId));

    }


}
