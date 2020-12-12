package com.micheal.mute.business.controller;

import com.google.common.collect.Maps;
import com.micheal.mute.business.dto.LoginInfo;
import com.micheal.mute.business.feign.ProfileFeign;
import com.micheal.mute.business.dto.LoginParam;
import com.micheal.mute.commons.dto.ResponseResult;
import com.micheal.mute.commons.utils.MapperUtils;
import com.micheal.mute.commons.utils.OkHttpClientUtil;
import com.micheal.mute.provider.domain.UmsAdmin;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/20 17:53
 * @Description 登录管理
 */
@RestController
public class LoginController {

    public static final String URL_OAUTH_TOKEN = "http://localhost:9001/oauth/token";

    @Value("${business.oauth2.grant_type}")
    private String oauth2GrantType;

    @Value("${business.oauth2.client_id}")
    private String oauth2ClientId;

    @Value("${business.oauth2.client_secret}")
    private String oauth2ClientSecret;

    @Resource
    private UserDetailsService userDetailsService;
    
    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private TokenStore tokenStore;

    @Resource
    private ProfileFeign profileFeign;

    @PostMapping(value = "/user/login")
    public ResponseResult<Map<String, Object>> login(@RequestBody LoginParam loginParam) {
        // 封装返回结果集
        Map<String, Object> map = Maps.newHashMap();

        // 验证账号密码
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginParam.getUsername());
        if (userDetails == null || !passwordEncoder.matches(loginParam.getPassword(), userDetails.getPassword())){
            return new ResponseResult<Map<String, Object>>(ResponseResult.CodeStatus.ILLEGAL_REQUEST, "账号或密码错误", null);
        }

        // 通过 HTTP 客户端请求登录接口
        Map<String, String> params = Maps.newHashMap();
        params.put("username", loginParam.getUsername());
        params.put("password", loginParam.getPassword());
        params.put("grant_type", oauth2GrantType);
        params.put("client_id", oauth2ClientId);
        params.put("client_secret", oauth2ClientSecret);
        try {
            // 解析响应结果集并返回
            Response response = OkHttpClientUtil.getInstance().postData(URL_OAUTH_TOKEN, params);
            String jsonString = Objects.requireNonNull(response.body().string());
            Map<String, Object> jsonMap = MapperUtils.json2map(jsonString);
            String token = String.valueOf(jsonMap.get("access_token"));
            map.put("token", token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseResult<Map<String, Object>>(ResponseResult.CodeStatus.OK, "登录成功", map);
    }

    @GetMapping(value = "/user/info")
    public ResponseResult<LoginInfo> info() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String jsonString = profileFeign.info(authentication.getName());
        UmsAdmin umsAdmin = MapperUtils.json2pojoByTree(jsonString, "data", UmsAdmin.class);

        // 熔断后的结果
        if (umsAdmin == null) {
            return MapperUtils.json2pojo(jsonString, ResponseResult.class);
        }

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setName(umsAdmin.getUsername());
        loginInfo.setNickName(umsAdmin.getNickName());
        loginInfo.setAvatar(umsAdmin.getIcon());
        return new ResponseResult<LoginInfo>(ResponseResult.CodeStatus.OK, "获取用户信息", loginInfo);
    }

    @PostMapping(value = "/user/logout")
    public ResponseResult<Void> logout(HttpServletRequest request) {
        String token = request.getParameter("access_token");
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        tokenStore.removeAccessToken(oAuth2AccessToken);
        return new ResponseResult<Void>(ResponseResult.CodeStatus.OK, "用户注销", null);
    }
}
