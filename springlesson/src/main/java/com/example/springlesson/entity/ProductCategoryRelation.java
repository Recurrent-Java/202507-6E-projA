package com.example.springlesson.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "product_category_relation")
@IdClass(ProductCategoryRelation.ProductCategoryRelationId.class)
public class ProductCategoryRelation {

    @Id
    @Column(name = "product_id")
    private Long productId;

    @Id
    @Column(name = "category_id")
    private Integer categoryId;

    // コンストラクタ
    public ProductCategoryRelation() {
    }

    public ProductCategoryRelation(Long productId, Integer categoryId) {
        this.productId = productId;
        this.categoryId = categoryId;
    }

    // Getter/Setter
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    // 複合主キー用のIDクラス
    public static class ProductCategoryRelationId implements Serializable {
        private Long productId;
        private Integer categoryId;

        public ProductCategoryRelationId() {
        }

        public ProductCategoryRelationId(Long productId, Integer categoryId) {
            this.productId = productId;
            this.categoryId = categoryId;
        }

        // Getter/Setter
        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        // equals/hashCode
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ProductCategoryRelationId that = (ProductCategoryRelationId) o;

            if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
            return categoryId != null ? categoryId.equals(that.categoryId) : that.categoryId == null;
        }

        @Override
        public int hashCode() {
            int result = productId != null ? productId.hashCode() : 0;
            result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
            return result;
        }
    }
}
