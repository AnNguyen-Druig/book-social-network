package com.devteria.profile.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Format response chuẩn của profile-service.
 *
 * <p>Dùng generic T để cùng một wrapper có thể trả về nhiều kiểu dữ liệu khác nhau, ví dụ
 * một UserProfileResponse hoặc danh sách UserProfileResponse.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    // Mã mặc định cho request thành công; các lỗi sẽ được GlobalExceptionHandler ghi đè.
    @Builder.Default
    private int code = 1000;

    // Thông điệp mô tả lỗi hoặc trạng thái xử lý, chỉ serialize khi khác null.
    private String message;
    // Payload chính của response, có kiểu linh hoạt theo từng API.
    private T result;
}
