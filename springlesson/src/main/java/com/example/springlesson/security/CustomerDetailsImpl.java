package com.example.springlesson.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.springlesson.entity.Member; // ★修正: Customer -> Member

public class CustomerDetailsImpl implements UserDetails {
  private Member member; // ★修正: Customer -> Member
  private final Collection<GrantedAuthority> authorities;

  // コンストラクターインジェクション
  public CustomerDetailsImpl(Member member, Collection<GrantedAuthority> authorities) { // ★修正: Customer -> Member
    this.member = member; // ★修正: customer -> member
    this.authorities = authorities;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public Member getMember() { // ★修正: getCustomer -> getMember
    return this.member; // ★修正: customer -> member
  }

  @Override
  public String getUsername() {
    return this.member.getEmail(); // ★修正: customer -> member
  }

  @Override
  public String getPassword() {
    return this.member.getPasswordHash(); // ★修正: customer -> member
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public boolean isEnabled() {
    // status が 1 の時だけ true を返す
    return this.member.getStatus() != null && this.member.getStatus() == 1;
  }

  // 以下のメソッドも UserDetails インターフェースの規約上必要なので、
  // まだ書いていなければ false を返すように追加してください
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
}