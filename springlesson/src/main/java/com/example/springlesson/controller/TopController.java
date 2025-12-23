package com.example.springlesson.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springlesson.dto.ProductDTO;
import com.example.springlesson.entity.Category; // 追加
import com.example.springlesson.service.CategoryService; // 追加
import com.example.springlesson.service.ProductService;

@Controller
public class TopController {

  private final ProductService productService;
  private final CategoryService categoryService; // ★1. 追加

  // ★2. コンストラクタに CategoryService を追加
  public TopController(ProductService productService, CategoryService categoryService) {
    this.productService = productService;
    this.categoryService = categoryService;
  }

  /**
   * TOPページ表示
   */
  @GetMapping("/")
  public String index(
      @RequestParam(name = "catId", required = false) Integer catId,
      @RequestParam(name = "keyword", required = false) String keyword, // ついでに追加しておくと便利
      Model model) {

    // 商品リストの取得
    List<ProductDTO> list = getFilteredProducts(keyword, catId);
    model.addAttribute("products", list);

    // ★3. カテゴリー一覧をDBから取得してHTMLに渡す
    List<Category> categoryList = categoryService.findAll();
    model.addAttribute("categoryList", categoryList);

    // 現在選択中のIDを渡す（ボタンのactive切り替え用）
    model.addAttribute("catId", catId);

    return "index";
  }

  /**
   * 非同期（JavaScript）用（もしJSを使わなくなった場合はこれ自体不要になります）
   */
  @GetMapping("/filter")
  public String filter(
      @RequestParam(name = "catId", required = false) Integer catId,
      Model model) {

    List<ProductDTO> productList = getFilteredProducts(null, catId);
    model.addAttribute("products", productList);

    return "index :: product-list-fragment";
  }

  /**
   * 絞り込みロジックの共通化
   */
  private List<ProductDTO> getFilteredProducts(String keyword, Integer catId) {
    List<ProductDTO> list;

    if (keyword != null && !keyword.isBlank()) {
      list = productService.findByNameContaining(keyword);
    } else if (catId != null && catId != 0) {
      list = productService.findByCategory(catId);
    } else {
      list = productService.findAllProduct();
    }

    if (list == null) {
      return java.util.Collections.emptyList();
    }
    list.removeIf(java.util.Objects::isNull);

    return list;
  }
}