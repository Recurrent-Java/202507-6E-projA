package com.example.springlesson.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("/css/**", "/images/**", "/js/**", "/storage/**").permitAll() // すべてのユーザーにアクセスを許可するURL
            .requestMatchers("/").permitAll()
            .requestMatchers("/product/**").permitAll()
            .requestMatchers("/cart/**").permitAll()
            .requestMatchers("/purchase/**").permitAll()
            .anyRequest().authenticated() // 上記以外のURLはログインが必要（会員または管理者のどちらでもOK）
        )
        .formLogin((form) -> form
            .loginPage("/login") // ログインページのURL
            .loginProcessingUrl("/login") // ログインフォームの送信先URL
            .usernameParameter("login")  // ★これが重要！
            .passwordParameter("password")
            .defaultSuccessUrl("/?loggedIn=true", true) // ログイン成功時のリダイレクト先URL
            .failureUrl("/login?error") // ログイン失敗時のリダイレクト先URL
            .permitAll())
        .logout((logout) -> logout
            .logoutSuccessUrl("/?loggedOut=true") // ログアウト時のリダイレクト先URL
            .permitAll());

    return http.build();
  }
  // パスワードの暗号化を無視する(テスト用)
  @Bean
  public PasswordEncoder passwordEncoder() {
      return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
  }
//  // 本番用
//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
}
