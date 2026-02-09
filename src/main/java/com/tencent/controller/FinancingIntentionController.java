package com.tencent.controller;

import com.tencent.config.ApiResponse;
import com.tencent.dto.UpdateIntentionStatusRequest;
import com.tencent.model.FinancingIntention;
import com.tencent.service.FinancingIntentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.tencent.config.ApiCode;
import com.tencent.config.ApiException;
import com.tencent.config.PageRequest;
import com.tencent.config.PageResult;

@RestController
@RequestMapping("/api/financing-intentions")
public class FinancingIntentionController {

  private final FinancingIntentionService intentionService;

  public FinancingIntentionController(@Autowired FinancingIntentionService intentionService) {
    this.intentionService = intentionService;
  }

  @GetMapping("/{id}")
  public ApiResponse getById(@PathVariable Long id) {
    FinancingIntention intention = intentionService.getById(id);
    if (intention == null) {
      throw new ApiException(ApiCode.NOT_FOUND, "意向不存在");
    }
    return ApiResponse.ok(intention);
  }

  @GetMapping("/list")
  public ApiResponse list(@RequestParam(required = false) Long enterpriseId,
                          @RequestParam(required = false) Long userId,
                          @RequestParam(required = false) String status,
                          @RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer size) {
    int safePage = PageRequest.normalizePage(page);
    int safeSize = PageRequest.normalizeSize(size);
    int offset = PageRequest.offset(safePage, safeSize);
    List<FinancingIntention> intentions = intentionService.list(enterpriseId, userId, status, offset, safeSize);
    long total = intentionService.count(enterpriseId, userId, status);
    return ApiResponse.ok(PageResult.of(intentions, total, safePage, safeSize));
  }

  @PostMapping
  public ApiResponse create(@RequestBody FinancingIntention intention) {
    Long id = intentionService.create(intention);
    return ApiResponse.ok(id);
  }

  @PutMapping("/{id}")
  public ApiResponse update(@PathVariable Long id, @RequestBody FinancingIntention intention) {
    intention.setId(id);
    if (!intentionService.update(intention)) {
      throw new ApiException(ApiCode.NOT_FOUND, "意向不存在");
    }
    return ApiResponse.ok(true);
  }

  @DeleteMapping("/{id}")
  public ApiResponse delete(@PathVariable Long id) {
    if (!intentionService.delete(id)) {
      throw new ApiException(ApiCode.NOT_FOUND, "意向不存在");
    }
    return ApiResponse.ok(true);
  }

  @PostMapping("/{id}/status")
  public ApiResponse updateStatus(@PathVariable Long id,
                                  @RequestBody UpdateIntentionStatusRequest request) {
    if (!intentionService.updateStatus(id, request.getStatus(), request.getRefusalReason())) {
      throw new ApiException(ApiCode.NOT_FOUND, "意向不存在");
    }
    return ApiResponse.ok(true);
  }

  @PostMapping("/{id}/target-product/{productId}")
  public ApiResponse updateTargetProduct(@PathVariable Long id, @PathVariable Long productId) {
    if (!intentionService.updateTargetProduct(id, productId)) {
      throw new ApiException(ApiCode.NOT_FOUND, "意向不存在");
    }
    return ApiResponse.ok(true);
  }
}
