package com.example.springlesson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springlesson.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
  // 全商品検索
  // List<Product> findAll(); メソッドの実装をJPAが作成してくれている
  
  /*
   *  商品名で検索
   *  findByName -> 完全一致
   *  findByNameContaining -> 部分一致
   */
  List<Product> findByNameContaining(String keyword);
}
