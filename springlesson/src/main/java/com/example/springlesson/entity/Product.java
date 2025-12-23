package com.example.springlesson.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "PRODUCT")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private Integer productId;

  @Column(name = "product_name", nullable = false)
  private String productName;

  // DB定義に存在するが、管理画面フォームにはない項目（NOT NULL対応が必要）
  @Column(name = "CAT_ID", nullable = false)
  private Integer catId;

  @Column(name = "LIMITED_FLG", nullable = false)
  private Integer limitedFlg;

  @Column(name = "`USAGE`", nullable = false)
  private String usage;

  @Column(name = "SEASON", nullable = false)
  private Integer season;

  @Column(name = "sale_status", nullable = false)
  private String saleStatus;

  @Column(name = "is_limited", nullable = false)
  private Integer isLimited; // TINYINT(1)をIntegerでマッピング

  // フォームで入力される項目
  @Column(name = "price", nullable = false)
  private Integer price;

  @Column(name = "stock_quantity", nullable = false)
  private Integer stockQuantity;

  // エラーの原因となったフィールド群
  @Column(name = "is_gift_available", nullable = false)
  private Integer isGiftAvailable;

  @Column(name = "is_low_stock", nullable = false)
  private Integer isLowStock;

  @Column(name = "image_url") // DBの列名(image_url)とJavaの変数名を紐付け
  private String imagePath;

  // その他のフィールドは省略...
  // @Column(name = "PROD_DESC")
  // private String prodDesc;
}