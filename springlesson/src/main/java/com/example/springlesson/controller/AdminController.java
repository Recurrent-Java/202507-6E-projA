// src/main/java/com/example/springlesson/controller/AdminController.java
package com.example.springlesson.controller;

import java.io.IOException;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springlesson.entity.MemberInfo;
import com.example.springlesson.entity.Product;
import com.example.springlesson.form.AdminMemberForm;
import com.example.springlesson.form.AdminProductForm;
import com.example.springlesson.service.AdminService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /* -------------------- 画面表示 -------------------- */
    @GetMapping
    public String index(Model model) {
        // 商品一覧
        List<Product> productList = adminService.findAllProducts();
        model.addAttribute("productList", productList);

        // 会員一覧
        List<MemberInfo> memberList = adminService.findAllMembers();
        model.addAttribute("memberList", memberList);

        /* フォーム初期化（リダイレクト時に保持される場合は無視） */
        if (!model.containsAttribute("adminProductForm")) {
            model.addAttribute("adminProductForm", new AdminProductForm());
        }
        if (!model.containsAttribute("adminMemberForm")) {
            model.addAttribute("adminMemberForm", new AdminMemberForm());
        }

        return "admin/index";
    }

    /* -------------------- 商品登録／更新 -------------------- */
    @PostMapping("/product/save")
    public String saveProduct(@Valid @ModelAttribute("adminProductForm") AdminProductForm form,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) throws IOException {
        if (result.hasErrors()) {
            // バリデーション失敗はフラッシュ属性に保持してリダイレクト
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.adminProductForm", result);
            redirectAttributes.addFlashAttribute("adminProductForm", form);
            return "redirect:/admin#product-manage";
        }

        adminService.saveProduct(form);
        return "redirect:/admin#product-list";
    }

    /* -------------------- 商品削除 -------------------- */
    @PostMapping("/product/delete")
    public String deleteProduct(@RequestParam("productId") Long productId) {
        if (productId != null) {
            adminService.deleteProduct(productId.intValue());
        }
        return "redirect:/admin#product-list";
    }

    /* -------------------- 会員削除 -------------------- */
    @PostMapping("/member/delete")
    public String deleteMember(@Valid @ModelAttribute("adminMemberForm") AdminMemberForm form,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {           // 未入力・不正入力時
            redirectAttributes.addFlashAttribute(
                "org.springframework.validation.BindingResult.adminMemberForm", result);
            redirectAttributes.addFlashAttribute("adminMemberForm", form);
            return "redirect:/admin#member-delete";
        }

        Integer memberId = form.getMemberId();
        adminService.deleteMember(memberId);

        // 成功時は会員一覧へ
        return "redirect:/admin#member-list";
    }
}