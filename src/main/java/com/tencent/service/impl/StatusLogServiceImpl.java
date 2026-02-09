package com.tencent.service.impl;

import com.tencent.dao.StatusLogsMapper;
import com.tencent.model.StatusLog;
import com.tencent.service.StatusLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusLogServiceImpl implements StatusLogService {

  private final StatusLogsMapper statusLogsMapper;

  public StatusLogServiceImpl(@Autowired StatusLogsMapper statusLogsMapper) {
    this.statusLogsMapper = statusLogsMapper;
  }

  @Override
  public Long create(StatusLog log) {
    statusLogsMapper.insert(log);
    return log.getId();
  }

  @Override
  public StatusLog getById(Long id) {
    return statusLogsMapper.getById(id);
  }

  @Override
  public List<StatusLog> listByOrderId(Long orderId, Integer offset, Integer size) {
    return statusLogsMapper.listByOrderId(orderId, offset, size);
  }

  @Override
  public long countByOrderId(Long orderId) {
    return statusLogsMapper.countByOrderId(orderId);
  }
}
