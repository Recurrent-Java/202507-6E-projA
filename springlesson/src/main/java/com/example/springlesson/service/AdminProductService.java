package com.example.springlesson.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springlesson.dto.AdminProductAllergenDto;
import com.example.springlesson.dto.AdminProductDto;
import com.example.springlesson.entity.Product;
import com.example.springlesson.entity.ProductAllergen;
import com.example.springlesson.form.AdminProductForm;
import com.example.springlesson.repository.AdminProductRepository;
import com.example.springlesson.repository.ProductAllergenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminProductService {
    
    private final AdminProductRepository adminProductRepository;
    private final ProductAllergenRepository productAllergenRepository;
    
    /**
     * 商品一覧を取得（管理者用）
     */
    @Transactional(readOnly = true)
    public List<AdminProductDto> getAllProducts() {
        List<Product> products = adminProductRepository.findAllByOrderByProductIdDesc();
        
        return products.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    /**
     * 商品IDで検索
     */
    @Transactional(readOnly = true)
    public AdminProductDto getProductById(Long productId) {
        Product product = adminProductRepository.findByProductId(productId);
        if (product == null) {
            return null;
        }
        
        AdminProductDto dto = convertToDto(product);
        
        // アレルゲン情報を取得
        List<ProductAllergen> allergens = productAllergenRepository.findByProductIdWithAllergen(productId);
        List<AdminProductAllergenDto> allergenDtos = allergens.stream()
            .map(pa -> new AdminProductAllergenDto(
                pa.getAllergenCode(),
                pa.getAllergen().getAllergenName(),
                pa.getIsContained(),
                pa.getNote()
            ))
            .collect(Collectors.toList());
        
        dto.setAllergenList(allergenDtos);
        
        return dto;
    }
    
    /**
     * 商品を登録
     */
    @Transactional
    public Product createProduct(AdminProductForm form) {
        Product product = new Product();
        setProductFromForm(product, form);
        
        return adminProductRepository.save(product);
    }
    
    /**
     * 商品を更新
     */
    @Transactional
    public Product updateProduct(Long productId, AdminProductForm form) {
        Product product = adminProductRepository.findByProductId(productId);
        if (product == null) {
            throw new RuntimeException("商品が見つかりません: ID = " + productId);
        }
        
        setProductFromForm(product, form);
        
        return adminProductRepository.save(product);
    }
    
    /**
     * 商品を削除
     */
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = adminProductRepository.findByProductId(productId);
        if (product == null) {
            throw new RuntimeException("商品が見つかりません: ID = " + productId);
        }
        
        // 関連するアレルゲン情報も削除
        productAllergenRepository.deleteByProductId(productId);
        
        adminProductRepository.delete(product);
    }
    
    /**
     * EntityをDTOに変換
     */
    private AdminProductDto convertToDto(Product product) {
        AdminProductDto dto = new AdminProductDto();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setSaleStatus(product.getSaleStatus());
        dto.setIsLimited(product.getIsLimited());
        dto.setDescription(product.getDescription());
        dto.setIngredientDetail(product.getIngredientDetail());
        dto.setBrandStory(product.getBrandStory());
        dto.setExpirationStorage(product.getExpirationStorage());
        dto.setIsGiftAvailable(product.getIsGiftAvailable());
        
        // アレルゲン名を取得（カンマ区切り）
        List<ProductAllergen> allergens = productAllergenRepository.findByProductIdWithAllergen(product.getProductId());
        String allergenNames = allergens.stream()
            .map(pa -> pa.getAllergen().getAllergenName())
            .collect(Collectors.joining(", "));
        dto.setAllergens(allergenNames);
        
        return dto;
    }
    
    /**
     * フォームデータをEntityに設定
     */
    private void setProductFromForm(Product product, AdminProductForm form) {
        product.setProductName(form.getProductName());
        product.setPrice(form.getPrice());
        product.setStockQuantity(form.getStockQuantity());
        product.setDescription(form.getDescription());
        product.setIngredientDetail(form.getIngredientDetail());
        product.setBrandStory(form.getBrandStory());
        product.setExpirationStorage(form.getExpirationStorage());
        product.setIsGiftAvailable(form.getIsGiftAvailable());
        product.setSaleStatus(form.getSaleStatus());
        product.setIsLimited(form.getIsLimited());
        product.setCatId(form.getCatId());
        product.setLimitedFlg(form.getLimitedFlg());
        product.setUsage(form.getUsage());
        product.setSeason(form.getSeason());
        
        // 在庫数に応じて在庫少フラグを設定
        product.setIsLowStock(form.getStockQuantity() <= 10);
    }
}