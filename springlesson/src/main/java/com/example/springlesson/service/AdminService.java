package com.example.springlesson.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springlesson.entity.MemberInfo;
import com.example.springlesson.entity.Product;
import com.example.springlesson.form.AdminProductForm;
import com.example.springlesson.repository.CustomerRepository;
import com.example.springlesson.repository.MemberInfoRepository; // 追加
import com.example.springlesson.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

  private final ProductRepository productRepository;
  private final CustomerRepository customerRepository;
  private final MemberInfoRepository memberInfoRepository;

  public List<Product> findAllProducts() {
    return productRepository.findAll();
  }

  public void saveProduct(AdminProductForm form) {
    Product product;

    Integer productId = form.getProductId();

    if (productId != null && productRepository.existsById(productId)) {
      product = productRepository.findById(productId).orElse(new Product());
    } else {
      product = new Product();

      // product.setCatId(1);
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

  public void deleteProduct(Integer productId) {
    if (productId != null && productRepository.existsById(productId)) {
      productRepository.deleteById(productId);
    }
  }

  public List<MemberInfo> findAllMembers() {
	    return memberInfoRepository.findAll();
	  }

	  public void deleteMember(Integer memberId) {
	    if (memberId != null && memberInfoRepository.existsById(memberId)) {
	      memberInfoRepository.deleteById(memberId);
	    }
	  }
	}