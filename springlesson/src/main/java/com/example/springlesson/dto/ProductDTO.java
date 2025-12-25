package com.example.springlesson.dto;

import java.text.NumberFormat;
import java.util.List;
import java.util.ArrayList;

import lombok.Data;

@Data
public class ProductDTO {

  private Long productId;
  private String productName;
  private Integer price;

  // 画像（DB未対応なら仮）
  private String imagePath;

  private Long catId;

  // 複数カテゴリー対応
  private List<CategoryInfo> categories = new ArrayList<>();

  public String getFormattedPrice() {
    return NumberFormat.getNumberInstance().format(price);
  }

  // カテゴリー情報を保持する内部クラス
  @Data
  public static class CategoryInfo {
    private Integer catId;
    private String catName;

    public CategoryInfo() {}

    public CategoryInfo(Integer catId, String catName) {
      this.catId = catId;
      this.catName = catName;
    }
  }
}