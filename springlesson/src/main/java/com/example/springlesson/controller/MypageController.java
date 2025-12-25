package com.example.springlesson.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springlesson.entity.AllergenMaster;
import com.example.springlesson.entity.Member;
import com.example.springlesson.entity.MemberRank;
import com.example.springlesson.security.CustomerDetailsImpl;

@Controller
@RequestMapping("/mypage")
public class MypageController {

  @Autowired
  private com.example.springlesson.repository.MemberRankRepository rankRepository;
  // Serviceなどは省略

  //--- 【2. MemberRepositoryを自動注入する】 ---
  // データの保存（save）に必要です。リポジトリのパッケージ名は環境に合わせて調整してください。
  @Autowired
  private com.example.springlesson.repository.MemberRepository memberRepository;

  @Autowired
  private com.example.springlesson.repository.AllergenMasterRepository allergenMasterRepository;

  @Autowired
  private com.example.springlesson.service.MemberService memberService;

  /**
   * マイページ画面を表示する
   */
  @GetMapping
  public String showMypage(@AuthenticationPrincipal CustomerDetailsImpl userDetails, Model model) {
    if (userDetails == null)
      return "redirect:/login";

    // 1. 最新の会員情報をDBから取得（新規登録時のデータ漏れを防ぐため）
    Member member = memberRepository.findById(userDetails.getMember().getMemberId())
        .orElse(userDetails.getMember());

    BigDecimal spending = member.getAnnualSpending();
    List<MemberRank> allRanks = rankRepository.findAllByOrderByThresholdAmountAsc();

    // --- ランク計算ロジック (維持) ---
    if (allRanks.isEmpty()) {
      MemberRank dummy = new MemberRank();
      dummy.setRankName("ランク未設定");
      dummy.setIconHtml("❓");
      member.setCurrentRank(dummy);
    } else {
      MemberRank currentRank = allRanks.get(0);
      MemberRank nextRank = null;

      for (int i = 0; i < allRanks.size(); i++) {
        MemberRank r = allRanks.get(i);
        if (spending.compareTo(r.getThresholdAmount()) >= 0) {
          currentRank = r;
          if (i + 1 < allRanks.size()) {
            nextRank = allRanks.get(i + 1);
          } else {
            nextRank = null;
          }
        }
      }
      member.setCurrentRank(currentRank);
      member.setNextRank(nextRank);

      if (nextRank != null && nextRank.getThresholdAmount().compareTo(BigDecimal.ZERO) > 0) {
        BigDecimal gap = nextRank.getThresholdAmount().subtract(spending);
        member.setSpendingToNextRank(gap);
        double percent = (spending.doubleValue() / nextRank.getThresholdAmount().doubleValue()) * 100;
        member.setRankProgressPercent(Math.min(100, (int) percent));
      } else {
        member.setSpendingToNextRank(BigDecimal.ZERO);
        member.setRankProgressPercent(100);
      }
    }

    // --- アレルギー日本語変換ロジック (追加) ---
    List<AllergenMaster> allergenMasters = allergenMasterRepository.findAll();
    List<String> registeredAllergenNames = new ArrayList<>();

    // member.getAllergens() が null の場合に備えて安全策
    if (member.getAllergens() != null) {
      for (String code : member.getAllergens()) {
        for (AllergenMaster master : allergenMasters) {
          if (master.getAllergenCode().equals(code)) {
            registeredAllergenNames.add(master.getAllergenName());
            break;
          }
        }
      }
    }

    // --- モデル属性の追加 (維持 + 追加) ---
    model.addAttribute("member", member);
    model.addAttribute("allergenMasters", allergenMasters); // 全選択肢
    model.addAttribute("registeredAllergenNames", registeredAllergenNames); // 登録済みの日本語名リスト

    // 注文履歴（後ほど実装するため、空リストでエラー回避）
    model.addAttribute("orders", java.util.Collections.emptyList());

    return "mypage/mypage";
  }

  /**
   * 会員情報を更新する
   */
  @PostMapping("/update_info")
  public String updateInfo(@AuthenticationPrincipal CustomerDetailsImpl userDetails,
      Member formMember,
      RedirectAttributes redirectAttributes) {

    if (userDetails == null)
      return "redirect:/login";

    // 1. 現在ログイン中の会員エンティティを取得
    Member member = userDetails.getMember();

    // 2. フォーム（formMember）から送られてきた内容で会員情報を更新
    member.setFullName(formMember.getFullName());
    member.setPhoneNumber(formMember.getPhoneNumber());
    member.setPostalCode(formMember.getPostalCode());
    member.setPrefecture(formMember.getPrefecture());
    member.setAddressLine1(formMember.getAddressLine1());
    member.setAddressLine2(formMember.getAddressLine2());

    // 3. データベースに保存
    memberRepository.save(member);

    // 4. メッセージを添えてマイページへリダイレクト
    redirectAttributes.addFlashAttribute("successMessage", "会員情報を更新しました。");

    return "redirect:/mypage?section=info";
  }

  /**
   * 【追加箇所】アレルギー情報を更新する
   */
  @PostMapping("/update_allergies")
  public String updateAllergies(@AuthenticationPrincipal CustomerDetailsImpl userDetails,
      @RequestParam(value = "allergenCode", required = false) List<String> allergenCodes,
      RedirectAttributes redirectAttributes) {

    if (userDetails == null)
      return "redirect:/login";

    // 1. DBから「今の最新状態」を直接取ってくる（超重要！）
    Member member = memberRepository.findById(userDetails.getMember().getMemberId())
        .orElseThrow(() -> new RuntimeException("会員が見つかりません"));

    // 2. リストの中身を入れ替える
    // 新しいArrayListを作ることでHibernateに「変更があった」と確実に伝えます
    if (allergenCodes == null) {
      member.getAllergens().clear(); // 中身を空にする
    } else {
      member.setAllergens(new ArrayList<>(allergenCodes));
    }

    // 3. DBへ保存
    Member savedMember = memberRepository.save(member);

    // 4. ログイン情報（userDetails）の中身も最新に更新する
    // これをしないと、リダイレクト後の画面で古い情報が表示される原因になります
    userDetails.setMember(savedMember);

    redirectAttributes.addFlashAttribute("successMessage", "アレルギー情報を更新しました。");
    return "redirect:/mypage?section=allergy";
  }

  /**
   * 会員退会処理を実行する
   * @param userDetails
   * @param request
   * @return
   */
  @PostMapping("/withdraw")
  public String withdraw(java.security.Principal principal, jakarta.servlet.http.HttpServletRequest request) {
    // principal を引数に入れることで解決
    if (principal == null) {
      return "redirect:/login";
    }

    // Serviceを呼び出してDBを更新(status=0にする)
    memberService.withdraw(principal.getName());

    // セッションを無効化（ログアウト）
    try {
      request.logout();
    } catch (ServletException e) {
      e.printStackTrace();
    }

    // TOP画面へ（退会完了メッセージ用パラメータ付与）
    return "redirect:/?withdraw_success=true";
  }
}