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
import com.example.springlesson.service.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {

  private final ProductService productService;

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
   * カートに商品を追加（GETメソッド）
   */
  @GetMapping("/add")
  public String add(HttpSession session, Model model,
      @RequestParam(name = "id") Integer productId,
      @RequestParam(required = false) Integer count) {

    @SuppressWarnings("unchecked")
    List<Item> cart = (List<Item>) session.getAttribute("cart");
    if (cart == null) {
      cart = new ArrayList<>();
    }

    ProductDTO productDTO = productService.findProductById(productId).orElse(null);

    if (productDTO != null) {
      final Long prodIdLong = productId.longValue();
      Optional<Item> existingItem = cart.stream()
          .filter(item -> item.getProduct().getProductId().equals(prodIdLong))
          .findFirst();

      int addCount = (count != null && count > 0) ? count : 1;

      if (existingItem.isPresent()) {
        existingItem.get().setCount(existingItem.get().getCount() + addCount);
      } else {
        cart.add(new Item(productDTO, addCount, false));
      }
    }

    session.setAttribute("cart", cart);

    return "redirect:/product/products/" + productId;
  }

  /**
   * カートに商品を追加（POSTメソッド）
   */
  @PostMapping("/add")
  public String addPost(HttpSession session, Model model,
      @RequestParam(name = "id") Integer productId,
      @RequestParam(required = false) Integer count) {

    @SuppressWarnings("unchecked")
    List<Item> cart = (List<Item>) session.getAttribute("cart");
    if (cart == null) {
      cart = new ArrayList<>();
    }

    ProductDTO productDTO = productService.findProductById(productId).orElse(null);

    if (productDTO != null) {
      final Long prodIdLong = productId.longValue();
      Optional<Item> existingItem = cart.stream()
          .filter(item -> item.getProduct().getProductId().equals(prodIdLong))
          .findFirst();

      int addCount = (count != null && count > 0) ? count : 1;

      if (existingItem.isPresent()) {
        existingItem.get().setCount(existingItem.get().getCount() + addCount);
      } else {
        cart.add(new Item(productDTO, addCount, false));
      }
    }

    session.setAttribute("cart", cart);

    return "redirect:/cart";
  }

  /**
   * カートから商品を削除
   */
  @GetMapping("/remove")
  public String remove(HttpSession session, Model model, @RequestParam(name = "id") Integer productId) {
    @SuppressWarnings("unchecked")
    List<Item> cart = (List<Item>) session.getAttribute("cart");
    if (cart != null) {
      final Long prodIdLong = productId.longValue();
      cart.removeIf(item -> item.getProduct().getProductId().equals(prodIdLong));
    }
    session.setAttribute("cart", cart);
    return "redirect:/cart";
  }

  /**
   * 数量・ギフト設定の更新
   */
  @PostMapping("/update")
  public String update(HttpSession session, Model model,
      @RequestParam List<Integer> productId,
      @RequestParam List<Integer> quantity,
      @RequestParam(required = false) List<Integer> giftProductIds) {

    @SuppressWarnings("unchecked")
    List<Item> cart = (List<Item>) session.getAttribute("cart");

    if (cart != null && productId.size() == quantity.size()) {
      for (int i = 0; i < productId.size(); i++) {
        final Integer currentId = productId.get(i);
        final Integer newQty = quantity.get(i);
        final boolean isGift = giftProductIds != null && giftProductIds.contains(currentId);
        final Long currentIdLong = currentId.longValue();

        cart.stream()
            .filter(item -> item.getProduct().getProductId().equals(currentIdLong))
            .findFirst()
            .ifPresent(item -> {
              if (newQty >= 1)
                item.setCount(newQty);
              item.setGift(isGift);
            });
      }
    }
    session.setAttribute("cart", cart);
    return "redirect:/cart";
  }

  /**
   * カートを空にする
   */
  @GetMapping("/clear")
  public String clear(HttpSession session) {
    session.removeAttribute("cart");
    return "redirect:/cart";
  }
}
