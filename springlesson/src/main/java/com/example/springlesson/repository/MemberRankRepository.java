package com.example.springlesson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springlesson.entity.MemberRank;

@Repository
public interface MemberRankRepository extends JpaRepository<MemberRank, String> {
  // 判定のために、必要金額（THRESHOLD_AMOUNT）の昇順で全ランクを取得するメソッド
  List<MemberRank> findAllByOrderByThresholdAmountAsc();
}