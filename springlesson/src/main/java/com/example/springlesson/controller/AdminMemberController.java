package com.example.springlesson.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springlesson.dto.AdminMemberDto;
import com.example.springlesson.form.AdminMemberDeleteForm;
import com.example.springlesson.service.AdminMemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {
    
    private final AdminMemberService adminMemberService;
    
    /**
     * 管理者用会員管理画面の表示
     */
    @GetMapping
    public String showAdminMemberPage(Model model) {
        List<AdminMemberDto> members = adminMemberService.getAllMembers();
        model.addAttribute("members", members);
        model.addAttribute("adminMemberDeleteForm", new AdminMemberDeleteForm());
        
        return "admin/member-management";
    }
    
    /**
     * 会員一覧のみ取得（AJAX用）
     */
    @GetMapping("/list")
    @ResponseBody
    public List<AdminMemberDto> getMemberList() {
        return adminMemberService.getAllMembers();
    }
    
    /**
     * 有効な会員一覧を取得（AJAX用）
     */
    @GetMapping("/list/active")
    @ResponseBody
    public List<AdminMemberDto> getActiveMemberList() {
        return adminMemberService.getActiveMembers();
    }
    
    /**
     * 会員詳細取得（AJAX用）
     */
    @GetMapping("/{memberId}")
    @ResponseBody
    public AdminMemberDto getMember(@PathVariable Long memberId) {
        return adminMemberService.getMemberById(memberId);
    }
    
    /**
     * 会員検索（氏名）
     */
    @GetMapping("/search")
    public String searchMembers(
            @RequestParam(required = false) String fullName,
            Model model) {
        
        List<AdminMemberDto> members;
        
        if (fullName != null && !fullName.isEmpty()) {
            members = adminMemberService.searchMembersByName(fullName);
            model.addAttribute("searchKeyword", fullName);
        } else {
            members = adminMemberService.getAllMembers();
        }
        
        model.addAttribute("members", members);
        model.addAttribute("adminMemberDeleteForm", new AdminMemberDeleteForm());
        
        return "admin/member-management";
    }
    
    /**
     * 会員削除処理（論理削除）
     */
    @PostMapping("/delete")
    public String deleteMember(
            @Validated @ModelAttribute AdminMemberDeleteForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        if (bindingResult.hasErrors()) {
            List<AdminMemberDto> members = adminMemberService.getAllMembers();
            model.addAttribute("members", members);
            return "admin/member-management";
        }
        
        try {
            // まず会員情報を取得して表示用に保持
            AdminMemberDto member = adminMemberService.getMemberById(form.getMemberId());
            
            if (member == null) {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "会員が見つかりません: ID = " + form.getMemberId());
            } else {
                adminMemberService.deleteMember(form.getMemberId());
                redirectAttributes.addFlashAttribute("successMessage", 
                    "会員を削除しました: " + member.getFullName() + " (ID: " + member.getMemberId() + ")");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "会員の削除に失敗しました: " + e.getMessage());
        }
        
        return "redirect:/admin/members";
    }
    
    /**
     * 会員削除処理（物理削除）
     */
    @PostMapping("/delete/permanent/{memberId}")
    public String deleteMemberPermanently(
            @PathVariable Long memberId,
            RedirectAttributes redirectAttributes) {
        
        try {
            AdminMemberDto member = adminMemberService.getMemberById(memberId);
            
            if (member == null) {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "会員が見つかりません: ID = " + memberId);
            } else {
                adminMemberService.deleteMemberPermanently(memberId);
                redirectAttributes.addFlashAttribute("successMessage", 
                    "会員を完全に削除しました: " + member.getFullName() + " (ID: " + member.getMemberId() + ")");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "会員の削除に失敗しました: " + e.getMessage());
        }
        
        return "redirect:/admin/members";
    }
    
    /**
     * 会員削除処理（AJAX用）
     */
    @DeleteMapping("/{memberId}")
    @ResponseBody
    public String deleteMemberAjax(@PathVariable Long memberId) {
        try {
            adminMemberService.deleteMember(memberId);
            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }
}