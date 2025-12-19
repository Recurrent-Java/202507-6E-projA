package com.example.springlesson.dto;

import java.util.List;

import lombok.Data;

@Data
public class MemberDTO {
  private String fullName;
  private String email;
  // ... その他の会員情報フィールド

  // ランク制度関連
  private RankInfo currentRank;
  private RankInfo nextRank;
  private Integer annualSpending; // 年間利用金額
  private Integer spendingToNextRank; // 次のランクまでの必要金額
  private Integer rankProgressPercent; // ランク進捗率
  private Integer availablePoints; // 保有ポイント

  // アレルギー情報
  private List<AllergenInfo> allergens;

  /** ランク情報内部クラス */
  @Data
  public static class RankInfo {
    private String rankName;
    private String iconHtml;

    public RankInfo(String rankName, String iconHtml) {
      this.rankName = rankName;
      this.iconHtml = iconHtml;
    }
  }

  /** アレルゲン情報内部クラス */
  @Data
  public static class AllergenInfo {
    private String name;
    private String code;
  }
}