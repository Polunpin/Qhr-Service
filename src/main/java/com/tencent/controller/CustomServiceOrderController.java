package com.tencent.controller;

import com.tencent.config.ApiResponse;
import com.tencent.dto.AdvanceStageRequest;
import com.tencent.dto.AssignStaffRequest;
import com.tencent.dto.UpdateSettleStatusRequest;
import com.tencent.dto.UpdateStringStatusRequest;
import com.tencent.model.CustomServiceOrder;
import com.tencent.service.CustomServiceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.tencent.config.ApiAssert;
import com.tencent.config.ApiCode;
import com.tencent.config.PageBounds;
import com.tencent.config.PageResult;

@RestController
@RequestMapping("/api/custom-service-orders")
public class CustomServiceOrderController {

  private final CustomServiceOrderService orderService;

  public CustomServiceOrderController(@Autowired CustomServiceOrderService orderService) {
    this.orderService = orderService;
  }

  /** 查询订单详情 */
  @GetMapping("/{id}")
  public ApiResponse getById(@PathVariable Long id) {
    CustomServiceOrder order = orderService.getById(id);
    ApiAssert.notNull(order, ApiCode.NOT_FOUND, "订单不存在");
    return ApiResponse.ok(order);
  }

  /** 分页查询订单列表 */
  @GetMapping("/list")
  public ApiResponse list(@RequestParam(required = false) Long enterpriseId,
                          @RequestParam(required = false) Long staffId,
                          @RequestParam(required = false) String serviceStatus,
                          @RequestParam(required = false) String currentStage,
                          @RequestParam(required = false) String settleStatus,
                          @RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer size) {
    PageBounds bounds = PageBounds.of(page, size);
    List<CustomServiceOrder> orders = orderService.list(enterpriseId, staffId, serviceStatus, currentStage, settleStatus,
        bounds.offset(), bounds.size());
    long total = orderService.count(enterpriseId, staffId, serviceStatus, currentStage, settleStatus);
    return ApiResponse.ok(PageResult.of(orders, total, bounds.page(), bounds.size()));
  }

  /** 创建订单 */
  @PostMapping
  public ApiResponse create(@RequestBody CustomServiceOrder order) {
    Long id = orderService.create(order);
    return ApiResponse.ok(id);
  }

  /** 更新订单 */
  @PutMapping("/{id}")
  public ApiResponse update(@PathVariable Long id, @RequestBody CustomServiceOrder order) {
    ApiAssert.isTrue(orderService.update(order.withId(id)), ApiCode.NOT_FOUND, "订单不存在");
    return ApiResponse.ok(true);
  }

  /** 删除订单 */
  @DeleteMapping("/{id}")
  public ApiResponse delete(@PathVariable Long id) {
    ApiAssert.isTrue(orderService.delete(id), ApiCode.NOT_FOUND, "订单不存在");
    return ApiResponse.ok(true);
  }

  /** 指派订单负责人 */
  @PostMapping("/{id}/assign")
  public ApiResponse assignStaff(@PathVariable Long id, @RequestBody AssignStaffRequest request) {
    ApiAssert.isTrue(orderService.assignStaff(id, request.staffId()), ApiCode.NOT_FOUND, "订单不存在");
    return ApiResponse.ok(true);
  }

  /** 更新订单服务状态 */
  @PostMapping("/{id}/service-status")
  public ApiResponse updateServiceStatus(@PathVariable Long id,
                                         @RequestBody UpdateStringStatusRequest request) {
    ApiAssert.isTrue(orderService.updateServiceStatus(id, request.status()), ApiCode.NOT_FOUND, "订单不存在");
    return ApiResponse.ok(true);
  }

  /** 更新订单结算状态 */
  @PostMapping("/{id}/settle-status")
  public ApiResponse updateSettleStatus(@PathVariable Long id,
                                        @RequestBody UpdateSettleStatusRequest request) {
    ApiAssert.isTrue(orderService.updateSettleStatus(id, request.settleStatus()),
        ApiCode.NOT_FOUND, "订单不存在");
    return ApiResponse.ok(true);
  }

  /** 推进订单阶段并写入日志 */
  @PostMapping("/{id}/stage")
  public ApiResponse advanceStage(@PathVariable Long id, @RequestBody AdvanceStageRequest request) {
    boolean ok = orderService.advanceStage(id, request.postStage(), request.serviceStatus(),
        request.remark(), request.operatorType(), request.operatorId());
    ApiAssert.isTrue(ok, ApiCode.NOT_FOUND, "订单不存在");
    return ApiResponse.ok(true);
  }
}
