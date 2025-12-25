package com.example.springlesson.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.springlesson.dto.ProductDTO;
import com.example.springlesson.entity.Category;
import com.example.springlesson.entity.Product;
import com.example.springlesson.mapper.ProductMapper;
import com.example.springlesson.repository.CategoryRepository;
import com.example.springlesson.repository.ProductRepository;

@Service
public class ProductService {
  private final ProductMapper productMapper;
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  // コンストラクタ注入
  public ProductService(ProductMapper productMapper, ProductRepository productRepository, CategoryRepository categoryRepository) {
    this.productMapper = productMapper;
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
  }

  /**
   * 全ての商品情報を取得する
   */
  public List<ProductDTO> findAllProduct() {
    List<Product> list = productRepository.findAll();
    return productMapper.toDtoList(list);
  }

  /**
   * 商品名で商品情報を検索する
   */
  public List<ProductDTO> findByNameContaining(String keyword) {
    // Repository側のメソッド名が findByProductNameContaining であることを確認済み
    List<Product> list = productRepository.findByProductNameContaining(keyword);
    return productMapper.toDtoList(list);
  }

  /**
   * カテゴリIDで商品を取得する
   */
  public List<ProductDTO> findByCategory(Integer catId) {
    List<Product> list = productRepository.findByCatId(catId);
    return productMapper.toDtoList(list);
  }

  /**
   * 複数カテゴリIDで商品を取得する（AND条件）
   * 選択したすべてのカテゴリーに該当する商品のみを返す
   */
  public List<ProductDTO> findByCategories(List<Integer> catIds) {
    List<Product> list = productRepository.findByCategoryIds(catIds, catIds.size());
    return productMapper.toDtoList(list);
  }

  /**
   * 商品IDで商品詳細を取得する（カテゴリー情報付き）
   */
  public Optional<ProductDTO> findProductById(Integer productId) {
    Optional<Product> productOpt = productRepository.findById(productId);
    if (productOpt.isEmpty()) {
      return Optional.empty();
    }

    Product product = productOpt.get();
    ProductDTO dto = productMapper.toDto(product);

    // 商品に紐づくカテゴリー一覧を取得
    List<Category> categories = categoryRepository.findByProductId(dto.getProductId());
    List<ProductDTO.CategoryInfo> categoryInfoList = categories.stream()
        .map(cat -> new ProductDTO.CategoryInfo(cat.getCatId(), cat.getCatName()))
        .collect(Collectors.toList());
    dto.setCategories(categoryInfoList);

    return Optional.of(dto);
  }
}