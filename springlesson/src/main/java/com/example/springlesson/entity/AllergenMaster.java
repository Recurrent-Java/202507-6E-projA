package com.example.springlesson.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "M_ALLERGEN")
@Data
public class AllergenMaster {
  @Id
  @Column(name = "allergen_code")
  private String allergenCode; // EGG, MILK など

  @Column(name = "allergen_name")
  private String allergenName; // 卵, 乳 など
}