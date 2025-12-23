package com.example.springlesson.dto;

import java.text.NumberFormat;

import lombok.Data;

@Data
public class ProductDTO {

  private Long productId;
  private String productName;
  private Integer price;

  // 画像（DB未対応なら仮）
  private String imagePath;

  private Long catId;

  public String getFormattedPrice() {
    return NumberFormat.getNumberInstance().format(price);
  }
}