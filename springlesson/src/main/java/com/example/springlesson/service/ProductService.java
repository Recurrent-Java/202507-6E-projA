package com.example.springlesson.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springlesson.dto.ProductDTO;
import com.example.springlesson.entity.Product;
import com.example.springlesson.mapper.ProductMapper;
import com.example.springlesson.repository.ProductRepository;

@Service
public class ProductService {
  private final ProductMapper productMapper;
  private final ProductRepository productRepository;

  public ProductService(ProductMapper productMapper,ProductRepository productRepository) {
    this.productMapper = productMapper;
    this.productRepository = productRepository;
  }
  // 全ての商品情報を取得する
  public List<ProductDTO> findAllProduct(){
    List<Product> list = productRepository.findAll();
    return productMapper.toDtoList(list);
  }
  // 商品名で商品情報を取得する
  public List<ProductDTO> findByNameContaining(String keyword){
    List<Product> list = productRepository.findByNameContaining(keyword);
    return productMapper.toDtoList(list);
  }
}