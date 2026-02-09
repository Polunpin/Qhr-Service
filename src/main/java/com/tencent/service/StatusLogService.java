package com.tencent.service;

import com.tencent.model.StatusLog;

import java.util.List;

public interface StatusLogService {

  Long create(StatusLog log);

  StatusLog getById(Long id);

  List<StatusLog> listByOrderId(Long orderId, Integer offset, Integer size);

  long countByOrderId(Long orderId);
}
