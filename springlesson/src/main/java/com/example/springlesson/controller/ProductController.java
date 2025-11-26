package com.example.springlesson.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springlesson.dto.ProductDTO;
import com.example.springlesson.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
  private final ProductService productService;
  
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  // 商品ページの表示
  @GetMapping({"","/"})
  public String product(HttpSession session, Model model) {
    // 全商品情報の取得
    List<ProductDTO> list = productService.findAllProduct();
    // セッションスコープへセット
    session.setAttribute("list", list);
    // 画面表示に利用する場合は必ずmodel.addAttributeが必要
    // ThymeleafはModelしか受け取らないため
    model.addAttribute("list", list);
    
    // 商品ページへ
    return "product/product";
  }
  // キーワード検索
  @PostMapping("/find")
  String find(@RequestParam(required = false, defaultValue="") String keyword ,Model model) {
    // 商品名で商品情報検索
    List<ProductDTO> list = productService.findByNameContaining(keyword);
    model.addAttribute("keyword", keyword);
    // 画面表示用
    model.addAttribute("list", list);
    // 商品ページへ
    return "product/product";
  }
}
