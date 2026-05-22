package com.devteria.identity.configuration;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

// Interceptor này được gắn riêng cho Feign client gọi profile-service để chuyển tiếp token
// từ request hiện tại sang request nội bộ giữa các microservice.
@Slf4j
// Không bật @Component để tránh interceptor tự động áp dụng cho mọi Feign client trong identity-service.
// @Component
public class AuthenticationRequestInterceptor implements RequestInterceptor {

    // Feign gọi method này trước khi gửi request, vì vậy đây là điểm phù hợp để bổ sung header chung.
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // Lấy thông tin HTTP request đang được xử lý bởi identity-service.
        // Token của người dùng nằm trong header Authorization của request gốc.
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        var authHeader = servletRequestAttributes.getRequest().getHeader("Authorization");
        log.info("Header: {}", authHeader);

        // Chỉ gắn header khi token thật sự tồn tại để tránh gửi Authorization rỗng sang profile-service.
        if (StringUtils.hasText(authHeader)) requestTemplate.header("Authorization", authHeader);
    }
}
