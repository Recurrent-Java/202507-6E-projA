package com.example.springlesson.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springlesson.entity.MemberInfo;
import com.example.springlesson.entity.Product;
import com.example.springlesson.form.AdminMemberForm; // 追加
import com.example.springlesson.form.AdminProductForm;
import com.example.springlesson.service.AdminService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
  private final AdminService adminService;

  @GetMapping
  public String index(Model model) {
    List<Product> productList = adminService.findAllProducts();
    model.addAttribute("productList", productList);
    List<MemberInfo> memberList = adminService.findAllMembers();
    model.addAttribute("memberList", memberList);
    // 商品フォーム初期化
    if (!model.containsAttribute("adminProductForm")) {
      model.addAttribute("adminProductForm", new AdminProductForm());
    }
    // 会員用フォーム初期化（追加）
    if (!model.containsAttribute("adminMemberForm")) {
      model.addAttribute("adminMemberForm", new AdminMemberForm());
    }
    return "admin/index";
  }

  /**
   * 商品登録・更新処理
   */
  @PostMapping("/product/save")
  public String saveProduct(@Validated @ModelAttribute AdminProductForm form, BindingResult result, Model model) {
    if (result.hasErrors()) {
      return index(model);
    }
    adminService.saveProduct(form);
    return "redirect:/admin#product-list";
  }

  /**
   * 商品削除処理
   */
  @PostMapping("/product/delete")
  public String deleteProduct(@RequestParam("productId") Long productId) {
    if (productId != null) {
      adminService.deleteProduct(productId.intValue());
    }
    return "redirect:/admin#product-list";
  }

  /**
   * 会員削除処理
   * @Validated を付けて未入力チェックを行い、
   * 内部では成功している削除ロジックを確実に実行します。
   */
  @PostMapping("/member/delete")
  public String deleteMember(
      @Validated @ModelAttribute AdminMemberForm adminMemberForm,
      BindingResult result,
      RedirectAttributes redirectAttributes,
      Model model) {
    // 1. バリデーションエラーがある場合（未入力時）
    if (result.hasErrors()) {
      // エラー情報をリダイレクト先に保持させる
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.adminMemberForm", result);
      redirectAttributes.addFlashAttribute("adminMemberForm", adminMemberForm);
      return "redirect:/admin#member-delete";
    }
    // 2. バリデーションを通過した場合：
    // 確実に削除するために、フォームから取得したIDを使って削除を実行
    Integer memberId = adminMemberForm.getMemberId();
    adminService.deleteMember(memberId);
    return "redirect:/admin#member-list";
  }
}