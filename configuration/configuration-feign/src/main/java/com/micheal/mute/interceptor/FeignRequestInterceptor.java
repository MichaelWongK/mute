package com.micheal.mute.interceptor;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Enumeration;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/21 21:10
 * @Description
 */
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        // 封装请求头
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = request.getHeader(name);
                template.header(name, value);
            }
        }

        // 封装请求体
        Enumeration<String> parameterNames = request.getParameterNames();
        StringBuilder body = new StringBuilder();
        if (parameterNames != null) {
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                String value = request.getParameter(name);

                // 将令牌放入header
                if ("access_token".equals(name)) {
                    template.header("authorization", "Bearer " + value);
                } else {
                    // 其它参数封装成请求体
                    body.append(name).append("=").append(value).append("&");
                }


            }
        }

        if (body.length() > 0) {
            body.deleteCharAt(body.length() - 1);
            template.body(Request.Body.bodyTemplate(body.toString(), Charset.defaultCharset()));
        }
    }
}
