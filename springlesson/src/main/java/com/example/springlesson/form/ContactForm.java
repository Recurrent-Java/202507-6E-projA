package com.example.springlesson.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class ContactForm {
  @NotBlank(message = "名前を入力してください")
  private String name;

  @NotBlank(message = "メールアドレスを入力してください")
  @Email(message = "有効なメールアドレスを入力してください")
  private String email;

  @NotBlank(message = "お問い合わせ内容を入力してください")
  @Size(min = 10, message = "内容は10文字以上で入力してください")
  private String content;
}