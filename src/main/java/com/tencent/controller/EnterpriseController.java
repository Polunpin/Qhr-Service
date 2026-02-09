package com.tencent.controller;

import com.tencent.config.ApiResponse;
import com.tencent.dto.UpdateMatchStatusRequest;
import com.tencent.dto.UpdateProfileDataRequest;
import com.tencent.model.Enterprise;
import com.tencent.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.tencent.config.ApiCode;
import com.tencent.config.ApiException;
import com.tencent.config.PageRequest;
import com.tencent.config.PageResult;

@RestController
@RequestMapping("/api/enterprises")
public class EnterpriseController {

  private final EnterpriseService enterpriseService;

  public EnterpriseController(@Autowired EnterpriseService enterpriseService) {
    this.enterpriseService = enterpriseService;
  }

  @GetMapping("/{id}")
  public ApiResponse getById(@PathVariable Long id) {
    Enterprise enterprise = enterpriseService.getById(id);
    if (enterprise == null) {
      throw new ApiException(ApiCode.NOT_FOUND, "企业不存在");
    }
    return ApiResponse.ok(enterprise);
  }

  @GetMapping("/list")
  public ApiResponse list(@RequestParam(required = false) String matchStatus,
                          @RequestParam(required = false) String industry,
                          @RequestParam(required = false) String regionCode,
                          @RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer size) {
    int safePage = PageRequest.normalizePage(page);
    int safeSize = PageRequest.normalizeSize(size);
    int offset = PageRequest.offset(safePage, safeSize);
    List<Enterprise> enterprises = enterpriseService.list(matchStatus, industry, regionCode, offset, safeSize);
    long total = enterpriseService.count(matchStatus, industry, regionCode);
    return ApiResponse.ok(PageResult.of(enterprises, total, safePage, safeSize));
  }

  @GetMapping("/user/{userId}")
  public ApiResponse listByUser(@PathVariable Long userId,
                                @RequestParam(required = false) Integer page,
                                @RequestParam(required = false) Integer size) {
    int safePage = PageRequest.normalizePage(page);
    int safeSize = PageRequest.normalizeSize(size);
    int offset = PageRequest.offset(safePage, safeSize);
    List<Enterprise> enterprises = enterpriseService.listByUserId(userId, offset, safeSize);
    long total = enterpriseService.countByUserId(userId);
    return ApiResponse.ok(PageResult.of(enterprises, total, safePage, safeSize));
  }

  @PostMapping
  public ApiResponse create(@RequestBody Enterprise enterprise) {
    Long id = enterpriseService.create(enterprise);
    return ApiResponse.ok(id);
  }

  @PutMapping("/{id}")
  public ApiResponse update(@PathVariable Long id, @RequestBody Enterprise enterprise) {
    enterprise.setId(id);
    if (!enterpriseService.update(enterprise)) {
      throw new ApiException(ApiCode.NOT_FOUND, "企业不存在");
    }
    return ApiResponse.ok(true);
  }

  @DeleteMapping("/{id}")
  public ApiResponse delete(@PathVariable Long id) {
    if (!enterpriseService.delete(id)) {
      throw new ApiException(ApiCode.NOT_FOUND, "企业不存在");
    }
    return ApiResponse.ok(true);
  }

  @PostMapping("/{id}/match-status")
  public ApiResponse updateMatchStatus(@PathVariable Long id,
                                       @RequestBody UpdateMatchStatusRequest request) {
    if (!enterpriseService.updateMatchStatus(id, request.getMatchStatus())) {
      throw new ApiException(ApiCode.NOT_FOUND, "企业不存在");
    }
    return ApiResponse.ok(true);
  }

  @PostMapping("/{id}/profile-data")
  public ApiResponse updateProfileData(@PathVariable Long id,
                                       @RequestBody UpdateProfileDataRequest request) {
    if (!enterpriseService.updateProfileData(id, request.getProfileData())) {
      throw new ApiException(ApiCode.NOT_FOUND, "企业不存在");
    }
    return ApiResponse.ok(true);
  }
}
