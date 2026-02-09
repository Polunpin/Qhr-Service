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
import com.tencent.config.ApiCode;
import com.tencent.config.ApiException;
import com.tencent.config.PageRequest;
import com.tencent.config.PageResult;

@RestController
@RequestMapping("/api/custom-service-orders")
public class CustomServiceOrderController {

  private final CustomServiceOrderService orderService;

  public CustomServiceOrderController(@Autowired CustomServiceOrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/{id}")
  public ApiResponse getById(@PathVariable Long id) {
    CustomServiceOrder order = orderService.getById(id);
    if (order == null) {
      throw new ApiException(ApiCode.NOT_FOUND, "订单不存在");
    }
    return ApiResponse.ok(order);
  }

  @GetMapping("/list")
  public ApiResponse list(@RequestParam(required = false) Long enterpriseId,
                          @RequestParam(required = false) Long staffId,
                          @RequestParam(required = false) String serviceStatus,
                          @RequestParam(required = false) String currentStage,
                          @RequestParam(required = false) String settleStatus,
                          @RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer size) {
    int safePage = PageRequest.normalizePage(page);
    int safeSize = PageRequest.normalizeSize(size);
    int offset = PageRequest.offset(safePage, safeSize);
    List<CustomServiceOrder> orders = orderService.list(enterpriseId, staffId, serviceStatus, currentStage, settleStatus,
        offset, safeSize);
    long total = orderService.count(enterpriseId, staffId, serviceStatus, currentStage, settleStatus);
    return ApiResponse.ok(PageResult.of(orders, total, safePage, safeSize));
  }

  @PostMapping
  public ApiResponse create(@RequestBody CustomServiceOrder order) {
    Long id = orderService.create(order);
    return ApiResponse.ok(id);
  }

  @PutMapping("/{id}")
  public ApiResponse update(@PathVariable Long id, @RequestBody CustomServiceOrder order) {
    order.setId(id);
    if (!orderService.update(order)) {
      throw new ApiException(ApiCode.NOT_FOUND, "订单不存在");
    }
    return ApiResponse.ok(true);
  }

  @DeleteMapping("/{id}")
  public ApiResponse delete(@PathVariable Long id) {
    if (!orderService.delete(id)) {
      throw new ApiException(ApiCode.NOT_FOUND, "订单不存在");
    }
    return ApiResponse.ok(true);
  }

  @PostMapping("/{id}/assign")
  public ApiResponse assignStaff(@PathVariable Long id, @RequestBody AssignStaffRequest request) {
    if (!orderService.assignStaff(id, request.getStaffId())) {
      throw new ApiException(ApiCode.NOT_FOUND, "订单不存在");
    }
    return ApiResponse.ok(true);
  }

  @PostMapping("/{id}/service-status")
  public ApiResponse updateServiceStatus(@PathVariable Long id,
                                         @RequestBody UpdateStringStatusRequest request) {
    if (!orderService.updateServiceStatus(id, request.getStatus())) {
      throw new ApiException(ApiCode.NOT_FOUND, "订单不存在");
    }
    return ApiResponse.ok(true);
  }

  @PostMapping("/{id}/settle-status")
  public ApiResponse updateSettleStatus(@PathVariable Long id,
                                        @RequestBody UpdateSettleStatusRequest request) {
    if (!orderService.updateSettleStatus(id, request.getSettleStatus())) {
      throw new ApiException(ApiCode.NOT_FOUND, "订单不存在");
    }
    return ApiResponse.ok(true);
  }

  @PostMapping("/{id}/stage")
  public ApiResponse advanceStage(@PathVariable Long id, @RequestBody AdvanceStageRequest request) {
    boolean ok = orderService.advanceStage(id, request.getPostStage(), request.getServiceStatus(),
        request.getRemark(), request.getOperatorType(), request.getOperatorId());
    if (!ok) {
      throw new ApiException(ApiCode.NOT_FOUND, "订单不存在");
    }
    return ApiResponse.ok(true);
  }
}
