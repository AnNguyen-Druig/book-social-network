package com.devteria.profile.controller;

import org.springframework.web.bind.annotation.*;

import com.devteria.profile.dto.request.UserProfileCreationRequest;
import com.devteria.profile.dto.request.UserProfileUpdationRequest;
import com.devteria.profile.dto.response.UserProfileResponse;
import com.devteria.profile.service.UserProfileService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/users")
    UserProfileResponse createUserProfile(@RequestBody UserProfileCreationRequest request) {
        return userProfileService.createUserProfile(request);
    }

    @GetMapping("/users/{profileId}")
    UserProfileResponse getUserProfile(@PathVariable String profileId) {
        return userProfileService.getUserProfile(profileId);
    }

    @PutMapping("/users/{profileId}")
    UserProfileResponse updateUserProfile(
            @PathVariable String profileId, @RequestBody UserProfileUpdationRequest request) {
        return userProfileService.updateUserProfile(profileId, request);
    }

    @DeleteMapping("/users/{profileId}")
    void deleteUserProfile(@PathVariable String profileId) {
        userProfileService.deleteUserProfile(profileId);
    }

    @DeleteMapping("/users/{userId}")
    void deleteUserProfileByUserId(@PathVariable String userId) {
        userProfileService.deleteUserProfileByUserId(userId);
    }
}
