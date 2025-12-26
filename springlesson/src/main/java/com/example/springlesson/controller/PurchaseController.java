package com.example.springlesson.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springlesson.form.Item;
import com.example.springlesson.service.PurchaseService;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    /**
     * 購入入力ページの表示
     */
    @GetMapping({ "", "/", "/input" })
    public String purchaseInput(HttpSession session, Model model) {
        // セッションからカートを取得
        @SuppressWarnings("unchecked")
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        
        // カートが空の場合はカートページにリダイレクト
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        // 商品合計金額を計算
        long productTotal = cart.stream()
            .mapToLong(item -> (long) item.getProduct().getPrice() * item.getCount())
            .sum();
        
        // 送料（基本420円）
        int shippingFee = 420;
        
        // 合計金額
        long grandTotal = productTotal + shippingFee;

        // 選択肢を作成
        model.addAttribute("deliveryDateOptions", createDeliveryDateOptions());
        model.addAttribute("deliveryTimeOptions", createDeliveryTimeOptions());
        model.addAttribute("destinationOptions", createDestinationOptions());
        model.addAttribute("giftOptions", createGiftOptions());
        model.addAttribute("paymentOptions", createPaymentOptions());

        // 金額情報
        model.addAttribute("productTotal", productTotal);
        model.addAttribute("shippingFee", shippingFee);
        model.addAttribute("grandTotal", grandTotal);
        
        // カート情報
        model.addAttribute("cart", cart);

        return "purchase/purchase-in";
    }

    /**
     * 注文確定処理
     */
    @PostMapping("/confirm")
    public String confirm(HttpSession session, Model model,
                          @RequestParam(required = false) String deliveryDate,
                          @RequestParam(required = false) String deliveryTime,
                          @RequestParam(required = false) String shippingDestination,
                          @RequestParam(required = false) String giftOption,
                          @RequestParam(required = false) String paymentMethod,
                          @RequestParam(required = false) String couponCode,
                          @RequestParam(required = false) String remarks) {

        try {
            // セッションからカートを取得
            @SuppressWarnings("unchecked")
            List<Item> cart = (List<Item>) session.getAttribute("cart");
            
            if (cart == null || cart.isEmpty()) {
                throw new Exception("購入する商品が選択されていません。");
            }

            // 購入処理（簡易版）
            // TODO: 実際の購入処理を実装（注文テーブルへの登録など）
            
            // セッションからカート情報を削除
            session.removeAttribute("cart");

            // 購入完了画面へ
            return "purchase/purchase-out";
            
        } catch (Exception e) {
            model.addAttribute("errMsg", e.getMessage());
            return "error/error";
        }
    }

    /**
     * 配送希望日の選択肢を作成
     */
    private List<Map<String, String>> createDeliveryDateOptions() {
        List<Map<String, String>> options = new ArrayList<>();
        
        // 「指定なし」オプション
        Map<String, String> noSpec = new HashMap<>();
        noSpec.put("value", "");
        noSpec.put("label", "指定なし");
        options.add(noSpec);
        
        // 3日後から14日後までの日付を選択肢として追加
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd (E)");
        
        for (int i = 3; i <= 14; i++) {
            LocalDate date = today.plusDays(i);
            Map<String, String> option = new HashMap<>();
            option.put("value", date.toString());
            option.put("label", date.format(formatter));
            options.add(option);
        }
        
        return options;
    }

    /**
     * 時間指定の選択肢を作成
     */
    private List<Map<String, String>> createDeliveryTimeOptions() {
        List<Map<String, String>> options = new ArrayList<>();
        
        String[][] timeSlots = {
            {"", "指定なし"},
            {"08-12", "午前中 (8:00-12:00)"},
            {"12-14", "12:00-14:00"},
            {"14-16", "14:00-16:00"},
            {"16-18", "16:00-18:00"},
            {"18-20", "18:00-20:00"},
            {"19-21", "19:00-21:00"}
        };
        
        for (String[] slot : timeSlots) {
            Map<String, String> option = new HashMap<>();
            option.put("value", slot[0]);
            option.put("label", slot[1]);
            options.add(option);
        }
        
        return options;
    }

    /**
     * 配送先の選択肢を作成
     */
    private List<Map<String, String>> createDestinationOptions() {
        List<Map<String, String>> options = new ArrayList<>();
        
        String[][] destinations = {
            {"home", "登録住所に届ける"},
            {"other", "別の住所に届ける"},
            {"hokkaido", "北海道 (+1,100円)"},
            {"okinawa", "沖縄 (+1,100円)"}
        };
        
        for (String[] dest : destinations) {
            Map<String, String> option = new HashMap<>();
            option.put("value", dest[0]);
            option.put("label", dest[1]);
            options.add(option);
        }
        
        return options;
    }

    /**
     * ギフトオプションの選択肢を作成
     */
    private List<Map<String, String>> createGiftOptions() {
        List<Map<String, String>> options = new ArrayList<>();
        
        String[][] giftOpts = {
            {"none", "ギフトラッピングなし"},
            {"standard", "標準ラッピング (+300円)"},
            {"premium", "プレミアムラッピング (+500円)"},
            {"message", "メッセージカード付き (+200円)"}
        };
        
        for (String[] gift : giftOpts) {
            Map<String, String> option = new HashMap<>();
            option.put("value", gift[0]);
            option.put("label", gift[1]);
            options.add(option);
        }
        
        return options;
    }

    /**
     * 支払い方法の選択肢を作成
     */
    private List<Map<String, String>> createPaymentOptions() {
        List<Map<String, String>> options = new ArrayList<>();
        
        String[][] payments = {
            {"credit", "クレジットカード"},
            {"convenience", "コンビニ支払い"},
            {"bank", "銀行振込"},
            {"cod", "代金引換 (+330円)"}
        };
        
        for (String[] payment : payments) {
            Map<String, String> option = new HashMap<>();
            option.put("value", payment[0]);
            option.put("label", payment[1]);
            options.add(option);
        }
        
        return options;
    }
}
