package com.example.springlesson.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminMemberDto {
    
    private Long memberId;
    private String fullName;
    private String email;
    private LocalDateTime registrationDate;
    private LocalDateTime lastLoginAt;
    private Integer status;
    
    // 詳細表示用
    private String phoneNumber;
    private String postalCode;
    private String prefecture;
    private String addressLine1;
    private String addressLine2;
}