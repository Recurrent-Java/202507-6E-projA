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
import com.example.springlesson.service.ProductService; // 追加

@Controller
@RequestMapping("/cart")
public class CartController {

  private final ProductService productService; // ★追加

  // ★コンストラクタでProductServiceを注入するように変更
  public CartController(ProductService productService) {
    this.productService = productService;
  }

  private void prepareCartView(HttpSession session, Model model) {
    @SuppressWarnings("unchecked")
    List<Item> cart = (List<Item>) session.getAttribute("cart");
    long totalPrice = 0;

    if (cart == null) {
      cart = new ArrayList<>();
    } else {
      totalPrice = cart.stream()
          .mapToLong(item -> (long) item.getProduct().getPrice() * item.getCount())
          .sum();
    }

    model.addAttribute("cart", cart);
    model.addAttribute("totalPrice", totalPrice);
  }

  @GetMapping({ "", "/" })
  public String cart(HttpSession session, Model model) {
    prepareCartView(session, model);
    return "cart/cart";
  }

  /**
   * カートに商品を追加
   */
  @GetMapping("/add")
  public String add(HttpSession session, Model model,
      @RequestParam(name = "id") Integer productId, // ★LongからIntegerに変更（Serviceに合わせる）
      @RequestParam(required = false) Integer count) {

    @SuppressWarnings("unchecked")
    List<Item> cart = (List<Item>) session.getAttribute("cart");
    if (cart == null) {
      cart = new ArrayList<>();
    }

    // ★セッションの"list"から探すのをやめ、Service経由でDBから直接取得する
    ProductDTO productDTO = productService.findProductById(productId).orElse(null);

    if (productDTO != null) {
      Optional<Item> existingItem = cart.stream()
          .filter(item -> item.getProduct().getProductId().longValue() == productId.longValue())
          .findFirst();

      int addCount = (count != null && count > 0) ? count : 1;

      if (existingItem.isPresent()) {
        existingItem.get().setCount(existingItem.get().getCount() + addCount);
      } else {
        // Itemクラスのコンストラクタに合わせて引数を調整してください
        cart.add(new Item(productDTO, addCount, false));
      }
    }

    session.setAttribute("cart", cart);

    // 追加したらそのままカート画面へ移動する（入ったことがすぐ確認できる）
    return "redirect:/product/products/" + productId;
  }

  @GetMapping("/remove")
  public String remove(HttpSession session, Model model, @RequestParam(name = "id") Integer productId) {
    @SuppressWarnings("unchecked")
    List<Item> cart = (List<Item>) session.getAttribute("cart");
    if (cart != null) {
      cart.removeIf(item -> item.getProduct().getProductId().equals(productId));
    }
    session.setAttribute("cart", cart);
    return "redirect:/cart";
  }

  @PostMapping("/update")
  public String update(HttpSession session, Model model,
      @RequestParam List<Integer> productId,
      @RequestParam List<Integer> quantity) {

    @SuppressWarnings("unchecked")
    List<Item> cart = (List<Item>) session.getAttribute("cart");

    if (cart != null && productId.size() == quantity.size()) {
      for (int i = 0; i < productId.size(); i++) {
        final Integer currentId = productId.get(i);
        final Integer newQty = quantity.get(i);

        cart.stream()
            .filter(item -> item.getProduct().getProductId().equals(currentId))
            .findFirst()
            .ifPresent(item -> {
              if (newQty >= 1)
                item.setCount(newQty);
            });
      }
    }
    session.setAttribute("cart", cart);
    return "redirect:/cart";
  }
}