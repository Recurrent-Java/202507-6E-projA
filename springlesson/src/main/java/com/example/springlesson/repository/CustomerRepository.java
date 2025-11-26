package com.example.springlesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springlesson.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
  // ログイン名で検索
  public Customer findByLogin(String login);
}
