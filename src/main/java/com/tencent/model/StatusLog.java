package com.tencent.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class StatusLog implements Serializable {

  private Long id;

  private Long orderId;

  private String operatorType;

  private Long operatorId;

  private String preStage;

  private String postStage;

  private String remark;

  private LocalDateTime createdAt;
}
