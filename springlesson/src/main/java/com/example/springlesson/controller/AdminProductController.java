package com.example.springlesson.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springlesson.dto.AdminProductDto;
import com.example.springlesson.entity.Product;
import com.example.springlesson.form.AdminProductForm;
import com.example.springlesson.service.AdminProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {
    
    private final AdminProductService adminProductService;
    
    /**
     * 管理者用商品管理画面の表示
     */
    @GetMapping
    public String showAdminProductPage(Model model) {
        List<AdminProductDto> products = adminProductService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("adminProductForm", new AdminProductForm());
        
        return "admin/product-management";
    }
    
    /**
     * 商品一覧のみ取得（AJAX用）
     */
    @GetMapping("/list")
    @ResponseBody
    public List<AdminProductDto> getProductList() {
        return adminProductService.getAllProducts();
    }
    
    /**
     * 商品詳細取得（AJAX用）
     */
    @GetMapping("/{productId}")
    @ResponseBody
    public AdminProductDto getProduct(@PathVariable Long productId) {
        return adminProductService.getProductById(productId);
    }
    
    /**
     * 商品登録処理
     */
    @PostMapping("/register")
    public String registerProduct(
            @Validated @ModelAttribute AdminProductForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        if (bindingResult.hasErrors()) {
            List<AdminProductDto> products = adminProductService.getAllProducts();
            model.addAttribute("products", products);
            return "admin/product-management";
        }
        
        try {
            Product product = adminProductService.createProduct(form);
            redirectAttributes.addFlashAttribute("successMessage", 
                "商品を登録しました: " + product.getProductName());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "商品の登録に失敗しました: " + e.getMessage());
        }
        
        return "redirect:/admin/products";
    }
    
    /**
     * 商品更新処理
     */
    @PostMapping("/update/{productId}")
    public String updateProduct(
            @PathVariable Long productId,
            @Validated @ModelAttribute AdminProductForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        if (bindingResult.hasErrors()) {
            List<AdminProductDto> products = adminProductService.getAllProducts();
            model.addAttribute("products", products);
            return "admin/product-management";
        }
        
        try {
            Product product = adminProductService.updateProduct(productId, form);
            redirectAttributes.addFlashAttribute("successMessage", 
                "商品を更新しました: " + product.getProductName());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "商品の更新に失敗しました: " + e.getMessage());
        }
        
        return "redirect:/admin/products";
    }
    
    /**
     * 商品削除処理
     */
    @PostMapping("/delete/{productId}")
    public String deleteProduct(
            @PathVariable Long productId,
            RedirectAttributes redirectAttributes) {
        
        try {
            adminProductService.deleteProduct(productId);
            redirectAttributes.addFlashAttribute("successMessage", 
                "商品を削除しました");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "商品の削除に失敗しました: " + e.getMessage());
        }
        
        return "redirect:/admin/products";
    }
    
    /**
     * 商品削除処理（AJAX用）
     */
    @DeleteMapping("/{productId}")
    @ResponseBody
    public String deleteProductAjax(@PathVariable Long productId) {
        try {
            adminProductService.deleteProduct(productId);
            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }
}