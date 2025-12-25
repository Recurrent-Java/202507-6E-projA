package com.example.springlesson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.springlesson.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

  /**
   * 商品名で部分一致検索
   */
  List<Product> findByProductNameContaining(String keyword);

  /**
   * 単一カテゴリIDで検索
   */
  List<Product> findByCatId(Integer catId);

  /**
   * 複数カテゴリIDで検索（AND条件）
   * 選択したすべてのカテゴリーに該当する商品のみを表示
   */
  @Query("SELECT p FROM Product p " +
         "WHERE (SELECT COUNT(DISTINCT pcr.categoryId) " +
         "       FROM ProductCategoryRelation pcr " +
         "       WHERE pcr.productId = p.productId " +
         "       AND pcr.categoryId IN :catIds) = :catCount")
  List<Product> findByCategoryIds(@Param("catIds") List<Integer> catIds, @Param("catCount") long catCount);
}