package com.tencent.service;

import com.tencent.model.MatchRecord;

import java.util.List;

public interface MatchRecordService {

  MatchRecord getById(Long id);

  MatchRecord getByMatchNo(String matchNo);

  Long create(MatchRecord record);

  boolean update(MatchRecord record);

  boolean delete(Long id);

  boolean updateStatus(Long id, String status);

  List<MatchRecord> list(Long enterpriseId, Long intentionId, String status, Integer offset, Integer size);

  long count(Long enterpriseId, Long intentionId, String status);
}
