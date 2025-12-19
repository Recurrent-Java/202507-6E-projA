package com.example.springlesson.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springlesson.dto.ProductDTO;
import com.example.springlesson.service.ProductService;

@Controller
public class TopController {
  private final ProductService productService;

  public TopController(ProductService productService) {
    this.productService = productService;
  }

  /**
   * TOPページ（商品一覧）を表示する
   */
  @GetMapping("/")
  public String index(
      @RequestParam(name = "keyword", required = false) String keyword,
      @RequestParam(name = "category", required = false) String category,
      Model model) {

    List<ProductDTO> productList;

    if (keyword != null && !keyword.isEmpty()) {
      productList = productService.findByNameContaining(keyword);
    } else {
      // カテゴリ検索ロジックも追加可能だが、今回は全件取得/キーワード検索のみに留める
      productList = productService.findAllProduct();
    }

    model.addAttribute("products", productList);

    // src/main/resources/templates/index.html (または /index) を返す
    return "index";
  }

}