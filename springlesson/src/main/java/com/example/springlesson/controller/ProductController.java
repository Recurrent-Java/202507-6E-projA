package com.example.springlesson.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  @GetMapping("/products/1")
  public String productDetail() {
    return "product/product-melonpan";
  }

  //商品詳細ページ（ピーチタルト）
  @GetMapping("/products/3")
  public String productPeach() {
    return "product/product-peachtart";
  }

  //商品詳細ページ（アニマルクッキー缶）
  @GetMapping("/products/9")
  public String productanimalcookie() {
    return "product/product-animalcookie";
  }

  //商品詳細ページ（アニバーサリーケーキ）
  @GetMapping("/products/11")
  public String productanniversarycake() {
    return "product/product-anniversarycake";
  }

  //商品詳細ページ（アップルパイ）
  @GetMapping("/products/2")
  public String productapplepie() {
    return "product/product-applepie";
  }

  //商品詳細ページ（焼きドーナツ）
  @GetMapping("/products/6")
  public String productbakeddonut() {
    return "product/product-bakeddonuts";
  }

  //商品詳細ページ（ココアエクレア）
  @GetMapping("/products/4")
  public String productcocoaeclair() {
    return "product/product-cacaoeclair";
  }

  //商品詳細ページ（チョコケーキ）
  @GetMapping("/products/10")
  public String productchocolatecake() {
    return "product/product-chocolatecake";
  }

  //商品詳細ページ（ココナッツマカロン）
  @GetMapping("/products/8")
  public String productcoconutmacaron() {
    return "product/product-coconutmacaroons";
  }

  //商品詳細ページ（クッキー缶）
  @GetMapping("/products/")
  public String productcookie() {
    return "product/product-cookie";
  }

  //商品詳細ページ（ムーランルージュ）
  @GetMapping("/products/5")
  public String producteggfreerouge() {
    return "product/product-eggfreerouge";
  }

  //商品詳細ページ（レモンケーキ）
  @GetMapping("/products/7")
  public String productlemoncake() {
    return "product/product-lemoncake";
  }
  
  //商品詳細ページのID12～追加商品分
  @GetMapping("/products/{id}")
public String tempProductDetail(@PathVariable("id") Integer id, Model model) {
    // IDをModelに渡しておけば、HTML側で「商品ID: 19 の詳細」と表示できます
    model.addAttribute("id", id);
    // 共通の「準備中」または「汎用詳細ページ」を1枚作って返す
    return "product/product-generic-detail";
  }
}