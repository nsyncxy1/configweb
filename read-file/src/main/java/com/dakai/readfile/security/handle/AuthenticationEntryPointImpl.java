package com.dakai.readfile.security.handle;

import cn.hutool.json.JSON;
import com.dakai.readfile.domain.AjaxResult;
import com.dakai.readfile.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 * 
 * @author ruoyi
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException
    {
        String requestURI = request.getRequestURI();
        String msg = "请求访问："+ requestURI +"，认证失败，无法访问系统资源";
        ServletUtils.renderString(response, msg);
    }
}
