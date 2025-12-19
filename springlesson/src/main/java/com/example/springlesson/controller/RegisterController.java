package com.example.springlesson.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springlesson.form.RegisterForm;

@Controller
@RequestMapping("/register")
public class RegisterController {

  // 登録Service, Mapperなどは省略

  /**
   * 新規会員登録画面を表示する
   */
  @GetMapping
  public String showRegisterForm(Model model) {
    // RegisterFormをモデルに格納 (Thymeleafのフィールドバインディングに必要)
    model.addAttribute("registerForm", new RegisterForm());
    // src/main/resources/templates/register/register.html を返す
    return "register/register";
  }

  /**
   * 登録内容確認、または登録処理（今回は確認画面への遷移を想定）
   */
  @PostMapping("/confirm")
  public String confirmRegistration(RegisterForm form, Model model) {
    // 実際の処理: バリデーションチェック、確認画面へのデータ転送など
    // 今回はデモとして、登録ページに戻るか、成功ページへリダイレクト

    // 登録成功と仮定し、ログインページへリダイレクト
    return "redirect:/login?registered";
  }
}