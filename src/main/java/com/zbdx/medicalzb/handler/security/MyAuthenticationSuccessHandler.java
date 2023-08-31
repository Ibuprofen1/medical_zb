package com.zbdx.medicalzb.handler.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zbdx.medicalzb.model.AccountInfoModel;
import com.zbdx.medicalzb.model.AccountModel;
import com.zbdx.medicalzb.util.JwtUtils;
import com.zbdx.medicalzb.util.Msg;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理器
 */

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        AccountModel accountModel = (AccountModel) authentication.getPrincipal();
        String jwtToken = JwtUtils.getJwtToken(accountModel.getId(), accountModel.getUname(), accountModel.getUtype());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AccountInfoModel infoModel = new AccountInfoModel();
        infoModel.setRealname(accountModel.getUrealName());
        infoModel.setUtype(accountModel.getUtype());
        infoModel.setUtype(infoModel.getUtype().substring("ROLE_".length()));
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(Msg.success().mess("登录成功").data("token",jwtToken).data("userInfo",infoModel)));
    }
}
