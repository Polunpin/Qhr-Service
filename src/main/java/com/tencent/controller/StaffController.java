package com.tencent.controller;

import com.tencent.config.ApiResponse;
import com.tencent.dto.UpdateStatusRequest;
import com.tencent.model.Staff;
import com.tencent.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.tencent.config.ApiCode;
import com.tencent.config.ApiException;
import com.tencent.config.PageRequest;
import com.tencent.config.PageResult;

@RestController
@RequestMapping("/api/staffs")
public class StaffController {

  private final StaffService staffService;

  public StaffController(@Autowired StaffService staffService) {
    this.staffService = staffService;
  }

  @GetMapping("/{id}")
  public ApiResponse getById(@PathVariable Long id) {
    Staff staff = staffService.getById(id);
    if (staff == null) {
      throw new ApiException(ApiCode.NOT_FOUND, "员工不存在");
    }
    return ApiResponse.ok(staff);
  }

  @GetMapping("/list")
  public ApiResponse list(@RequestParam(required = false) String role,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) String department,
                          @RequestParam(required = false) String mobile,
                          @RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer size) {
    int safePage = PageRequest.normalizePage(page);
    int safeSize = PageRequest.normalizeSize(size);
    int offset = PageRequest.offset(safePage, safeSize);
    List<Staff> staffs = staffService.list(role, status, department, mobile, offset, safeSize);
    long total = staffService.count(role, status, department, mobile);
    return ApiResponse.ok(PageResult.of(staffs, total, safePage, safeSize));
  }

  @PostMapping
  public ApiResponse create(@RequestBody Staff staff) {
    Long id = staffService.create(staff);
    return ApiResponse.ok(id);
  }

  @PutMapping("/{id}")
  public ApiResponse update(@PathVariable Long id, @RequestBody Staff staff) {
    staff.setId(id);
    if (!staffService.update(staff)) {
      throw new ApiException(ApiCode.NOT_FOUND, "员工不存在");
    }
    return ApiResponse.ok(true);
  }

  @DeleteMapping("/{id}")
  public ApiResponse delete(@PathVariable Long id) {
    if (!staffService.delete(id)) {
      throw new ApiException(ApiCode.NOT_FOUND, "员工不存在");
    }
    return ApiResponse.ok(true);
  }

  @PostMapping("/{id}/status")
  public ApiResponse updateStatus(@PathVariable Long id, @RequestBody UpdateStatusRequest request) {
    if (!staffService.updateStatus(id, request.getStatus())) {
      throw new ApiException(ApiCode.NOT_FOUND, "员工不存在");
    }
    return ApiResponse.ok(true);
  }
}
