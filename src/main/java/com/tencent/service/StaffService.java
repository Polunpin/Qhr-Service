package com.tencent.service;

import com.tencent.model.Staff;

import java.util.List;

public interface StaffService {

  Staff getById(Long id);

  Staff getByMobile(String mobile);

  Long create(Staff staff);

  boolean update(Staff staff);

  boolean delete(Long id);

  boolean updateStatus(Long id, Integer status);

  List<Staff> list(String role, Integer status, String department, String mobile, Integer offset, Integer size);

  long count(String role, Integer status, String department, String mobile);
}
