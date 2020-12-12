package com.micheal.mute.business;

import com.google.common.collect.Maps;
import com.micheal.mute.commons.utils.MapperUtils;
import com.micheal.mute.commons.utils.OkHttpClientUtil;
import okhttp3.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/20 20:07
 * @Description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OKHttp3Tests {

    @Test
    public void testGet() {
        String url = "https://www.baidu.com";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPost() {
        String url = "http://localhost:9001/oauth/token";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("username", "admin")
                .add("password", "123456")
                .add("grant_type", "password")
                .add("client_id", "client")
                .add("client_secret", "secret")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUtilPost() {
        String url = "http://localhost:9001/oauth/token";
        Map<String, String> params = Maps.newHashMap();
        params.put("username", "admin");
        params.put("password", "123456");
        params.put("grant_type", "password");
        params.put("client_id", "client");
        params.put("client_secret", "secret");
        Response response = OkHttpClientUtil.getInstance().postData(url, params);
        try {
            Map<String, Object> jsonMap = MapperUtils.json2map(response.body().string());
            String token = String.valueOf(jsonMap.get("access_token"));
            System.out.println(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
