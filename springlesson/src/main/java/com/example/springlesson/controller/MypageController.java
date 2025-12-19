package com.example.springlesson.controller;

import java.util.Collections;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springlesson.dto.MemberDTO;

@Controller
@RequestMapping("/mypage")
public class MypageController {

  // Serviceなどは省略

  /**
   * マイページ画面を表示する
   */
  @GetMapping
  public String showMypage(Model model) {
    // 実際の処理: ログインユーザーの情報をDBから取得する
    // ここではデモデータを作成
    MemberDTO member = createDummyMember();

    model.addAttribute("member", member);
    // ordersやallergenMastersなどのリストもダミーで追加
    model.addAttribute("orders", Collections.emptyList());
    model.addAttribute("allergenMasters", Collections.emptyList());

    // src/main/resources/templates/mypage/mypage.html を返す
    return "mypage/mypage";
  }

  // ... 実際の情報更新やパスワード変更処理は省略 ...

  /** デモ用ダミーデータ作成メソッド */
  private MemberDTO createDummyMember() {
    MemberDTO member = new MemberDTO();
    member.setFullName("田中 太郎");
    member.setAnnualSpending(35000);
    member.setAvailablePoints(1200);
    // ランク情報 (DTO側で定義が必要)
    member.setCurrentRank(new MemberDTO.RankInfo("ホールケーキランク", "👑"));
    member.setNextRank(new MemberDTO.RankInfo("バースデーランク", "🎂"));
    member.setSpendingToNextRank(15000);
    member.setRankProgressPercent(70); // 35000/50000 * 100
    return member;
  }
}