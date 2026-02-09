package com.tencent.controller;

import com.tencent.config.ApiResponse;
import com.tencent.dto.UpdateMatchStatusRequest;
import com.tencent.dto.UpdateProfileDataRequest;
import com.tencent.model.Enterprise;
import com.tencent.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.tencent.config.ApiAssert;
import com.tencent.config.ApiCode;
import com.tencent.config.PageBounds;
import com.tencent.config.PageResult;

@RestController
@RequestMapping("/api/enterprises")
public class EnterpriseController {

  private final EnterpriseService enterpriseService;

  public EnterpriseController(@Autowired EnterpriseService enterpriseService) {
    this.enterpriseService = enterpriseService;
  }

  /** 查询企业详情 */
  @GetMapping("/{id}")
  public ApiResponse getById(@PathVariable Long id) {
    Enterprise enterprise = enterpriseService.getById(id);
    ApiAssert.notNull(enterprise, ApiCode.NOT_FOUND, "企业不存在");
    return ApiResponse.ok(enterprise);
  }

  /** 分页查询企业列表 */
  @GetMapping("/list")
  public ApiResponse list(@RequestParam(required = false) String matchStatus,
                          @RequestParam(required = false) String industry,
                          @RequestParam(required = false) String regionCode,
                          @RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer size) {
    PageBounds bounds = PageBounds.of(page, size);
    List<Enterprise> enterprises = enterpriseService.list(matchStatus, industry, regionCode, bounds.offset(), bounds.size());
    long total = enterpriseService.count(matchStatus, industry, regionCode);
    return ApiResponse.ok(PageResult.of(enterprises, total, bounds.page(), bounds.size()));
  }

  /** 分页查询用户关联企业 */
  @GetMapping("/user/{userId}")
  public ApiResponse listByUser(@PathVariable Long userId,
                                @RequestParam(required = false) Integer page,
                                @RequestParam(required = false) Integer size) {
    PageBounds bounds = PageBounds.of(page, size);
    List<Enterprise> enterprises = enterpriseService.listByUserId(userId, bounds.offset(), bounds.size());
    long total = enterpriseService.countByUserId(userId);
    return ApiResponse.ok(PageResult.of(enterprises, total, bounds.page(), bounds.size()));
  }

  /** 创建企业 */
  @PostMapping
  public ApiResponse create(@RequestBody Enterprise enterprise) {
    Long id = enterpriseService.create(enterprise);
    return ApiResponse.ok(id);
  }

  /** 更新企业 */
  @PutMapping("/{id}")
  public ApiResponse update(@PathVariable Long id, @RequestBody Enterprise enterprise) {
    ApiAssert.isTrue(enterpriseService.update(enterprise.withId(id)), ApiCode.NOT_FOUND, "企业不存在");
    return ApiResponse.ok(true);
  }

  /** 删除企业 */
  @DeleteMapping("/{id}")
  public ApiResponse delete(@PathVariable Long id) {
    ApiAssert.isTrue(enterpriseService.delete(id), ApiCode.NOT_FOUND, "企业不存在");
    return ApiResponse.ok(true);
  }

  /** 更新企业匹配状态 */
  @PostMapping("/{id}/match-status")
  public ApiResponse updateMatchStatus(@PathVariable Long id,
                                       @RequestBody UpdateMatchStatusRequest request) {
    ApiAssert.isTrue(enterpriseService.updateMatchStatus(id, request.matchStatus()),
        ApiCode.NOT_FOUND, "企业不存在");
    return ApiResponse.ok(true);
  }

  /** 更新企业画像数据 */
  @PostMapping("/{id}/profile-data")
  public ApiResponse updateProfileData(@PathVariable Long id,
                                       @RequestBody UpdateProfileDataRequest request) {
    ApiAssert.isTrue(enterpriseService.updateProfileData(id, request.profileData()),
        ApiCode.NOT_FOUND, "企业不存在");
    return ApiResponse.ok(true);
  }
}
