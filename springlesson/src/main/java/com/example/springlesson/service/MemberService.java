package com.example.springlesson.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springlesson.entity.Member;
import com.example.springlesson.form.RegisterForm;
import com.example.springlesson.repository.MemberRepository;

@Service
public class MemberService {
  private final MemberRepository memberRepository;
  // PasswordEncoderのインジェクションも本来は必要

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  /**
   * 新規会員を登録する
   */
  @Transactional
  public void registerNewMember(RegisterForm form) {
    Member member = new Member();

    // FormからEntityへの値のコピー
    member.setLastName(form.getLastName());
    /* ★修正: form.getLogin() -> form.getEmail() (ログインIDとして利用) */
    member.setFirstName(form.getFirstName());
    member.setLogin(form.getEmail());

    // member.setPassword(passwordEncoder.encode(form.getPassword())); // 本来は暗号化
    member.setPassword(form.getPassword()); // テスト用
    member.setPhoneNumber(form.getPhone());

    member.setRole("ROLE_USER");

    memberRepository.save(member);
  }

  /**
   * ログインIDがすでに存在するかチェック
   */
  public boolean isLoginIdAlreadyExists(String loginId) {
    return memberRepository.existsByLogin(loginId);
  }
}