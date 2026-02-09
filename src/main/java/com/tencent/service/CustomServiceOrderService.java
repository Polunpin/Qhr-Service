package com.tencent.service;

import com.tencent.model.CustomServiceOrder;

import java.util.List;

public interface CustomServiceOrderService {

  CustomServiceOrder getById(Long id);

  Long create(CustomServiceOrder order);

  boolean update(CustomServiceOrder order);

  boolean delete(Long id);

  boolean assignStaff(Long id, Long staffId);

  boolean updateServiceStatus(Long id, String serviceStatus);

  boolean updateSettleStatus(Long id, String settleStatus);

  boolean advanceStage(Long id, String postStage, String serviceStatus,
                       String remark, String operatorType, Long operatorId);

  List<CustomServiceOrder> list(Long enterpriseId, Long staffId,
                                String serviceStatus, String currentStage, String settleStatus,
                                Integer offset, Integer size);

  long count(Long enterpriseId, Long staffId,
             String serviceStatus, String currentStage, String settleStatus);
}
