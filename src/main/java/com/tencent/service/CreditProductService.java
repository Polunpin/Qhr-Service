package com.tencent.service;

import com.tencent.model.CreditProduct;

import java.math.BigDecimal;
import java.util.List;

public interface CreditProductService {

  CreditProduct getById(Long id);

  Long create(CreditProduct product);

  boolean update(CreditProduct product);

  boolean delete(Long id);

  boolean updateStatus(Long id, Integer status);

  List<CreditProduct> list(Integer status, String productType, String bankName, Integer offset, Integer size);

  long count(Integer status, String productType, String bankName);

  List<CreditProduct> findEligibleProducts(BigDecimal expectedAmount,
                                           Integer expectedTerm,
                                           String regionCode,
                                           String productType,
                                           Integer offset,
                                           Integer size);

  long countEligibleProducts(BigDecimal expectedAmount,
                             Integer expectedTerm,
                             String regionCode,
                             String productType);
}
