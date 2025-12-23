package com.example.springlesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springlesson.entity.AllergySetting;

public interface AllergySettingRepository extends JpaRepository<AllergySetting, Long> {
}
