package com.tencent.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Staff implements Serializable {

  private Long id;

  private String openid;

  private String realName;

  private String mobile;

  private String role;

  private String department;

  private Integer status;

  private LocalDateTime lastUpdateAt;

  private LocalDateTime createdAt;
}
