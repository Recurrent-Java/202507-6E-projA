package com.example.springlesson.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springlesson.dto.ProductDTO;
import com.example.springlesson.entity.Cart;
import com.example.springlesson.entity.Product;
import com.example.springlesson.form.Item;
import com.example.springlesson.mapper.ProductMapper;
import com.example.springlesson.repository.CartRepository;
import com.example.springlesson.repository.ProductRepository;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, ProductMapper productMapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * カートの内容を取得（Itemリストに変換）
     * @param userId ログインユーザーのID（null可）
     * @param sessionId セッションID
     * @return カート内のアイテムリスト
     */
    public List<Item> getCartItems(Long userId, String sessionId) {
        List<Cart> cartList;
        
        if (userId != null) {
            cartList = cartRepository.findByUserId(userId);
        } else {
            cartList = cartRepository.findBySessionId(sessionId);
        }

        List<Item> items = new ArrayList<>();
        for (Cart cart : cartList) {
            Optional<Product> productOpt = productRepository.findById(cart.getProdId().intValue());
            if (productOpt.isPresent()) {
                ProductDTO dto = productMapper.toDto(productOpt.get());
                // ギフト設定はオプションIDがnullでないかで判定
                boolean isGift = cart.getOptionId() != null;
                items.add(new Item(dto, cart.getQuantity(), isGift));
            }
        }
        return items;
    }

    /**
     * カートに商品を追加
     * @param userId ログインユーザーのID（null可）
     * @param sessionId セッションID
     * @param productId 商品ID
     * @param count 数量
     */
    public void addToCart(Long userId, String sessionId, Long productId, int count) {
        Optional<Cart> existingCart;
        
        if (userId != null) {
            existingCart = cartRepository.findByUserIdAndProdId(userId, productId);
        } else {
            existingCart = cartRepository.findBySessionIdAndProdId(sessionId, productId);
        }

        if (existingCart.isPresent()) {
            // 既にカートにある場合は数量を追加
            Cart cart = existingCart.get();
            cart.setQuantity(cart.getQuantity() + count);
            cartRepository.save(cart);
        } else {
            // 新規追加
            // ProductRepositoryはInteger型のIDを使用
            Optional<Product> productOpt = productRepository.findById(productId.intValue());
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                Cart newCart = new Cart();
                newCart.setUserId(userId);
                newCart.setSessionId(userId == null ? sessionId : null);
                newCart.setProdId(productId);
                newCart.setQuantity(count);
                newCart.setUnitPrice(BigDecimal.valueOf(product.getPrice()));
                newCart.setAddDate(LocalDateTime.now());
                cartRepository.save(newCart);
            }
        }
    }

    /**
     * カートから商品を削除
     * @param userId ログインユーザーのID（null可）
     * @param sessionId セッションID
     * @param productId 商品ID
     */
    public void removeFromCart(Long userId, String sessionId, Long productId) {
        if (userId != null) {
            cartRepository.deleteByUserIdAndProdId(userId, productId);
        } else {
            cartRepository.deleteBySessionIdAndProdId(sessionId, productId);
        }
    }

    /**
     * カートの数量を更新
     * @param userId ログインユーザーのID（null可）
     * @param sessionId セッションID
     * @param productId 商品ID
     * @param quantity 新しい数量
     * @param isGift ギフト設定
     */
    public void updateCartItem(Long userId, String sessionId, Long productId, int quantity, boolean isGift) {
        Optional<Cart> cartOpt;
        
        if (userId != null) {
            cartOpt = cartRepository.findByUserIdAndProdId(userId, productId);
        } else {
            cartOpt = cartRepository.findBySessionIdAndProdId(sessionId, productId);
        }

        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            if (quantity <= 0) {
                // 数量が0以下の場合は削除
                cartRepository.delete(cart);
            } else {
                cart.setQuantity(quantity);
                // ギフト設定（簡易的にoptionIdを1に設定、実際にはギフトオプションのIDを使用）
                if (isGift) {
                    cart.setOptionId(1L); // 仮のギフトオプションID
                    cart.setOptionPrice(BigDecimal.ZERO); // 無料ギフトの場合
                } else {
                    cart.setOptionId(null);
                    cart.setOptionPrice(null);
                }
                cartRepository.save(cart);
            }
        }
    }

    /**
     * カートを空にする
     * @param userId ログインユーザーのID（null可）
     * @param sessionId セッションID
     */
    public void clearCart(Long userId, String sessionId) {
        if (userId != null) {
            cartRepository.deleteByUserId(userId);
        } else {
            cartRepository.deleteBySessionId(sessionId);
        }
    }

    /**
     * セッションカートをユーザーに移行（ログイン時）
     * @param sessionId セッションID
     * @param userId ユーザーID
     */
    public void transferCartToUser(String sessionId, Long userId) {
        // 既存のセッションカートを取得
        List<Cart> sessionCarts = cartRepository.findBySessionId(sessionId);
        
        for (Cart sessionCart : sessionCarts) {
            // ユーザーのカートに同じ商品があるか確認
            Optional<Cart> userCartOpt = cartRepository.findByUserIdAndProdId(userId, sessionCart.getProdId());
            
            if (userCartOpt.isPresent()) {
                // 既存のユーザーカートに数量を追加
                Cart userCart = userCartOpt.get();
                userCart.setQuantity(userCart.getQuantity() + sessionCart.getQuantity());
                cartRepository.save(userCart);
                // セッションカートを削除
                cartRepository.delete(sessionCart);
            } else {
                // セッションカートをユーザーカートに変更
                sessionCart.setUserId(userId);
                sessionCart.setSessionId(null);
                cartRepository.save(sessionCart);
            }
        }
    }

    /**
     * カートの合計金額を計算
     * @param items カートアイテムリスト
     * @return 合計金額
     */
    public long calculateTotalPrice(List<Item> items) {
        return items.stream()
            .mapToLong(item -> (long) item.getProduct().getPrice() * item.getCount())
            .sum();
    }
    
    /**
     * カート内の商品数を取得
     * @param userId ログインユーザーのID（null可）
     * @param sessionId セッションID
     * @return カート内の商品数
     */
    public int getCartItemCount(Long userId, String sessionId) {
        List<Item> items = getCartItems(userId, sessionId);
        return items.stream().mapToInt(Item::getCount).sum();
    }
}
