package com.devteria.identity.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.devteria.identity.dto.request.UserProfileCreationRequest;
import com.devteria.identity.dto.request.UserProfileUpdationRequest;
import com.devteria.identity.dto.response.UserProfileResponse;

@FeignClient(name = "profile-service", url = "${app.services.profile}")
public interface ProfileClient {

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse createUserProfile(UserProfileCreationRequest request);

    @GetMapping(value = "/users/{profileId}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse getUserProfile(@PathVariable String profileId);

    @PutMapping(value = "/users/{profileId}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse updateUserProfile(
            @PathVariable String profileId, @RequestBody UserProfileUpdationRequest request);

    @DeleteMapping(value = "/users/{profileId}", produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteUserProfile(@PathVariable String profileId);
}
