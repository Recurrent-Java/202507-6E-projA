package com.example.springlesson.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springlesson.form.Item;
import com.example.springlesson.form.PurchaseForm;
import com.example.springlesson.service.PurchaseService;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {
  private final PurchaseService purchaseService;

  public PurchaseController(PurchaseService purchaseService) {
    this.purchaseService = purchaseService;
  }
  // 購入ページの表示
  @GetMapping({"","/"})
  public String purchase(HttpSession session, Model model) {
    // セッションスコープよりカートを取得
    List<Item> cart = (List<Item>)session.getAttribute("cart");
    // 画面表示用
    model.addAttribute("cart", cart);
    // バリデーション用(バリデーションを行うformを最初に開く場合には必要
    model.addAttribute("purchaseForm", new PurchaseForm());
    // 購入ページへ
    return "purchase/purchase-in";
  }
  // 購入ページ
  @PostMapping("/regist")
  public String regist(
      @Valid @ModelAttribute("purchaseForm") PurchaseForm form,
      BindingResult bindingResult,
      HttpSession session, Model model) {
    // 入力エラーのチェック
    if(bindingResult.hasErrors()) {
      // 入力エラーあり
      // セッションスコープよりカートを取得
      List<Item> cart = (List<Item>)session.getAttribute("cart");
      // 画面表示用
      model.addAttribute("cart", cart);
      return "purchase/purchase-in";
    }
    /** 購入処理 */
    try {
      // セッションスコープよりカートを取得
      List<Item> cart = (List<Item>)session.getAttribute("cart");
      if(cart == null || cart.isEmpty()) {
        throw new Exception("購入する商品が選択されていません。");
      }
      // 購入情報登録
      purchaseService.save(cart, form.getName(), form.getAddress());
      // セッションスコープよりカート情報削除
      session.removeAttribute("cart");
      // 購入完了画面へ
      return "purchase/purchase-out";
    }catch(Exception e) {
      model.addAttribute("errMsg", e.getMessage());
      return "error/error";
    }
  }
}
