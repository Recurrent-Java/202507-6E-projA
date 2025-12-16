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

    // --- 商品関連 ---

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public void saveProduct(AdminProductForm form) {
        Product product;

        // IDがあり、かつDBに存在する場合は更新モード
        if (form.getProductId() != null && productRepository.existsById(form.getProductId())) {
            product = productRepository.findById(form.getProductId()).orElse(new Product());
        } else {
            // 新規作成モード
            product = new Product();

            // SQLのNOT NULL制約に対応する初期値設定 [cite: 6, 7]
            product.setCatId(1);             // CAT_ID 
            product.setLimitedFlg(0);        // LIMITED_FLG 
            product.setUsage("自宅用");        // USAGE 
            product.setSeason(1);            // SEASON 
            product.setSaleStatus("SALE");   // sale_status 

            // 【最終修正】 isGiftAvailable に対応するセッター: setIsGiftAvailable()
            product.setIsGiftAvailable(0);   // is_gift_available  (ギフト対応可否)

            product.setIsLimited(0);         // is_limited  (限定商品か)

            // 【最終修正】 isLowStock に対応するセッター: setIsLowStock()
            product.setIsLowStock(0);        // is_low_stock  (残り数のフラグ)
        }

        // フォームからの入力値をセット
        product.setProductName(form.getProductName());
        product.setPrice(form.getPrice());
        product.setStockQuantity(form.getStockQuantity());

        productRepository.save(product);
    }

    public void deleteProduct(Long productId) { // IDの型がLongであることを確認
        if (productId != null && productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
        }
    }

    // --- 会員関連 ---

    public List<Customer> findAllMembers() {
        return customerRepository.findAll();
    }

    public void deleteMember(Long memberId) { // IDの型がLongであることを確認
        if (memberId != null && customerRepository.existsById(memberId)) {
            customerRepository.deleteById(memberId);
        }
    }
}