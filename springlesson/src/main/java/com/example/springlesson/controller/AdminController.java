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

import com.example.springlesson.entity.MemberInfo;
import com.example.springlesson.entity.Product;
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

        // フォーム初期化（新規登録用）
        if (!model.containsAttribute("adminProductForm")) {
            model.addAttribute("adminProductForm", new AdminProductForm());
        }

        return "admin/index";
    }

    /**
     * 商品登録・更新処理
     */
    @PostMapping("/product/save")
    public String saveProduct(@Validated @ModelAttribute AdminProductForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // エラー時は画面再描画（一覧データも再取得が必要）
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
        System.out.println("削除対象ID: " + productId);

        if (productId != null) {
            adminService.deleteProduct(productId.intValue());
        }
        return "redirect:/admin#product-list";
    }

    /**
     * 会員削除処理
     * HTMLの <input name="delete-id"> を受け取る
     */
    @PostMapping("/member/delete")
    public String deleteMember(@RequestParam("delete-id") Integer memberId) {
        adminService.deleteMember(memberId);
        return "redirect:/admin#member-list";
    }
}