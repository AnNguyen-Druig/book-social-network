package com.devteria.identity.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.devteria.identity.configuration.AuthenticationRequestInterceptor;
import com.devteria.identity.dto.request.ApiResponse;
import com.devteria.identity.dto.request.ProfileCreationRequest;
import com.devteria.identity.dto.response.UserProfileResponse;

/**
 * Feign client đại diện cho API nội bộ của profile-service.
 *
 * <p>Identity-service dùng client này sau khi tạo user để tạo profile tương ứng ở
 * profile-service, giữ dữ liệu định danh và hồ sơ người dùng tách biệt theo microservice.
 */
@FeignClient(
        name = "profile-service",
        url = "${app.services.profile}",
        configuration = {AuthenticationRequestInterceptor.class})
public interface ProfileClient {
    /**
     * Gửi yêu cầu tạo profile sang endpoint nội bộ của profile-service.
     *
     * <p>Endpoint này chỉ phục vụ giao tiếp service-to-service, không phải API public cho
     * client bên ngoài.
     */
    @PostMapping(value = "/internal/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request);
}
