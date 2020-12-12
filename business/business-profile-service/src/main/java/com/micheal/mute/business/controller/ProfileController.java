package com.micheal.mute.business.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.micheal.mute.business.controller.fallback.ProfileControllerFallback;
import com.micheal.mute.business.dto.UmsAdminDTO;
import com.micheal.mute.business.dto.params.ProfileParam;
import com.micheal.mute.business.dto.params.IconParam;
import com.micheal.mute.business.dto.params.PasswordParam;
import com.micheal.mute.commons.dto.ResponseResult;
import com.micheal.mute.provider.api.UmsAdminService;
import com.micheal.mute.provider.domain.UmsAdmin;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/21 20:17
 * @Description 个人信息管理
 */
@RestController
@RequestMapping(value = "/profile")
public class ProfileController {

    @Reference(version = "1.0.0")
    private UmsAdminService umsAdminService;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 获取个人信息
     *
     * @param username {@code String} 用户名
     * @return {@link ResponseResult}
     */
    @GetMapping(value = "/info/{username}")
    @SentinelResource(value = "info", fallback = "infoFallback", fallbackClass = ProfileControllerFallback.class)
    public ResponseResult<UmsAdminDTO> info(@PathVariable String username) {
        UmsAdmin umsAdmin = umsAdminService.get(username);
        UmsAdminDTO umsAdminDTO = new UmsAdminDTO();
        BeanUtils.copyProperties(umsAdmin, umsAdminDTO);
        return new ResponseResult<UmsAdminDTO>(ResponseResult.CodeStatus.OK, "查询用户信息", umsAdminDTO);
    }


    /**
     * 更新个人信息
     *
     * @param profileParam {@link ProfileParam}
     * @return {@link ResponseResult}
     */
    @PostMapping(value = "update")
    public ResponseResult<Void> update(@RequestBody ProfileParam profileParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(profileParam, umsAdmin);
        int result = umsAdminService.update(umsAdmin);

        // 成功
        if (result > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "更新个人信息成功");
        }

        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "更新个人信息失败");
    }

    /**
     * 修改密码
     *
     * @param passwordParam {@link PasswordParam}
     * @return {@link ResponseResult}
     */
    @PostMapping(value = "modify/password")
    public ResponseResult<Void> modifyPassword(@RequestBody PasswordParam passwordParam) {
        UmsAdmin umsAdmin = umsAdminService.get(passwordParam.getUsername());

        // 判断旧密码是否正确
        if (passwordEncoder.matches(passwordParam.getOldPassword(), umsAdmin.getPassword())) {
            int result = umsAdminService.modifyPassword(umsAdmin.getUsername(), passwordParam.getNewPassword());
            if (result > 0) {
                return new ResponseResult<Void>(ResponseResult.CodeStatus.OK, "修改密码成功");
            }
        } else {
            return new ResponseResult<Void>(ResponseResult.CodeStatus.FAIL, "旧密码不匹配，请重试");
        }

        return new ResponseResult<Void>(ResponseResult.CodeStatus.FAIL, "修改密码失败");
    }

    @PostMapping(value = "modify/icon")
    public ResponseResult<Void> modifyIcon(@RequestBody IconParam iconParam) {
        int result = umsAdminService.modifyIcon(iconParam.getUsername(), iconParam.getPath());

        // 成功
        if (result > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "更新头像成功");
        }

        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "更新头像失败");
    }
}
