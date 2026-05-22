package com.devteria.profile.configuration;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.devteria.profile.dto.ApiResponse;
import com.devteria.profile.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Điểm xử lý khi request không được xác thực trong profile-service.
 *
 * <p>Thay vì để Spring Security trả response mặc định, class này trả về ApiResponse thống
 * nhất với format lỗi của toàn bộ service.
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * Ghi response JSON 401 khi token thiếu, sai hoặc không đọc được.
     */
    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        // Đồng bộ HTTP status và body lỗi theo ErrorCode để client dễ xử lý.
        response.setStatus(errorCode.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}
