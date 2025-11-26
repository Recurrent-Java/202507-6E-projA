package com.example.springlesson.form;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PurchaseForm {
  // 名前
  @NotBlank(message = "名前を入力してください。")
  private String name;
  // 住所
  @NotBlank(message = "住所を入力してください。")
  private String address;
}
