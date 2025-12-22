package com.example.springlesson.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AdminProductForm {

    private Integer productId;

    @NotBlank(message = "商品名を入力してください")
    private String productName;

    @NotNull(message = "価格を入力してください")
    @Min(value = 0, message = "価格は0円以上で入力してください")
    private Integer price;
    
    @NotNull(message = "在庫数を入力してください")
    @Min(value = 0, message = "在庫数は0以上で入力してください")
    private Integer stockQuantity;
}