package com.example.springlesson.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_ID")
    private Integer cartId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "SESSION_ID")
    private String sessionId;

    @Column(name = "PROD_ID", nullable = false)
    private Long prodId;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "UNIT_PRICE", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "OPTION_ID")
    private Long optionId;

    @Column(name = "OPTION_PRICE")
    private BigDecimal optionPrice;

    @Column(name = "ADD_DATE", nullable = false)
    private LocalDateTime addDate;

    // 商品情報との関連（読み取り専用で使用）
    @ManyToOne
    @JoinColumn(name = "PROD_ID", insertable = false, updatable = false)
    private Product product;
}
