package com.example.springlesson.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springlesson.entity.AllergenMaster;
import com.example.springlesson.form.RegisterForm;
import com.example.springlesson.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

  // 登録Service, Mapperなどは省略
  private final MemberService memberService; // Serviceを呼び出せるように定義

  private final com.example.springlesson.repository.AllergenMasterRepository allergenMasterRepository;

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
    List<String> selectedCodes = registerForm.getAllergy();
    System.out.println("=== デバッグ開始 ===");
    System.out.println("① 画面からの受信: " + selectedCodes);

    List<String> selectedNames = new java.util.ArrayList<>();

    // DBから全件取得
    List<AllergenMaster> masters = allergenMasterRepository.findAll();

    if (masters == null || masters.isEmpty()) {
      System.out.println("② エラー: DBからマスタが1件も取得できていません！");
    } else {
      System.out.println("② マスタ取得成功: " + masters.size() + "件取得しました");

      for (AllergenMaster m : masters) {
        // ここで中身が null かどうかを物理的に確認
        System.out.println("   -> DBの中身: Code=[" + m.getAllergenCode() + "], Name=[" + m.getAllergenName() + "]");
      }
    }

    if (selectedCodes != null && masters != null) {
      for (String sCode : selectedCodes) {
        for (AllergenMaster m : masters) {
          // nullチェックを入れた安全な比較
          String dbCode = (m.getAllergenCode() != null) ? m.getAllergenCode().trim() : "";
          if (dbCode.equalsIgnoreCase(sCode.trim())) {
            selectedNames.add(m.getAllergenName());
            System.out.println("③ 一致確認: " + sCode + " -> " + m.getAllergenName());
          }
        }
      }
    }

    System.out.println("④ 最終結果(selectedNames): " + selectedNames);
    System.out.println("=== デバッグ終了 ===");

    model.addAttribute("allergyNames", selectedNames);
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