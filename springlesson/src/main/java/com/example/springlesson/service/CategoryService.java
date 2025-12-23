package com.example.springlesson.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springlesson.entity.Category;
import com.example.springlesson.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

  // 先ほど作ったRepositoryを注入（インジェクション）します
  private final CategoryRepository categoryRepository;

  /**
   * カテゴリー一覧をすべて取得します
   */
  public List<Category> findAll() {
    return categoryRepository.findAll();
  }
}