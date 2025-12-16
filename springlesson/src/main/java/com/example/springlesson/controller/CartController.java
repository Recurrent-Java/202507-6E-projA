package com.example.springlesson.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springlesson.dto.ProductDTO;
import com.example.springlesson.form.Item;

@Controller
@RequestMapping("/cart")
public class CartController {

    // 合計金額を計算し、Modelに追加するプライベートメソッド
    private void prepareCartView(HttpSession session, Model model) {
        // セッションスコープよりカート情報取得
        List<Item> cart = (List<Item>)session.getAttribute("cart");
        
        long totalPrice = 0; // 商品合計金額 (税込) を格納
        if (cart != null) {
            // Item.getProduct().getPrice() は税込単価であることを想定
            totalPrice = cart.stream()
                             .mapToLong(item -> (long)item.getProduct().getPrice() * item.getCount())
                             .sum();
        }

        // 画面表示用
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice); // 合計金額をModelに追加
    }

    // 1. カートページの表示
    @GetMapping({"","/"})
    public String cart(HttpSession session, Model model) {
        prepareCartView(session, model); 
        return "cart/cart";
    }

    // 2. カートに追加
    @GetMapping("/add")
    public String add(HttpSession session, Model model, 
                      @RequestParam Integer id, @RequestParam(required=false) Integer count) {
        
        List<Item> cart = (List<Item>)session.getAttribute("cart");
        int addCount = (count != null && count > 0) ? count : 1; // countが有効でなければ1とする
        
        if (cart == null) {
            cart = new ArrayList<>();
        }
        
        // 商品情報の取得（既存ロジック）
        List<ProductDTO> productList = (List<ProductDTO>)session.getAttribute("list");
        ProductDTO productDTO = productList.stream()
                                        .filter(pro -> pro.getId().equals(id))
                                        .findFirst().orElse(null);
        
        if (productDTO != null) {
            Optional<Item> existingItem = cart.stream()
                                            .filter(item -> item.getProduct().getId().equals(id))
                                            .findFirst();
            
            if(existingItem.isPresent()) {
                // 既存商品なら数量を加算
                existingItem.get().setCount(existingItem.get().getCount() + addCount);
            } else {
                // 新規商品なら追加
                Item item = new Item(productDTO, addCount); 
                cart.add(item);
            }
        }
        
        session.setAttribute("cart", cart);
        prepareCartView(session, model); 
        
        return "cart/cart";
    }

    // 3. カートから完全削除
    @GetMapping("/remove")
    public String remove(HttpSession session, Model model, @RequestParam Integer id) {
        List<Item> cart = (List<Item>)session.getAttribute("cart");
        
        if (cart != null) {
            // 該当の商品IDを持つItemを完全に削除
            cart.removeIf(item -> item.getProduct().getId().equals(id));
        }
        
        session.setAttribute("cart", cart);
        prepareCartView(session, model); 
        
        return "cart/cart";
    }

    // 4. 数量更新処理
    @PostMapping("/update")
    public String update(HttpSession session, Model model, 
                         @RequestParam List<Integer> productId, 
                         @RequestParam List<Integer> quantity) {
        
        List<Item> cart = (List<Item>)session.getAttribute("cart");

        if (cart != null && productId.size() == quantity.size()) {
            for (int i = 0; i < productId.size(); i++) {
                final Integer currentProductId = productId.get(i);
                final Integer newQuantity = quantity.get(i);

                Optional<Item> resultItem = cart.stream()
                    .filter(item -> item.getProduct().getId().equals(currentProductId))
                    .findFirst();

                if (resultItem.isPresent()) {
                    Item item = resultItem.get();
                    if (newQuantity != null && newQuantity >= 1) {
                        // 数量を更新
                        item.setCount(newQuantity);
                    } else {
                        // 数量が0以下ならカートから削除
                        cart.remove(item); 
                    }
                }
            }
        }
        
        session.setAttribute("cart", cart);
        prepareCartView(session, model);
        
        return "cart/cart";
    }
}