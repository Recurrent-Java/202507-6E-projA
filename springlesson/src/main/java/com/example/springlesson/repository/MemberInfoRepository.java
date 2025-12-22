package com.example.springlesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springlesson.entity.MemberInfo;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Integer> {
}