package com.tencent.dto;

import lombok.Data;

@Data
public class UpdateIntentionStatusRequest {

  private String status;

  private String refusalReason;
}
