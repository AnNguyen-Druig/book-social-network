package com.devteria.identity.configuration;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * Interceptor của OpenFeign dùng để chuyển tiếp header Authorization từ request hiện tại
 * sang request gọi service khác.
 *
 * <p>Identity-service cần interceptor này khi gọi profile-service để profile-service vẫn
 * nhận được token của người dùng đang thao tác.
 */
@Slf4j
public class AuthenticationRequestInterceptor implements RequestInterceptor {
    /**
     * Được OpenFeign gọi trước khi gửi request ra ngoài.
     *
     * <p>Method lấy Authorization header từ HttpServletRequest hiện tại rồi gắn lại vào
     * RequestTemplate của Feign, nhờ vậy request liên-service vẫn giữ ngữ cảnh xác thực.
     */
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        var authHeader = servletRequestAttributes.getRequest().getHeader("Authorization");

        log.info("Header: {}", authHeader);
        // Chỉ forward khi header có giá trị thật để tránh gửi header rỗng sang service khác.
        if (StringUtils.hasText(authHeader)) template.header("Authorization", authHeader);
    }
}
