package com.example.springlesson.form;

// ★追加: java.util.List
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterForm {
  // ステップ1: 基本情報
  @NotBlank(message = "姓を入力してください。")
  private String lastName;

  @NotBlank(message = "名を入力してください。")
  private String firstName;

  @NotBlank(message = "メールアドレスを入力してください。")
  @Email(message = "メールアドレスの形式が正しくありません。")
  private String email; // ログインIDとして使用

  @Size(min = 8, message = "パスワードは8文字以上で入力してください。")
  private String password;

  private String passwordConfirm;

  private String phone;

  private String birthYear;
  private String birthMonth;
  private String birthDay;

  // ステップ2: 住所情報
  @Size(min = 7, max = 7, message = "郵便番号は7桁で入力してください。")
  private String postalCode;
  private String prefecture;
  private String city;
  private String address1;
  private String address2;

  // ステップ3: アレルギー情報
  private List<String> allergy;

}