package com.example.springlesson.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminProductAllergenDto {
    
    private String allergenCode;
    private String allergenName;
    private Boolean isContained;
    private String note;
}