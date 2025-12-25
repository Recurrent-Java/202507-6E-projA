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
@Table(name = "product") // 小文字にする
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private Integer productId;

  @Column(name = "product_name", nullable = false)
  private String productName;

  // ここを small snake case (cat_id) に合わせる
  @Column(name = "cat_id", nullable = false)
  private Integer catId;

  @Column(name = "LIMITED_FLG", nullable = false)
  private Integer limitedFlg;

  // 予約語対策
  @Column(name = "`USAGE`", nullable = false)
  private String usage;

  @Column(name = "SEASON", nullable = false)
  private Integer season;

  @Column(name = "sale_status", nullable = false)
  private String saleStatus;

  @Column(name = "is_limited", nullable = false)
  private Integer isLimited;

  @Column(name = "price", nullable = false)
  private Integer price;

  @Column(name = "stock_quantity", nullable = false)
  private Integer stockQuantity;

  @Column(name = "is_gift_available", nullable = false)
  private Integer isGiftAvailable;

  @Column(name = "is_low_stock", nullable = false)
  private Integer isLowStock;

  @Column(name = "image_url")
  private String imagePath;
}