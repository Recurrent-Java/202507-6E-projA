package com.example.springlesson.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity  // DBからのデータ格納用クラスであることを表す
@Table(name="purchase")
@Data  // getter/setterを自動で作成してくれる
@NoArgsConstructor  // 引数なしのコンストラクターをlombokが作成してくれる
@AllArgsConstructor // 全フィールドを使用したコンストラクターをlombokが作成してくれる
public class Purchase {
  // 購入ID
  @Id  // 主キーを表す
  @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENTの場合付与が必要
  @Column(name="id")  // DB上のカラム名と変数名を別にしたい場合は必須
  private Integer id;
  // 商品ID
  @Column(name="product_id")
  private Integer productId;
  // 商品名
  @Column(name="product_name")
  private String productName;
  // 価格
  @Column(name="product_price")
  private Integer productPrice;
  // 個数
  @Column(name="product_count")
  private Integer productCount;
  // 名前
  @Column(name="customer_name")
  private String customerName;
  // 住所
  @Column(name="customer_address")
  private String customerAddress;
}
