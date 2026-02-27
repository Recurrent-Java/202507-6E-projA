// src/main/java/com/example/springlesson/service/AdminService.java
package com.example.springlesson.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.springlesson.entity.MemberInfo;
import com.example.springlesson.entity.Product;
import com.example.springlesson.form.AdminProductForm;
import com.example.springlesson.repository.MemberInfoRepository;
import com.example.springlesson.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final ProductRepository productRepository;
    private final MemberInfoRepository memberInfoRepository;

    /* ==================== 画像アップロード先（最後に / が必要） ==================== */
    @Value("${app.upload-dir}")
    private String uploadDir;          // ex: "C:/teama-git/.../static/images/"

    /* ==================== 商品一覧 ==================== */
    @Transactional(readOnly = true)
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    /* ==================== 商品登録・更新 ==================== */
    public void saveProduct(AdminProductForm form) throws IOException, ValidationException {

        Product product;
        Integer id = form.getProductId();

        // 既存商品か新規商品か判定
        if (id != null && productRepository.existsById(id)) {
            product = productRepository.findById(id)
                                      .orElseThrow(() -> new IllegalStateException("該当商品が見つかりません"));
        } else {                                // 新規作成時のデフォルト設定
            product = new Product();
            product.setCatId(1);
            product.setLimitedFlg(0);
            product.setUsage("自宅用");
            product.setSeason(1);
            product.setSaleStatus("SALE");
            product.setIsGiftAvailable(0);
            product.setIsLimited(0);
            product.setIsLowStock(0);
        }

        /* ---- 基本情報更新 ---- */
        product.setProductName(form.getProductName());
        product.setPrice(form.getPrice());
        product.setStockQuantity(form.getStockQuantity());

        /* ---- 画像アップロード処理 ---- */
        MultipartFile image = form.getImageFile();
        if (image != null && !image.isEmpty()) {
            // ファイル名をそのまま使うが、サニタイズは必ず実施
            String originalFilename = Paths.get(image.getOriginalFilename()).getFileName().toString();

            Path targetPath = Paths.get(uploadDir, originalFilename);
            Files.createDirectories(targetPath.getParent());  // ディレクトリが無ければ作成

            image.transferTo(targetPath.toFile());

            // DB には公開用 URL を保存
            product.setImagePath("/images/" + originalFilename);
        }

        /* ---- 保存 ---- */
        productRepository.save(product);
    }

    /* ==================== 商品削除 ==================== */
    public void deleteProduct(Integer productId) {
        if (productId != null && productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
        }
    }

    /* ==================== 会員一覧 ==================== */
    @Transactional(readOnly = true)
    public List<MemberInfo> findAllMembers() {
        return memberInfoRepository.findAll();
    }

    /* ==================== 会員削除 ==================== */
    public void deleteMember(Integer memberId) {
        if (memberId != null && memberInfoRepository.existsById(memberId)) {
            memberInfoRepository.deleteById(memberId);
        }
    }
}
