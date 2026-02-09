package com.tencent.controller;

import com.tencent.config.ApiResponse;
import com.tencent.dto.BindEnterpriseRequest;
import com.tencent.dto.UpdateStatusRequest;
import com.tencent.model.Enterprise;
import com.tencent.model.User;
import com.tencent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.tencent.config.ApiCode;
import com.tencent.config.ApiException;
import com.tencent.config.PageRequest;
import com.tencent.config.PageResult;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(@Autowired UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public ApiResponse getById(@PathVariable Long id) {
    User user = userService.getById(id);
    if (user == null) {
      throw new ApiException(ApiCode.NOT_FOUND, "用户不存在");
    }
    return ApiResponse.ok(user);
  }

  @GetMapping("/list")
  public ApiResponse list(@RequestParam(required = false) Integer status,
                          @RequestParam(required = false) String mobile,
                          @RequestParam(required = false) String realName,
                          @RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer size) {
    int safePage = PageRequest.normalizePage(page);
    int safeSize = PageRequest.normalizeSize(size);
    int offset = PageRequest.offset(safePage, safeSize);
    List<User> users = userService.list(status, mobile, realName, offset, safeSize);
    long total = userService.count(status, mobile, realName);
    return ApiResponse.ok(PageResult.of(users, total, safePage, safeSize));
  }

  @PostMapping
  public ApiResponse create(@RequestBody User user) {
    Long id = userService.create(user);
    return ApiResponse.ok(id);
  }

  @PutMapping("/{id}")
  public ApiResponse update(@PathVariable Long id, @RequestBody User user) {
    user.setId(id);
    if (!userService.update(user)) {
      throw new ApiException(ApiCode.NOT_FOUND, "用户不存在");
    }
    return ApiResponse.ok(true);
  }

  @DeleteMapping("/{id}")
  public ApiResponse delete(@PathVariable Long id) {
    if (!userService.delete(id)) {
      throw new ApiException(ApiCode.NOT_FOUND, "用户不存在");
    }
    return ApiResponse.ok(true);
  }

  @PostMapping("/{id}/status")
  public ApiResponse updateStatus(@PathVariable Long id, @RequestBody UpdateStatusRequest request) {
    if (!userService.updateStatus(id, request.getStatus())) {
      throw new ApiException(ApiCode.NOT_FOUND, "用户不存在");
    }
    return ApiResponse.ok(true);
  }

  @PostMapping("/{id}/enterprises/{enterpriseId}")
  public ApiResponse bindEnterprise(@PathVariable Long id,
                                    @PathVariable Long enterpriseId,
                                    @RequestBody(required = false) BindEnterpriseRequest request) {
    String role = request == null ? null : request.getRole();
    return ApiResponse.ok(userService.bindEnterprise(id, enterpriseId, role));
  }

  @DeleteMapping("/{id}/enterprises/{enterpriseId}")
  public ApiResponse unbindEnterprise(@PathVariable Long id,
                                      @PathVariable Long enterpriseId) {
    return ApiResponse.ok(userService.unbindEnterprise(id, enterpriseId));
  }

  @GetMapping("/{id}/enterprises")
  public ApiResponse listEnterprises(@PathVariable Long id,
                                     @RequestParam(required = false) Integer page,
                                     @RequestParam(required = false) Integer size) {
    int safePage = PageRequest.normalizePage(page);
    int safeSize = PageRequest.normalizeSize(size);
    int offset = PageRequest.offset(safePage, safeSize);
    List<Enterprise> enterprises = userService.listEnterprises(id, offset, safeSize);
    long total = userService.countEnterprises(id);
    return ApiResponse.ok(PageResult.of(enterprises, total, safePage, safeSize));
  }

  @GetMapping("/enterprise/{enterpriseId}/users")
  public ApiResponse listUsersByEnterprise(@PathVariable Long enterpriseId,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size) {
    int safePage = PageRequest.normalizePage(page);
    int safeSize = PageRequest.normalizeSize(size);
    int offset = PageRequest.offset(safePage, safeSize);
    List<User> users = userService.listUsersByEnterprise(enterpriseId, offset, safeSize);
    long total = userService.countUsersByEnterprise(enterpriseId);
    return ApiResponse.ok(PageResult.of(users, total, safePage, safeSize));
  }
}
