package com.challengerstory.chattingstory.user.command.aggregate.dto.normal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NormalLoginResponseDTO {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("time_stamp")
    private LocalDateTime timestamp;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("email")
    private String userEmail;

    @JsonProperty("user_type")
    private String userType;



}
