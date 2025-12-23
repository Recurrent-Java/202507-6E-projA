package com.example.springlesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springlesson.entity.AllergenMaster;

@Repository
public interface AllergenMasterRepository extends JpaRepository<AllergenMaster, String> {
  // 主キーが allergen_code (VARCHAR) なので、第2引数に String を指定しています
}