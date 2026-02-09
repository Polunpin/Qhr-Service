package com.tencent.service;

import com.tencent.model.Enterprise;
import com.tencent.model.User;

import java.util.List;

public interface UserService {

  User getById(Long id);

  User getByOpenid(String openid);

  Long create(User user);

  boolean update(User user);

  boolean delete(Long id);

  boolean updateStatus(Long id, Integer status);

  List<User> list(Integer status, String mobile, String realName, Integer offset, Integer size);

  long count(Integer status, String mobile, String realName);

  boolean bindEnterprise(Long userId, Long enterpriseId, String role);

  boolean unbindEnterprise(Long userId, Long enterpriseId);

  List<Enterprise> listEnterprises(Long userId, Integer offset, Integer size);

  long countEnterprises(Long userId);

  List<User> listUsersByEnterprise(Long enterpriseId, Integer offset, Integer size);

  long countUsersByEnterprise(Long enterpriseId);
}
