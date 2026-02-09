package com.tencent.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 企业实体。
 */
public record Enterprise(Long id,
                         String fullName,
                         String creditCode,
                         String industry,
                         String taxRating,
                         String regionCode,
                         BigDecimal annualTurnover,
                         BigDecimal annualTaxAmount,
                         BigDecimal existingLoanBalance,
                         String matchStatus,
                         String profileData,
                         LocalDateTime createdAt,
                         LocalDateTime updatedAt) implements Serializable, WithId<Enterprise> {

  /** 复制并替换id。 */
  public Enterprise withId(Long id) {
    return new Enterprise(id, fullName, creditCode, industry, taxRating, regionCode,
        annualTurnover, annualTaxAmount, existingLoanBalance, matchStatus, profileData,
        createdAt, updatedAt);
  }
}
