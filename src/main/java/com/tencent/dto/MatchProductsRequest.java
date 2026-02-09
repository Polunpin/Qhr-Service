package com.tencent.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MatchProductsRequest {

  private BigDecimal expectedAmount;

  private Integer expectedTerm;

  private String regionCode;

  private String productType;
}
