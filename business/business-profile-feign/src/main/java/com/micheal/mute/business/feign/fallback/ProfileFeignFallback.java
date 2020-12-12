package com.micheal.mute.business.feign.fallback;

import com.micheal.mute.business.dto.params.IconParam;
import com.micheal.mute.business.dto.params.PasswordParam;
import com.micheal.mute.business.dto.params.ProfileParam;
import com.micheal.mute.business.feign.ProfileFeign;
import com.micheal.mute.commons.dto.ResponseResult;
import com.micheal.mute.commons.utils.MapperUtils;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/24 16:59
 * @Description 个人信息服务熔断
 */
@Component
public class ProfileFeignFallback implements ProfileFeign {

    public static final String BREAKING_MESSAGE = "您的网络有问题，请检查";

    @Override
    public String info(String username) {
        try {
            return MapperUtils.obj2json(new ResponseResult<Void>(ResponseResult.CodeStatus.BREAKING, BREAKING_MESSAGE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String update(ProfileParam profileParam) {
        try {
            return MapperUtils.obj2json(new ResponseResult<>(ResponseResult.CodeStatus.BREAKING, BREAKING_MESSAGE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String modifyPassword(PasswordParam passwordParam) {
        try {
            return MapperUtils.obj2json(new ResponseResult<>(ResponseResult.CodeStatus.BREAKING, BREAKING_MESSAGE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String modifyIcon(IconParam iconParam) {
        try {
            return MapperUtils.obj2json(new ResponseResult<>(ResponseResult.CodeStatus.BREAKING, BREAKING_MESSAGE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
