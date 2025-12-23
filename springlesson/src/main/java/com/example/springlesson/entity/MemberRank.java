package com.example.springlesson.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "M_MEMBER_RANK")
@Data // Lombok使用の場合
public class MemberRank {
  @Id
  @Column(name = "RANK_CODE")
  private String rankCode;

  @Column(name = "RANK_NAME")
  private String rankName;

  @Column(name = "THRESHOLD_AMOUNT")
  private BigDecimal thresholdAmount;

  @Column(name = "ICON_HTML")
  private String iconHtml;
}
