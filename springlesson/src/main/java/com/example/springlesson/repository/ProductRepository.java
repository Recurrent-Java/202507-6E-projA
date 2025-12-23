package com.example.springlesson.repository;

import java.util.List;

// Optionalのインポートは不要になります（標準機能を使うため）
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springlesson.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

  /**
   * 商品名で部分一致検索
   */
  List<Product> findByProductNameContaining(String keyword);

  List<Product> findByCatId(Integer catId);

  // ★修正ポイント：
  // existsById, findById, deleteById は JpaRepository が 
  // 自動的に「Integer型」で提供するため、ここに書くとエラーになります。
  // すべて削除してください。
}