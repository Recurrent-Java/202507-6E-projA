package com.example.springlesson.service;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springlesson.dto.AdminMemberDto;
import com.example.springlesson.repository.AdminMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminMemberService {
    
    private final AdminMemberRepository adminMemberRepository;
    
    /**
     * 会員一覧を取得（管理者用）
     */
    @Transactional(readOnly = true)
    public List<AdminMemberDto> getAllMembers() {
        List<Member> members = adminMemberRepository.findAllByOrderByRegistrationDateDesc();
        
        return members.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    /**
     * 有効な会員のみ取得
     */
    @Transactional(readOnly = true)
    public List<AdminMemberDto> getActiveMembers() {
        List<Member> members = adminMemberRepository.findByStatusOrderByRegistrationDateDesc(1);
        
        return members.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    /**
     * 会員IDで検索
     */
    @Transactional(readOnly = true)
    public AdminMemberDto getMemberById(Long memberId) {
        Optional<Member> memberOpt = adminMemberRepository.findByMemberId(memberId);
        
        return memberOpt.map(this::convertToDto).orElse(null);
    }
    
    /**
     * 氏名で検索
     */
    @Transactional(readOnly = true)
    public List<AdminMemberDto> searchMembersByName(String fullName) {
        List<Member> members = adminMemberRepository.findByFullNameContainingOrderByRegistrationDateDesc(fullName);
        
        return members.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    /**
     * 会員を削除（論理削除）
     */
    @Transactional
    public void deleteMember(Long memberId) {
        Optional<Member> memberOpt = adminMemberRepository.findByMemberId(memberId);
        
        if (memberOpt.isEmpty()) {
            throw new RuntimeException("会員が見つかりません: ID = " + memberId);
        }
        
        Member member = memberOpt.get();
        
        // 論理削除（statusを0に設定）
        member.setStatus(0);
        adminMemberRepository.save(member);
    }
    
    /**
     * 会員を物理削除
     */
    @Transactional
    public void deleteMemberPermanently(Long memberId) {
        Optional<Member> memberOpt = adminMemberRepository.findByMemberId(memberId);
        
        if (memberOpt.isEmpty()) {
            throw new RuntimeException("会員が見つかりません: ID = " + memberId);
        }
        
        adminMemberRepository.delete(memberOpt.get());
    }
    
    /**
     * EntityをDTOに変換
     */
    private AdminMemberDto convertToDto(Member member) {
        AdminMemberDto dto = new AdminMemberDto();
        dto.setMemberId(member.getMemberId());
        dto.setFullName(member.getFullName());
        dto.setEmail(member.getEmail());
        dto.setRegistrationDate(member.getRegistrationDate());
        dto.setLastLoginAt(member.getLastLoginAt());
        dto.setStatus(member.getStatus());
        dto.setPhoneNumber(member.getPhoneNumber());
        dto.setPostalCode(member.getPostalCode());
        dto.setPrefecture(member.getPrefecture());
        dto.setAddressLine1(member.getAddressLine1());
        dto.setAddressLine2(member.getAddressLine2());
        
        return dto;
    }
}