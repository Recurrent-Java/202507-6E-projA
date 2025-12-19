package com.example.springlesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springlesson.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
  /**
   * ログインID（メールアドレス）で会員情報を検索
   */
  Member findByLogin(String login);

  /**
   * 指定された会員IDが存在するかどうかを確認
   */
  boolean existsByLogin(String login);
}