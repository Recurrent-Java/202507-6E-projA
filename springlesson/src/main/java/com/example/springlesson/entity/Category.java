package com.example.springlesson.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "m_category") // 先ほどMySQLで作ったテーブル名
public class Category {

  @Id
  @Column(name = "cat_id")
  private Integer catId;

  @Column(name = "cat_name")
  private String catName;
}