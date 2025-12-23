package com.example.springlesson.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springlesson.entity.Member;
import com.example.springlesson.repository.MemberRepository;

@Service
public class CutomerDetailsServiceImpl implements UserDetailsService {
  private final MemberRepository memberRepository;

  // コンストラクターインジェクション
  public CutomerDetailsServiceImpl(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    try {
      Member member = memberRepository.findByEmail(login);
      if (member == null) {
        throw new UsernameNotFoundException("ユーザーが見つかりません：" + login);
      }
      // 権限(とりあえずここに固定しておきます)
      Collection<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

      // ★修正完了: CustomerDetailsImpl のコンストラクタの型と一致。
      return new CustomerDetailsImpl(member, authorities);
    } catch (Exception e) {
      throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
    }
  }

}