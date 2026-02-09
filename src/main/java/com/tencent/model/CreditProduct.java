package com.tencent.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreditProduct implements Serializable {

  private Long id;

  private String bankName;

  private String productName;

  private String productType;

  private BigDecimal minAmount;

  private BigDecimal maxAmount;

  private String interestRateRange;

  private Integer loanTerm;

  private String repaymentMethod;

  private String serviceArea;

  private String criteriaJson;

  private Integer status;

  private BigDecimal successRate;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
