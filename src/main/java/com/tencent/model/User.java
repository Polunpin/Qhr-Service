package com.tencent.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {

  private Long id;

  private String openid;

  private String unionid;

  private String mobile;

  private String realName;

  private Integer status;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
