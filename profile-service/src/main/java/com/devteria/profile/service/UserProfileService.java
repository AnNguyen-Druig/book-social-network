package com.devteria.profile.service;

import org.springframework.stereotype.Service;

import com.devteria.profile.dto.request.UserProfileCreationRequest;
import com.devteria.profile.dto.request.UserProfileUpdationRequest;
import com.devteria.profile.dto.response.UserProfileResponse;
import com.devteria.profile.entity.UserProfile;
import com.devteria.profile.mapper.UserProfileMapper;
import com.devteria.profile.repository.UserProfileRepo;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileService {
    UserProfileRepo userProfileRepo;
    UserProfileMapper userProfileMapper;

    // * Method createProfile
    // - Get request from UserProfileCreationRequest
    // - Change request to entity (userProfile)
    // - User repository to save entity to database(userProfile)
    // - Change from entity to response (userProfile to userProfileResponse)
    // - Return response from UserProfileResponse
    public UserProfileResponse createUserProfile(UserProfileCreationRequest request) {
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        userProfileRepo.save(userProfile);
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    // * Method getUserProfile
    // - Get profileId from Controller
    // - Use userProfile and userProfileRepo to findById(profileId)
    //      - If dont have profileId in database -> throw RunTimeException with notification
    //      - Else return result
    // - Use userProfileMapper to change userProfile to userProfileResponse
    public UserProfileResponse getUserProfile(String id) {
        UserProfile userProfile =
                userProfileRepo.findById(id).orElseThrow(() -> new RuntimeException("Profile not found!"));
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    // * Method updateUserProfile to update information of user
    // - User profileId to find user who want to update information
    //      - If not found throw RunTimeException
    //      - Else move to the next step
    // - Get UserProfileUpdationRequest request from Controller
    // - Set information follow by request
    // - Finally use repository to save new information for user who want to update info and return Response for
    // Controller
    public UserProfileResponse updateUserProfile(String id, UserProfileUpdationRequest request) {
        UserProfile userProfile =
                userProfileRepo.findById(id).orElseThrow(() -> new RuntimeException("Profile not found!"));
        userProfile.setFirstName(request.getFirstName());
        userProfile.setLastName(request.getLastName());
        userProfile.setCity(request.getCity());
        userProfileRepo.save(userProfile);
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public void deleteUserProfile(String id) {
        UserProfile userProfile =
                userProfileRepo.findById(id).orElseThrow(() -> new RuntimeException("Profile not found!"));
        userProfileRepo.delete(userProfile);
        System.out.println("Deleted Profile Successfully!");
    }
}
