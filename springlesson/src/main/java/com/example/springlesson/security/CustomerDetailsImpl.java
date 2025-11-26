package com.example.springlesson.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.springlesson.entity.Customer;

public class CustomerDetailsImpl implements UserDetails {
  private final Customer customer;
  private final Collection<GrantedAuthority> authorities;
  
  // コンストラクターインジェクション
  public CustomerDetailsImpl(Customer customer, Collection<GrantedAuthority> authorities) {
    this.customer = customer;
    this.authorities = authorities;
  }
  
  public Customer getCustomer() {
    return this.customer;
  }
  
  @Override
  public String getUsername() {
    return this.customer.getLogin();
  }
  
  @Override
  public String getPassword() {
    return this.customer.getPassword();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

}
