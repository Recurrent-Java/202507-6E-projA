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
  @GetMapping({ "", "/" })
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
  String find(@RequestParam(required = false, defaultValue = "") String keyword, Model model) {
    // 商品名で商品情報検索 
    List<ProductDTO> list = productService.findByNameContaining(keyword);
    model.addAttribute("keyword", keyword);
    // 画面表示用 
    model.addAttribute("list", list);
    // 商品ページへ 
    return "product/product";
  }
  
//商品詳細ページ（メロンパン）
  @GetMapping("/detail")
  public String productDetail() {
      return "product/product-melonpan";
  }
  
//商品詳細ページ（ピーチタルト）
  @GetMapping("/peachtart")
  public String productPeach() {
      return "product/product-peachtart";
  }
 
//商品詳細ページ（アニマルクッキー缶）
  @GetMapping("/animalcookie")
  public String productanimalcookie() {
      return "product/product-animalcookie";
  }

//商品詳細ページ（アニバーサリーケーキ）
  @GetMapping("/anniversarycake")
  public String productanniversarycake() {
      return "product/product-anniversarycake";
  }
  
//商品詳細ページ（アップルパイ）
  @GetMapping("/applepie")
  public String productapplepie() {
      return "product/product-applepie";
  }

//商品詳細ページ（焼きドーナツ）
  @GetMapping("/bakeddonut")
  public String productbakeddonut() {
      return "product/product-bakeddonut";
  }

//商品詳細ページ（ココアエクレア）
  @GetMapping("/cocoaeclair")
  public String productcocoaeclair() {
      return "product/product-cocoaeclair";
  }
 
//商品詳細ページ（チョコケーキ）
  @GetMapping("/chocolatecake")
  public String productchocolatecake() {
      return "product/product-chocolatecake";
  }
 
//商品詳細ページ（ココナッツマカロン）
  @GetMapping("/coconutmacaron")
  public String productcoconutmacaron() {
      return "product/product-coconutmacaron";
  }

//商品詳細ページ（クッキー缶）
  @GetMapping("/cookie")
  public String productcookie() {
      return "product/product-cookie";
  }
 
//商品詳細ページ（ムーランルージュ）
  @GetMapping("/eggfreerouge")
  public String producteggfreerouge() {
      return "product/product-eggfreerouge";
  }
 
//商品詳細ページ（レモンケーキ）
  @GetMapping("/lemoncake")
  public String productlemoncake() {
      return "product/product-lemoncake";
  }
}
