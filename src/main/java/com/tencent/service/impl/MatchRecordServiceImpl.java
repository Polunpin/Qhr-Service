package com.tencent.service.impl;

import com.tencent.dao.MatchRecordsMapper;
import com.tencent.model.MatchRecord;
import com.tencent.service.MatchRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MatchRecordServiceImpl implements MatchRecordService {

  private final MatchRecordsMapper matchRecordsMapper;

  public MatchRecordServiceImpl(@Autowired MatchRecordsMapper matchRecordsMapper) {
    this.matchRecordsMapper = matchRecordsMapper;
  }

  @Override
  public MatchRecord getById(Long id) {
    return matchRecordsMapper.getById(id);
  }

  @Override
  public MatchRecord getByMatchNo(String matchNo) {
    return matchRecordsMapper.getByMatchNo(matchNo);
  }

  @Override
  public Long create(MatchRecord record) {
    MatchRecord toSave = record;
    if (record.matchNo() == null || record.matchNo().trim().isEmpty()) {
      toSave = withMatchNo(record, generateMatchNo());
    }
    matchRecordsMapper.insert(toSave);
    return matchRecordsMapper.lastInsertId();
  }

  @Override
  public boolean update(MatchRecord record) {
    return matchRecordsMapper.update(record) > 0;
  }

  @Override
  public boolean delete(Long id) {
    return matchRecordsMapper.delete(id) > 0;
  }

  @Override
  public boolean updateStatus(Long id, String status) {
    return matchRecordsMapper.updateStatus(id, status) > 0;
  }

  @Override
  public List<MatchRecord> list(Long enterpriseId, Long intentionId, String status, Integer offset, Integer size) {
    return matchRecordsMapper.list(enterpriseId, intentionId, status, offset, size);
  }

  @Override
  public long count(Long enterpriseId, Long intentionId, String status) {
    return matchRecordsMapper.count(enterpriseId, intentionId, status);
  }

  private String generateMatchNo() {
    // 匹配编号: MR + 时间戳 + 随机4位
    String prefix = "MR";
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    int random = ThreadLocalRandom.current().nextInt(10000);
    return prefix + timestamp + String.format("%04d", random);
  }

  private MatchRecord withMatchNo(MatchRecord source, String matchNo) {
    return new MatchRecord(source.id(),
        matchNo,
        source.enterpriseId(),
        source.intentionId(),
        source.productIds(),
        source.matchScore(),
        source.riskType(),
        source.riskLevel(),
        source.status(),
        source.createdAt());
  }
}
