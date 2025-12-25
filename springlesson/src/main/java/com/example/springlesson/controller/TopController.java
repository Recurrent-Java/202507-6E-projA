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
      @RequestParam(name = "catIds", required = false) List<Integer> catIds,
      @RequestParam(name = "keyword", required = false) String keyword,
      Model model) {

    // 商品リストの取得
    List<ProductDTO> list = getFilteredProducts(keyword, catIds);
    model.addAttribute("products", list);

    // カテゴリー一覧をDBから取得してHTMLに渡す
    List<Category> categoryList = categoryService.findAll();
    model.addAttribute("categoryList", categoryList);

    // 選択中のカテゴリーIDリストを渡す（チェックボックスの状態用）
    if (catIds != null && !catIds.isEmpty() && !catIds.contains(0)) {
      model.addAttribute("selectedCatIds", catIds);
    } else {
      model.addAttribute("selectedCatIds", java.util.Collections.emptyList());
    }

    return "index";
  }

  /**
   * 非同期（JavaScript）用（もしJSを使わなくなった場合はこれ自体不要になります）
   */
  @GetMapping("/filter")
  public String filter(
      @RequestParam(name = "catIds", required = false) List<Integer> catIds,
      Model model) {

    List<ProductDTO> productList = getFilteredProducts(null, catIds);
    model.addAttribute("products", productList);

    return "index :: product-list-fragment";
  }

  /**
   * 絞り込みロジックの共通化
   */
  private List<ProductDTO> getFilteredProducts(String keyword, List<Integer> catIds) {
    List<ProductDTO> list;

    if (keyword != null && !keyword.isBlank()) {
      list = productService.findByNameContaining(keyword);
    } else if (catIds != null && !catIds.isEmpty() && !catIds.contains(0)) {
      // 複数カテゴリーでフィルタリング
      list = productService.findByCategories(catIds);
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