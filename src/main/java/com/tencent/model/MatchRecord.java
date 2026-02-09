package com.tencent.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MatchRecord implements Serializable {

  private Long id;

  private String matchNo;

  private Long enterpriseId;

  private Long intentionId;

  private String productIds;

  private BigDecimal matchScore;

  private String riskType;

  private String riskLevel;

  private String status;

  private LocalDateTime createdAt;
}
