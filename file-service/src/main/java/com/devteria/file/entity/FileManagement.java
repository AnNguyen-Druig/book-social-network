package com.devteria.file.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "file_mgmt")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileManagement {

    @MongoId
    String id;
    String ownerId;
    String contentType;
    long size;
    String md5Checksum;
    String path;
}
