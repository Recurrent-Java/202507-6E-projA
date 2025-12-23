package com.example.springlesson.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.Data;

@Entity
@Table(name = "MEMBER")
@Data
public class Member {
  @Id // 主キーとして認識させる
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long memberId;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(name = "password_hash", nullable = false)
  private String passwordHash;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Column(name = "postal_code")
  private String postalCode;

  private String prefecture;

  @Column(name = "address_line1")
  private String addressLine1;

  @Column(name = "address_line2")
  private String addressLine2;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "registration_date", nullable = false)
  private java.time.LocalDateTime registrationDate;

  // SQLのNOT NULL制約に対応するための初期値
  @Column(nullable = false)
  private Integer status = 1;

  @Column(name = "dm_reg_complete", nullable = false)
  private Integer dmRegComplete = 1;

  @Column(name = "dm_limited_item", nullable = false)
  private Integer dmLimitedItem = 0;

  @Column(name = "dm_campaign", nullable = false)
  private Integer dmCampaign = 1;

  @Column(name = "coupon_used_flag", nullable = false)
  private Integer couponUsedFlag = 0;

  @Column(name = "CURRENT_RANK_CODE", nullable = false)
  private String currentRankCode = "COOKIE";

  @Column(name = "ANNUAL_SPENDING", nullable = false)
  private BigDecimal annualSpending = BigDecimal.ZERO;

  // ポイントのカラムもSQLに合わせて追加（HTMLでのエラー回避用）
  @Column(name = "AVAILABLE_POINTS", nullable = false)
  private Integer availablePoints = 0;

  // 画面表示用の一時的なフィールド（DBには保存しない）
  @Transient
  private MemberRank currentRank;

  // mypage.html で ${member.currentRank} を使うために必要
  public MemberRank getCurrentRank() {
    return this.currentRank;
  }

  public void setCurrentRank(MemberRank currentRank) {
    this.currentRank = currentRank;
  }

  @Transient
  private MemberRank nextRank; // 追加

  // mypage.html の ${member.nextRank} に対応
  public MemberRank getNextRank() {
    return this.nextRank;
  }

  public void setNextRank(MemberRank nextRank) {
    this.nextRank = nextRank;
  }

  // ついでに HTML で使われている可能性がある spendingToNextRank も追加しておくと安全です
  @Transient
  private java.math.BigDecimal spendingToNextRank = java.math.BigDecimal.ZERO;

  public java.math.BigDecimal getSpendingToNextRank() {
    return this.spendingToNextRank;
  }

  @Transient
  private Integer rankProgressPercent = 0; // 進捗率（0〜100）

  public Integer getRankProgressPercent() {
    return this.rankProgressPercent;
  }

  public void setRankProgressPercent(Integer rankProgressPercent) {
    this.rankProgressPercent = rankProgressPercent;
  }

  @ElementCollection(fetch = FetchType.EAGER) // 即時ロードでアレルギー情報を取得
  @CollectionTable(name = "ALLERGY_SETTING", // 中間テーブル名
      joinColumns = @JoinColumn(name = "member_id") // 会員IDを結びつけるカラム名
  )
  @Column(name = "allergen_code") // 保存する値（EGG等）が入るカラム名
  private List<String> allergens = new ArrayList<>();

  public List<String> getAllergens() {
    return this.allergens;
  }

  public void setAllergens(List<String> allergens) {
    this.allergens = allergens;
  }
}