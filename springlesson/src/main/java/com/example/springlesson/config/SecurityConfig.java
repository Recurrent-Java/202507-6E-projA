package com.example.springlesson.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.springlesson.security.CustomerDetailsImpl;
import com.example.springlesson.service.CartService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final CartService cartService;

  public SecurityConfig(CartService cartService) {
    this.cartService = cartService;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authorize -> authorize
            // 全ての静的リソースと、共通レイアウトのパスを完全に許可
            .requestMatchers("/css/**", "/js/**", "/images/**", "/storage/**", "/comlayout/**").permitAll()
            // TOP、商品、ログイン、登録、カートに関わる全てのURLを許可
            .requestMatchers("/", "/index", "/login", "/register/**", "/product/**", "/cart/**").permitAll()
            // それ以外（管理画面など）のみ認証を必要とする
            .anyRequest().permitAll() // ★デバッグのため、一度ここを permitAll() にしてください
        )
        .formLogin(login -> login
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .successHandler(loginSuccessHandler())
            .failureUrl("/login?error")
            .permitAll())
        .logout(logout -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login")
            .deleteCookies("JSESSIONID")
            .invalidateHttpSession(true)
            .permitAll());

    return http.build();
  }

  /**
   * ログイン成功時にセッションカートをユーザーカートに移行するハンドラー
   */
  @Bean
  public AuthenticationSuccessHandler loginSuccessHandler() {
    return (request, response, authentication) -> {
      // ログインしたユーザー情報を取得
      CustomerDetailsImpl userDetails = (CustomerDetailsImpl) authentication.getPrincipal();
      Long userId = userDetails.getMember().getMemberId();
      String sessionId = request.getSession().getId();

      // セッションカートをユーザーカートに移行
      try {
        cartService.transferCartToUser(sessionId, userId);
      } catch (Exception e) {
        // エラーが発生しても、ログイン自体は成功させる
        e.printStackTrace();
      }

      // トップページにリダイレクト
      response.sendRedirect("/");
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }
}
