package com.example.springlesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springlesson.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
  /**
   * ログインID（メールアドレス）で会員情報を検索
   */
  Member findByEmail(String email);

  /**
   * 指定された会員IDが存在するかどうかを確認
   */
  boolean existsByEmail(String email);
}