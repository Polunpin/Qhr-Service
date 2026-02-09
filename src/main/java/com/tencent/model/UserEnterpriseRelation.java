package com.tencent.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserEnterpriseRelation implements Serializable {

  private Long id;

  private Long enterpriseId;

  private Long userId;

  private String role;

  private LocalDateTime createdAt;
}
