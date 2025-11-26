package com.example.springlesson.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springlesson.entity.Purchase;
import com.example.springlesson.form.Item;
import com.example.springlesson.repository.PurchaseRepository;

@Service
public class PurchaseService {
  private final PurchaseRepository purchaseRepository;

  public PurchaseService(PurchaseRepository purchaseRepository) {
    this.purchaseRepository = purchaseRepository;
  }

  // 購入処理
  @Transactional(rollbackFor = Exception.class)
  public void save(List<Item> cart, String name, String address) throws Exception {
    try {
      for (Item item : cart) {
        Purchase purchase = new Purchase(
            null,
            item.getProduct().getId(),
            item.getProduct().getName(),
            item.getProduct().getPrice(),
            item.getCount(),
            name,
            address);
        // DBへ登録
        purchaseRepository.save(purchase);
      }
    }catch(DataAccessException dae) {
      throw new Exception("購入処理が失敗しました。<br>" + dae.getMessage());
    }
  }
}
