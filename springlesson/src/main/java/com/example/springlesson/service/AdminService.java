package com.example.springlesson.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springlesson.entity.Customer;
import com.example.springlesson.entity.Product;
import com.example.springlesson.form.AdminProductForm;
import com.example.springlesson.repository.CustomerRepository;
import com.example.springlesson.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

  private final ProductRepository productRepository;
  private final CustomerRepository customerRepository;

  public List<Product> findAllProducts() {
    return productRepository.findAll();
  }

  public void saveProduct(AdminProductForm form) {
    Product product;

    // ★修正：formのIDを取得。Integerであることを確認してください。
    Integer productId = form.getProductId();

    if (productId != null && productRepository.existsById(productId)) {
      product = productRepository.findById(productId).orElse(new Product());
    } else {
      product = new Product();
      // ...省略（初期値設定）
      product.setCatId(1);
      product.setLimitedFlg(0);
      product.setUsage("自宅用");
      product.setSeason(1);
      product.setSaleStatus("SALE");
      product.setIsGiftAvailable(0);
      product.setIsLimited(0);
      product.setIsLowStock(0);
    }

    product.setProductName(form.getProductName());
    product.setPrice(form.getPrice());
    product.setStockQuantity(form.getStockQuantity());

    productRepository.save(product);
  }

  // ★修正：引数を Long から Integer に変更
  public void deleteProduct(Integer productId) {
    if (productId != null && productRepository.existsById(productId)) {
      productRepository.deleteById(productId);
    }
  }

  // --- 会員関連 ---

  public List<Customer> findAllMembers() {
    return customerRepository.findAll();
  }

  // ★注意：CustomerエンティティのIDがIntegerならここもIntegerにします
  public void deleteMember(Integer memberId) {
    if (memberId != null && customerRepository.existsById(memberId)) {
      customerRepository.deleteById(memberId);
    }
  }
}