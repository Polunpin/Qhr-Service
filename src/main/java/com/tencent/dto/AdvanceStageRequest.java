package com.tencent.dto;

import lombok.Data;

@Data
public class AdvanceStageRequest {

  private String postStage;

  private String serviceStatus;

  private String remark;

  private String operatorType;

  private Long operatorId;
}
