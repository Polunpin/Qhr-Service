package com.tencent.service;

import com.tencent.model.FinancingIntention;

import java.util.List;

public interface FinancingIntentionService {

  FinancingIntention getById(Long id);

  FinancingIntention getByApplicationNo(String applicationNo);

  Long create(FinancingIntention intention);

  boolean update(FinancingIntention intention);

  boolean delete(Long id);

  boolean updateStatus(Long id, String status, String refusalReason);

  boolean updateTargetProduct(Long id, Long targetProductId);

  List<FinancingIntention> list(Long enterpriseId, Long userId, String status, Integer offset, Integer size);

  long count(Long enterpriseId, Long userId, String status);
}
