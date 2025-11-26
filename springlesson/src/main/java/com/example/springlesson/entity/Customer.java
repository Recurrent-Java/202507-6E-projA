package com.example.springlesson.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity  // DBからのデータ格納用クラスであることを表す
@Table(name="customer")
@Data  // getter/setterを自動で作成してくれる
public class Customer {
  @Id  // 主キーを表す
  @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENTの場合付与が必要
  @Column(name="id")  // DB上のカラム名と変数名を別にしたい場合は必須
  private Integer id;
  
  @Column(name="login")  // DB上のカラム名と変数名を別にしたい場合は必須
  private String login;
  
  @Column(name="password")  // DB上のカラム名と変数名を別にしたい場合は必須
  private String password;
}
