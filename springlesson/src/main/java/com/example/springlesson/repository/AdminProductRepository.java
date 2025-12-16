package com.example.springlesson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springlesson.entity.Product;

@Repository
public interface AdminProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * 商品一覧を取得（管理者用）
     * すべての商品を取得（status関係なく）
     */
    List<Product> findAllByOrderByProductIdDesc();
    
    /**
     * 商品IDで検索
     */
    Product findByProductId(Long productId);
    
    /**
     * 商品名で部分一致検索（管理者用）
     */
    List<Product> findByProductNameContainingOrderByProductIdDesc(String productName);
    
    /**
     * 在庫数が指定数以下の商品を取得
     */
    List<Product> findByStockQuantityLessThanEqualOrderByStockQuantityAsc(Integer threshold);
}