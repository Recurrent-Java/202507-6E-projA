package com.example.springlesson.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springlesson.dto.ProductDTO;
import com.example.springlesson.form.Item;

@Controller
@RequestMapping("/cart")
public class CartController {
  // カートページの表示
  @GetMapping({"","/"})
  public String cart(HttpSession session, Model model) {
    // セッションスコープよりカート情報取得
    List<Item> cart = (List<Item>)session.getAttribute("cart");
    // 画面表示用
    model.addAttribute("cart", cart);
    
    // カートページへ
    return "cart/cart";
  }
  // カートに追加
  @GetMapping("/add")
  public String add(HttpSession session, Model model, @RequestParam Integer id, @RequestParam(required=false) Integer count) {
    // セッションスコープよりカート情報取得
    List<Item> cart = (List<Item>)session.getAttribute("cart");
    // 追加個数のチェック
    if(count == null) {
      // 画面表示用
      model.addAttribute("cart", cart);    
      // カートページへ
      return "cart/cart";
    }
    // カートのなかみをチェック
    // 最初のカート追加
    if(cart == null) {
      // カートを生成
      cart = new ArrayList<>();
      // セッションスコープより商品情報取得
      List<ProductDTO> productList = 
          (List<ProductDTO>)session.getAttribute("list");
      // 追加する商品情報を取得
      ProductDTO productDTO = null;
      for(ProductDTO pro: productList) {
        if(pro.getId().equals(id)) {
          productDTO = new ProductDTO(pro.getId(), pro.getName(),pro.getPrice());
        }
      }
      // Itemインスタンスを生成
      Item item = new Item(productDTO,1);
      // カートに追加
      cart.add(item);
    }
    // 2回目以降のカートの追加
    else {
      // カートの中のProductオブジェクトのidと追加しようとしているidが等しいかを検索
      boolean exists = cart.stream()
          .anyMatch(item -> item.getProduct().getId().equals(id));
      // 既にカートに存在する商品の追加
      if(exists) {
        cart.stream()
        .filter(item -> item.getProduct().getId().equals(id))
        .findFirst()
        .ifPresent(item -> item.setCount(item.getCount() + 1));
      }
      // 新規に商品を追加する
      else {
        // セッションスコープより商品情報取得
        List<ProductDTO> productList = 
            (List<ProductDTO>)session.getAttribute("list");
        // 追加する商品情報を取得
        ProductDTO productDTO = null;
        for(ProductDTO pro: productList) {
          if(pro.getId() == id) {
            productDTO = new ProductDTO(pro.getId(), pro.getName(),pro.getPrice());
          }
        }
        // Itemインスタンスを生成
        Item item = new Item(productDTO,1);
        // カートに追加
        cart.add(item);             
      }
    }
    // セッションスコープにカートをセット
    session.setAttribute("cart", cart); 
    // 画面表示用
    model.addAttribute("cart", cart);    
    // カートページへ
    return "cart/cart";
  }
  // カートから削除
  @GetMapping("/remove")
  public String remove(HttpSession session, Model model, @RequestParam Integer id) {
    // カートをセッションスコープより取得
    List<Item> cart = (List<Item>)session.getAttribute("cart");
    // 該当の商品を検索
    Optional<Item> resultItem = 
        cart.stream()
        .filter(it -> it.getProduct().getId().equals(id))
        .findFirst();
    // 商品の削除処理
    if(resultItem.isPresent()) {
      Item item = resultItem.get();
      // 個数が2以上の場合、商品の購入個数を-1
      if(item.getCount() >= 2) {
        item.setCount(item.getCount()-1);
      }
      // 個数が2未満の場合、商品削除
      else {
        cart.remove(item);
      }
    }
    // セッションスコープにカートをセット
    session.setAttribute("cart", cart);
    // 画面表示用
    model.addAttribute("cart", cart);    
    // カートページへ
    return "cart/cart";
  }
 
}
