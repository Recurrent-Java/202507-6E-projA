package com.example.springlesson.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springlesson.form.RegisterForm;
import com.example.springlesson.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

  // 登録Service, Mapperなどは省略
  private final MemberService memberService; // Serviceを呼び出せるように定義
  
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
   * 登録内容確認画面を表示する
   */
  @PostMapping("/confirm")
  public String confirmRegistration(@ModelAttribute RegisterForm registerForm, Model model) {
    // フォームの値を保持したまま confirm/confirm.html へ
    model.addAttribute("registerForm", registerForm);
    return "confirm/confirm";
  }

  /**
   * 登録処理を実行する
   */
  @PostMapping("/execute")
  public String execute(@ModelAttribute RegisterForm form) {
    memberService.registerNewMember(form);
    // 完了後は二重送信防止のため必ずリダイレクト
    return "redirect:/register/complete";
  }

  @GetMapping("/complete")
  public String complete() {
    return "complete/complete"; 
  }
}