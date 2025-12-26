package com.example.springlesson.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.springlesson.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    /**
     * ログインユーザーのカート一覧を取得
     */
    List<Cart> findByUserId(Long userId);

    /**
     * セッションIDでカート一覧を取得（非ログインユーザー用）
     */
    List<Cart> findBySessionId(String sessionId);

    /**
     * ユーザーIDと商品IDでカートアイテムを検索
     */
    Optional<Cart> findByUserIdAndProdId(Long userId, Long prodId);

    /**
     * セッションIDと商品IDでカートアイテムを検索
     */
    Optional<Cart> findBySessionIdAndProdId(String sessionId, Long prodId);

    /**
     * ユーザーIDと商品IDでカートから削除
     */
    @Modifying
    @Query("DELETE FROM Cart c WHERE c.userId = :userId AND c.prodId = :prodId")
    void deleteByUserIdAndProdId(@Param("userId") Long userId, @Param("prodId") Long prodId);

    /**
     * セッションIDと商品IDでカートから削除
     */
    @Modifying
    @Query("DELETE FROM Cart c WHERE c.sessionId = :sessionId AND c.prodId = :prodId")
    void deleteBySessionIdAndProdId(@Param("sessionId") String sessionId, @Param("prodId") Long prodId);

    /**
     * ユーザーIDでカートを全削除
     */
    void deleteByUserId(Long userId);

    /**
     * セッションIDでカートを全削除
     */
    void deleteBySessionId(String sessionId);

    /**
     * セッションのカートをユーザーIDに移行（ログイン時）
     */
    @Modifying
    @Query("UPDATE Cart c SET c.userId = :userId, c.sessionId = NULL WHERE c.sessionId = :sessionId")
    void transferSessionCartToUser(@Param("sessionId") String sessionId, @Param("userId") Long userId);
}
