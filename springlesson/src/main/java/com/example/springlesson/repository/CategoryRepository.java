package com.example.springlesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springlesson.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
  // 特別な検索をしない限り、中身は空っぽで大丈夫です。
  // JpaRepositoryを継承することで、findAll()などが自動的に使えるようになります。
}