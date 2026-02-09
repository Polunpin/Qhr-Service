package com.tencent.controller;

import com.tencent.config.ApiResponse;
import com.tencent.model.StatusLog;
import com.tencent.service.StatusLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.tencent.config.ApiAssert;
import com.tencent.config.ApiCode;
import com.tencent.config.PageBounds;
import com.tencent.config.PageResult;

@RestController
@RequestMapping("/api/status-logs")
public class StatusLogController {

  private final StatusLogService statusLogService;

  public StatusLogController(@Autowired StatusLogService statusLogService) {
    this.statusLogService = statusLogService;
  }

  /** 查询日志详情 */
  @GetMapping("/{id}")
  public ApiResponse getById(@PathVariable Long id) {
    StatusLog log = statusLogService.getById(id);
    ApiAssert.notNull(log, ApiCode.NOT_FOUND, "日志不存在");
    return ApiResponse.ok(log);
  }

  /** 分页查询订单日志 */
  @GetMapping("/order/{orderId}")
  public ApiResponse listByOrder(@PathVariable Long orderId,
                                 @RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) Integer size) {
    PageBounds bounds = PageBounds.of(page, size);
    List<StatusLog> logs = statusLogService.listByOrderId(orderId, bounds.offset(), bounds.size());
    long total = statusLogService.countByOrderId(orderId);
    return ApiResponse.ok(PageResult.of(logs, total, bounds.page(), bounds.size()));
  }

  /** 创建日志 */
  @PostMapping
  public ApiResponse create(@RequestBody StatusLog log) {
    Long id = statusLogService.create(log);
    return ApiResponse.ok(id);
  }
}
