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
    // 姓名を結合して full_name に入れる
    member.setFullName(form.getLastName() + " " + form.getFirstName());
    member.setEmail(form.getEmail());
    member.setPasswordHash(form.getPassword());
    member.setPostalCode(form.getPostalCode());
    member.setPrefecture(form.getPrefecture());
    // 市区町村と番地を結合して address_line1 に入れる
    member.setAddressLine1(form.getCity() + form.getAddress1());
    member.setAddressLine2(form.getAddress2());
    member.setPhoneNumber(form.getPhone());
    member.setRegistrationDate(java.time.LocalDateTime.now());

    memberRepository.save(member);
  }

  /**
   * ログインIDがすでに存在するかチェック
   */
  public boolean isLoginIdAlreadyExists(String loginId) {
    return memberRepository.existsByEmail(loginId);
  }
}