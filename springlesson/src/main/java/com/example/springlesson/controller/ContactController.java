package com.example.springlesson.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springlesson.form.ContactForm;

@Controller
@RequestMapping("/contact")
public class ContactController {

  /**
   * お問い合わせ入力画面を表示
   */
  @GetMapping
  public String showForm(Model model) {
    model.addAttribute("contactForm", new ContactForm());
    return "contact/contact_form";
  }

  /**
   * 送信処理
   */
  @PostMapping("/send")
  public String sendContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result) {
    // バリデーションエラーがあれば入力画面に戻る
    if (result.hasErrors()) {
      return "contact/contact_form";
    }

    // コンソールに内容を表示（動作確認用）
    System.out.println("お問い合わせ受信: " + contactForm);

    // 送信完了画面へリダイレクト
    return "redirect:/contact/complete";
  }

  /**
   * 送信完了画面を表示
   */
  @GetMapping("/complete")
  public String complete() {
    return "contact/contact_complete";
  }
}