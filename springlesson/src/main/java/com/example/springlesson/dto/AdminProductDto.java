package com.example.springlesson.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminProductDto {
    
    private Long productId;
    private String productName;
    private Integer price;
    private Integer stockQuantity;
    private String allergens;  // カンマ区切りのアレルゲン名
    private String saleStatus;
    private Boolean isLimited;
    
    // 詳細表示用
    private String description;
    private String ingredientDetail;
    private String brandStory;
    private String expirationStorage;
    private Boolean isGiftAvailable;
    
    // アレルゲン情報リスト（詳細用）
    private List<AdminProductAllergenDto> allergenList;
}