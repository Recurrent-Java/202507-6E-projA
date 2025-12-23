package com.example.springlesson.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springlesson.entity.MemberInfo;
import com.example.springlesson.entity.Product;
import com.example.springlesson.form.AdminProductForm;
import com.example.springlesson.repository.MemberInfoRepository;
import com.example.springlesson.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final ProductRepository productRepository;
    private final MemberInfoRepository memberInfoRepository;

    // 画像保存先（最後は / で終わること）
    private static final String UPLOAD_DIR =
        "C:/teama-git/202507-6E-projA/springlesson/src/main/resources/static/images/";

    // --- 商品一覧 ---
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    // --- 商品登録・更新 ---
    public void saveProduct(AdminProductForm form) {

        Product product;
        Integer productId = form.getProductId();

        // 既存商品か新規商品か判定
        if (productId != null && productRepository.existsById(productId)) {
            product = productRepository.findById(productId).orElse(new Product());
        } else {
            product = new Product();
            // 新規商品の初期値設定
            product.setCatId(1);
            product.setLimitedFlg(0);
            product.setUsage("自宅用");
            product.setSeason(1);
            product.setSaleStatus("SALE");
            product.setIsGiftAvailable(0);
            product.setIsLimited(0);
            product.setIsLowStock(0);
        }

        // 基本情報の更新
        product.setProductName(form.getProductName());
        product.setPrice(form.getPrice());
        product.setStockQuantity(form.getStockQuantity());

        // --- 画像アップロード処理 ---
        if (form.getImageFile() != null && !form.getImageFile().isEmpty()) {
            try {
                // UUID でファイル名の衝突を防ぐ
                String fileName = UUID.randomUUID().toString() + "_" +
                                  form.getImageFile().getOriginalFilename();

                File dest = new File(UPLOAD_DIR + fileName);

                // ディレクトリが無ければ作成
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }

                // ファイル保存
                form.getImageFile().transferTo(dest);

                // DB にはファイル名だけ保存（パスは固定）
                product.setImagePath(fileName);

            } catch (IOException e) {
                e.printStackTrace();
                // 必要に応じて例外スロー
            }
        }

        productRepository.save(product);
    }

    // --- 商品削除 ---
    public void deleteProduct(Integer productId) {
        if (productId != null && productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
        }
    }

    // --- 会員一覧 ---
    public List<MemberInfo> findAllMembers() {
        return memberInfoRepository.findAll();
    }

    // --- 会員削除 ---
    public void deleteMember(Integer memberId) {
        if (memberId != null && memberInfoRepository.existsById(memberId)) {
            memberInfoRepository.deleteById(memberId);
        }
    }
}
