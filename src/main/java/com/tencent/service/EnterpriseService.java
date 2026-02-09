package com.tencent.service;

import com.tencent.model.Enterprise;

import java.util.List;

public interface EnterpriseService {

  Enterprise getById(Long id);

  Long create(Enterprise enterprise);

  boolean update(Enterprise enterprise);

  boolean delete(Long id);

  boolean updateMatchStatus(Long id, String matchStatus);

  boolean updateProfileData(Long id, String profileData);

  List<Enterprise> list(String matchStatus, String industry, String regionCode, Integer offset, Integer size);

  long count(String matchStatus, String industry, String regionCode);

  List<Enterprise> listByUserId(Long userId, Integer offset, Integer size);

  long countByUserId(Long userId);
}
