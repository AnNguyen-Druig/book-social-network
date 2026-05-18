package com.devteria.profile.entity;

import java.time.LocalDate;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import lombok.*;
import lombok.experimental.FieldDefaults;

// In GraphSQL: @Node ~ @Entity to point database know this class is entity
@Node("user_profile")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfile {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    String id;

    @Property("userId") // In GraphSQL: @Property ~ @Column
    String userId;

    // Couldn't name for field, if @Property is not use/empty -> name of field in database will be same name in OOP
    String firstName;
    String lastName;
    LocalDate dob;
    String city;
}
