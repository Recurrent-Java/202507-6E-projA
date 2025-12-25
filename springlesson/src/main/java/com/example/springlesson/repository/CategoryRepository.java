package com.example.springlesson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springlesson.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
  // 特別な検索をしない限り、中身は空っぽで大丈夫です。
  // JpaRepositoryを継承することで、findAll()などが自動的に使えるようになります。

  /**
   * 商品IDに紐づくカテゴリー一覧を取得
   */
  @Query("SELECT c FROM Category c " +
         "WHERE c.catId IN (SELECT pcr.categoryId FROM ProductCategoryRelation pcr WHERE pcr.productId = :productId)")
  List<Category> findByProductId(@Param("productId") Long productId);
}