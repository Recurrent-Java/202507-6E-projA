package com.example.springlesson.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ALLERGY_SETTING")
@Data
public class AllergySetting {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "setting_id")
  private Long settingId;

  @Column(name = "member_id")
  private Long memberId;

  @Column(name = "allergen_code")
  private String allergenCode;
}