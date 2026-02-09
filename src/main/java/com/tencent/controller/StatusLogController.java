package com.tencent.controller;

import com.tencent.config.ApiResponse;
import com.tencent.model.StatusLog;
import com.tencent.service.StatusLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.tencent.config.PageRequest;
import com.tencent.config.PageResult;
import com.tencent.config.ApiCode;
import com.tencent.config.ApiException;

@RestController
@RequestMapping("/api/status-logs")
public class StatusLogController {

  private final StatusLogService statusLogService;

  public StatusLogController(@Autowired StatusLogService statusLogService) {
    this.statusLogService = statusLogService;
  }

  @GetMapping("/{id}")
  public ApiResponse getById(@PathVariable Long id) {
    StatusLog log = statusLogService.getById(id);
    if (log == null) {
      throw new ApiException(ApiCode.NOT_FOUND, "日志不存在");
    }
    return ApiResponse.ok(log);
  }

  @GetMapping("/order/{orderId}")
  public ApiResponse listByOrder(@PathVariable Long orderId,
                                 @RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) Integer size) {
    int safePage = PageRequest.normalizePage(page);
    int safeSize = PageRequest.normalizeSize(size);
    int offset = PageRequest.offset(safePage, safeSize);
    List<StatusLog> logs = statusLogService.listByOrderId(orderId, offset, safeSize);
    long total = statusLogService.countByOrderId(orderId);
    return ApiResponse.ok(PageResult.of(logs, total, safePage, safeSize));
  }

  @PostMapping
  public ApiResponse create(@RequestBody StatusLog log) {
    Long id = statusLogService.create(log);
    return ApiResponse.ok(id);
  }
}
