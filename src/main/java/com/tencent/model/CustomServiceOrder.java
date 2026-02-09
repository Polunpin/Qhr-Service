package com.tencent.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CustomServiceOrder implements Serializable {

  private Long id;

  private Long enterpriseId;

  private Long intentionId;

  private Long staffId;

  private String currentStage;

  private String serviceStatus;

  private BigDecimal loanAmount;

  private BigDecimal commissionAmount;

  private BigDecimal serviceCost;

  private String costDetails;

  private String settleStatus;

  private LocalDateTime lastUpdateAt;

  private LocalDateTime createdAt;
}
