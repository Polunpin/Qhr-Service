package com.tencent.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FinancingIntention implements Serializable {

  private Long id;

  private String applicationNo;

  private Long enterpriseId;

  private Long userId;

  private BigDecimal expectedAmount;

  private Integer expectedTerm;

  private String purpose;

  private String repaymentSource;

  private String guaranteeType;

  private Long targetProductId;

  private String contactMobile;

  private String status;

  private String refusalReason;

  private Integer urgencyLevel;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
