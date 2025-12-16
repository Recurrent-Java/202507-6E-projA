package com.example.springlesson.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class AdminProductForm {
    
    private Long productId;  // 更新時に使用
    
    @NotBlank(message = "商品名を入力してください")
    @Size(max = 255, message = "商品名は255文字以内で入力してください")
    private String productName;
    
    @NotNull(message = "価格を入力してください")
    @Min(value = 0, message = "価格は0円以上で入力してください")
    private Integer price;
    
    @NotNull(message = "在庫数を入力してください")
    @Min(value = 0, message = "在庫数は0以上で入力してください")
    private Integer stockQuantity;
    
    @Size(max = 2000, message = "説明文は2000文字以内で入力してください")
    private String description;
    
    @Size(max = 2000, message = "原材料詳細は2000文字以内で入力してください")
    private String ingredientDetail;
    
    @Size(max = 2000, message = "ブランドストーリーは2000文字以内で入力してください")
    private String brandStory;
    
    @Size(max = 500, message = "消費期限・保存方法は500文字以内で入力してください")
    private String expirationStorage;
    
    private Boolean isGiftAvailable = false;
    
    @NotBlank(message = "販売ステータスを選択してください")
    private String saleStatus;
    
    private Boolean isLimited = false;
    
    private Integer catId = 1;
    
    private Integer limitedFlg = 0;
    
    @NotBlank(message = "用途を入力してください")
    private String usage = "一般";
    
    private Integer season = 0;
}