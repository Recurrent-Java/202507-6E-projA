package com.example.springlesson.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springlesson.form.Item;
import com.example.springlesson.security.CustomerDetailsImpl;
import com.example.springlesson.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * ログインユーザーのIDを取得
     * @param userDetails ログイン情報
     * @return ユーザーID（未ログイン時はnull）
     */
    private Long getUserId(CustomerDetailsImpl userDetails) {
        if (userDetails != null && userDetails.getMember() != null) {
            return userDetails.getMember().getMemberId();
        }
        return null;
    }

    /**
     * カートの内容をModelにセット
     */
    private void prepareCartView(CustomerDetailsImpl userDetails, HttpSession session, Model model) {
        Long userId = getUserId(userDetails);
        String sessionId = session.getId();

        List<Item> cart = cartService.getCartItems(userId, sessionId);
        long totalPrice = cartService.calculateTotalPrice(cart);

        // セッションにも保持（他の画面で使用する可能性があるため）
        session.setAttribute("cart", cart);
        
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
    }

    /**
     * 1. カートページの表示
     */
    @GetMapping({ "", "/" })
    public String cart(@AuthenticationPrincipal CustomerDetailsImpl userDetails,
                       HttpSession session, Model model) {
        prepareCartView(userDetails, session, model);
        return "cart/cart";
    }

    /**
     * 2. カートに商品を追加
     */
    @GetMapping("/add")
    public String add(@AuthenticationPrincipal CustomerDetailsImpl userDetails,
                      HttpSession session, Model model,
                      @RequestParam(name = "id") Long productId,
                      @RequestParam(required = false, defaultValue = "1") Integer count) {

        Long userId = getUserId(userDetails);
        String sessionId = session.getId();

        // DBに商品を追加
        cartService.addToCart(userId, sessionId, productId, count);

        // カートの内容を更新
        prepareCartView(userDetails, session, model);
        
        return "redirect:/product/products/" + productId;
    }
    
    /**
     * 2-2. カートに商品を追加（POSTメソッド対応）
     */
    @PostMapping("/add")
    public String addPost(@AuthenticationPrincipal CustomerDetailsImpl userDetails,
                          HttpSession session, Model model,
                          @RequestParam(name = "id") Long productId,
                          @RequestParam(required = false, defaultValue = "1") Integer count) {

        Long userId = getUserId(userDetails);
        String sessionId = session.getId();

        // DBに商品を追加
        cartService.addToCart(userId, sessionId, productId, count);

        // カートの内容を更新
        prepareCartView(userDetails, session, model);
        
        // カートページにリダイレクト
        return "redirect:/cart";
    }

    /**
     * 3. カートから商品を削除
     */
    @GetMapping("/remove")
    public String remove(@AuthenticationPrincipal CustomerDetailsImpl userDetails,
                         HttpSession session, Model model,
                         @RequestParam(name = "id") Long productId) {

        Long userId = getUserId(userDetails);
        String sessionId = session.getId();

        // DBから商品を削除
        cartService.removeFromCart(userId, sessionId, productId);

        // カートの内容を更新
        prepareCartView(userDetails, session, model);
        
        return "cart/cart";
    }

    /**
     * 4. 数量・ギフト設定の一括更新
     */
    @PostMapping("/update")
    public String update(@AuthenticationPrincipal CustomerDetailsImpl userDetails,
                         HttpSession session, Model model,
                         @RequestParam List<Long> productId,
                         @RequestParam List<Integer> quantity,
                         @RequestParam(required = false) List<Long> giftProductIds) {

        Long userId = getUserId(userDetails);
        String sessionId = session.getId();

        // 各商品の数量とギフト設定を更新
        for (int i = 0; i < productId.size(); i++) {
            Long prodId = productId.get(i);
            Integer qty = quantity.get(i);
            
            // チェックボックスがチェックされた商品IDのリストでギフト判定
            boolean isGift = giftProductIds != null && giftProductIds.contains(prodId);
            
            cartService.updateCartItem(userId, sessionId, prodId, qty, isGift);
        }

        // カートの内容を更新
        prepareCartView(userDetails, session, model);
        
        return "cart/cart";
    }
    
    /**
     * 5. カートを空にする
     */
    @GetMapping("/clear")
    public String clear(@AuthenticationPrincipal CustomerDetailsImpl userDetails,
                        HttpSession session, Model model) {
        
        Long userId = getUserId(userDetails);
        String sessionId = session.getId();

        // カートを空にする
        cartService.clearCart(userId, sessionId);

        // カートの内容を更新
        prepareCartView(userDetails, session, model);
        
        return "redirect:/cart";
    }
}
