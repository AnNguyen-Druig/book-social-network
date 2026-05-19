package com.devteria.identity.dto.request;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    //    String firstName;
    //    String lastName;
    //
    //    @DobConstraint(min = 18, message = "INVALID_DOB")
    //    LocalDate dob;

    List<String> roles;
}
