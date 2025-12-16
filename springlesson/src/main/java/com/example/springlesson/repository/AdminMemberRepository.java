package com.example.springlesson.repository;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMemberRepository extends JpaRepository<Member, Long> {
    
    /**
     * 会員一覧を取得（管理者用）
     * すべての会員を取得（status関係なく）
     */
    List<Member> findAllByOrderByRegistrationDateDesc();
    
    /**
     * 会員IDで検索
     */
    Optional<Member> findByMemberId(Long memberId);
    
    /**
     * メールアドレスで検索
     */
    Optional<Member> findByEmail(String email);
    
    /**
     * 氏名で部分一致検索
     */
    List<Member> findByFullNameContainingOrderByRegistrationDateDesc(String fullName);
    
    /**
     * ステータスで検索（1:有効, 0:退会）
     */
    List<Member> findByStatusOrderByRegistrationDateDesc(Integer status);
}