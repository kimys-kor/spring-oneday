package com.oneday.api.common.security;

import com.oneday.api.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = (String)request.getAttribute("exception");

        System.out.println(request.getAttribute("exception")+"22ll");

        if(exception == null) {
            setResponse(response, ErrorCode.AUTHENTICATION_FAILED);
        }
        //잘못된 타입의 토큰인 경우
        else if(exception.equals(ErrorCode.INVALID_TOKEN.name())) {
            setResponse(response, ErrorCode.INVALID_TOKEN);
        }
        //토큰 만료된 경우
        else if(exception.equals(ErrorCode.EXPIRED_TOKEN.name())) {
            setResponse(response, ErrorCode.EXPIRED_TOKEN);
        }
        //지원되지 않는 토큰인 경우
        else if(exception.equals(ErrorCode.UNSUPPORTED_TOKEN.name())) {
            setResponse(response, ErrorCode.UNSUPPORTED_TOKEN);
        }
        else {
            setResponse(response, ErrorCode.ACCESS_DENIED);
        }
    }
    //한글 출력을 위해 getWriter() 사용
    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("message", errorCode.getMessage());
        responseJson.put("code", errorCode.getCode());

        response.getWriter().print(responseJson);
    }
}