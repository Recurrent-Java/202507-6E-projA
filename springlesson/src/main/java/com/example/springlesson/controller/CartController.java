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

    /**
     * 合計金額を計算し、Modelに追加する共通メソッド
     */
    private void prepareCartView(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        long totalPrice = 0;
        
        // カートがnullの場合は空のリストとして扱う（Thymeleafのエラー防止）
        if (cart == null) {
            cart = new ArrayList<>();
        } else {
            // カート内商品の小計を計算
            totalPrice = cart.stream()
                .mapToLong(item -> (long) item.getProduct().getPrice() * item.getCount())
                .sum();
        }
        
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
    }

    /**
     * 1. カートページの表示
     */
    @GetMapping({ "", "/" })
    public String cart(HttpSession session, Model model) {
        // テストデータは削除しました。今後は商品一覧からの追加、
        // または保存したテストコードをSlackから戻して使用してください。
        prepareCartView(session, model);
        return "cart/cart"; 
    }

    /**
     * 2. カートに商品を追加
     */
    @GetMapping("/add")
    public String add(HttpSession session, Model model,
            @RequestParam(name = "id") Long productId, 
            @RequestParam(required = false) Integer count) {

        @SuppressWarnings("unchecked")
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // 商品マスター（商品一覧）から該当商品を探す
        @SuppressWarnings("unchecked")
        List<ProductDTO> productList = (List<ProductDTO>) session.getAttribute("list");
        
        ProductDTO productDTO = null;
        if (productList != null) {
            productDTO = productList.stream()
                .filter(pro -> pro.getProductId().equals(productId))
                .findFirst().orElse(null);
        }

        if (productDTO != null) {
            Optional<Item> existingItem = cart.stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst();

            int addCount = (count != null && count > 0) ? count : 1;
            
            if (existingItem.isPresent()) {
                // 既にカートにある場合は数量アップ
                existingItem.get().setCount(existingItem.get().getCount() + addCount);
            } else {
                // 新規追加（Itemクラスの引数3つ：product, count, isGift）
                cart.add(new Item(productDTO, addCount, false));
            }
        }

        session.setAttribute("cart", cart);
        prepareCartView(session, model);
        return "cart/cart";
    }

    /**
     * 3. カートから商品を削除
     */
    @GetMapping("/remove")
    public String remove(HttpSession session, Model model, @RequestParam(name = "id") Long productId) {
        @SuppressWarnings("unchecked")
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        
        if (cart != null) {
            cart.removeIf(item -> item.getProduct().getProductId().equals(productId));
        }
        
        session.setAttribute("cart", cart);
        prepareCartView(session, model);
        return "cart/cart";
    }

    /**
     * 4. 数量・ギフト設定の一括更新
     */
    @PostMapping("/update")
    public String update(HttpSession session, Model model,
            @RequestParam List<Long> productId,
            @RequestParam List<Integer> quantity,
            @RequestParam(required = false) List<Boolean> isGift) { 

        @SuppressWarnings("unchecked")
        List<Item> cart = (List<Item>) session.getAttribute("cart");

        if (cart != null && productId.size() == quantity.size()) {
            for (int i = 0; i < productId.size(); i++) {
                final Long currentId = productId.get(i);
                final Integer newQty = quantity.get(i);

                Optional<Item> itemOpt = cart.stream()
                    .filter(item -> item.getProduct().getProductId().equals(currentId))
                    .findFirst();

                if (itemOpt.isPresent()) {
                    Item item = itemOpt.get();
                    if (newQty != null && newQty >= 1) {
                        item.setCount(newQty);
                    } else {
                        // 0以下の数量が入力された場合はカートから除外
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