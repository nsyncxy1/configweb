package com.dakai.readfile.controller;

import com.dakai.readfile.domain.AjaxResult;
import com.dakai.readfile.domain.UserVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
public class LoginController {
    @Value("${dakai.user.admin}")
    private String adminPassword;

    @Value("${dakai.user.test}")
    private String testPassword;

    @Value("${dakai.token}")
    private String token;

    @PostMapping()
    public AjaxResult login(@RequestBody UserVo userVo) {

        String username = userVo.getUsername();
        String password = userVo.getPassword();
        userVo.setToken(token);
        if (adminPassword!=null && "admin".equals(username) && adminPassword.equals(password)) {
            return AjaxResult.success("登录成功。",userVo);
        }
        if (testPassword!=null && "test".equals(username) && testPassword.equals(password) ) {
            return AjaxResult.success("登录成功。",userVo);
        }

        return AjaxResult.error("登录失败。。。");
    }


}
