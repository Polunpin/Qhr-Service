package com.tencent.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Enterprise implements Serializable {

  private Long id;

  private String fullName;

  private String creditCode;

  private String industry;

  private String taxRating;

  private String regionCode;

  private BigDecimal annualTurnover;

  private BigDecimal annualTaxAmount;

  private BigDecimal existingLoanBalance;

  private String matchStatus;

  private String profileData;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
