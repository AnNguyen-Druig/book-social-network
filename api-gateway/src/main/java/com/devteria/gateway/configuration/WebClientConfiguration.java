package com.devteria.gateway.configuration;

import com.devteria.gateway.repository.IdentityClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import java.util.List;

/**
 * Khai báo các bean dùng chung của API Gateway.
 *
 * <p>Gateway cần WebClient để gọi identity-service khi kiểm tra token và cần CORS filter
 * để frontend có thể gửi request qua gateway từ một domain/port khác.
 */
@Configuration
public class WebClientConfiguration {
    /**
     * Tạo WebClient có sẵn base URL của identity-service.
     *
     * <p>IdentityClient sẽ dùng bean này để gọi các API nội bộ như introspect token.
     */
    @Bean
    WebClient webClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8080/identity")
                .build();
    }

    /**
     * Cấu hình CORS ở tầng API Gateway cho toàn bộ endpoint.
     *
     * <p>API Gateway đang dùng WebFlux nên cần CorsWebFilter reactive thay vì CORS filter
     * của Spring MVC. Nếu thiếu bean này, browser có thể chặn request từ frontend dù backend
     * xử lý được request đó.
     */
    @Bean
    CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // Mở CORS rộng để thuận tiện khi phát triển local; production nên whitelist domain tin cậy.
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        // Áp dụng cấu hình CORS cho mọi route đi qua gateway.
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsWebFilter(urlBasedCorsConfigurationSource);
    }

    /**
     * Tạo implementation runtime cho IdentityClient từ WebClient.
     *
     * <p>Cách này giúp gọi identity-service bằng interface Java thay vì tự ghép URL/request
     * ở từng nơi trong gateway.
     */
    @Bean
    IdentityClient identityClient(WebClient webClient){
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient)).build();

        return httpServiceProxyFactory.createClient(IdentityClient.class);
    }
}
