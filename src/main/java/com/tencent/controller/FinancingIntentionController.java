package com.tencent.controller;

import com.tencent.config.ApiResponse;
import com.tencent.dto.UpdateIntentionStatusRequest;
import com.tencent.model.FinancingIntention;
import com.tencent.service.FinancingIntentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.tencent.config.ApiAssert;
import com.tencent.config.ApiCode;
import com.tencent.config.PageBounds;
import com.tencent.config.PageResult;

@RestController
@RequestMapping("/api/financing-intentions")
public class FinancingIntentionController {

  private final FinancingIntentionService intentionService;

  public FinancingIntentionController(@Autowired FinancingIntentionService intentionService) {
    this.intentionService = intentionService;
  }

  /** 查询融资意向详情 */
  @GetMapping("/{id}")
  public ApiResponse getById(@PathVariable Long id) {
    FinancingIntention intention = intentionService.getById(id);
    ApiAssert.notNull(intention, ApiCode.NOT_FOUND, "意向不存在");
    return ApiResponse.ok(intention);
  }

  /** 分页查询融资意向列表 */
  @GetMapping("/list")
  public ApiResponse list(@RequestParam(required = false) Long enterpriseId,
                          @RequestParam(required = false) Long userId,
                          @RequestParam(required = false) String status,
                          @RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer size) {
    PageBounds bounds = PageBounds.of(page, size);
    List<FinancingIntention> intentions = intentionService.list(enterpriseId, userId, status,
        bounds.offset(), bounds.size());
    long total = intentionService.count(enterpriseId, userId, status);
    return ApiResponse.ok(PageResult.of(intentions, total, bounds.page(), bounds.size()));
  }

  /** 创建融资意向 */
  @PostMapping
  public ApiResponse create(@RequestBody FinancingIntention intention) {
    Long id = intentionService.create(intention);
    return ApiResponse.ok(id);
  }

  /** 更新融资意向 */
  @PutMapping("/{id}")
  public ApiResponse update(@PathVariable Long id, @RequestBody FinancingIntention intention) {
    ApiAssert.isTrue(intentionService.update(intention.withId(id)), ApiCode.NOT_FOUND, "意向不存在");
    return ApiResponse.ok(true);
  }

  /** 删除融资意向 */
  @DeleteMapping("/{id}")
  public ApiResponse delete(@PathVariable Long id) {
    ApiAssert.isTrue(intentionService.delete(id), ApiCode.NOT_FOUND, "意向不存在");
    return ApiResponse.ok(true);
  }

  /** 更新融资意向状态 */
  @PostMapping("/{id}/status")
  public ApiResponse updateStatus(@PathVariable Long id,
                                  @RequestBody UpdateIntentionStatusRequest request) {
    ApiAssert.isTrue(intentionService.updateStatus(id, request.status(), request.refusalReason()),
        ApiCode.NOT_FOUND, "意向不存在");
    return ApiResponse.ok(true);
  }

  /** 绑定融资意向目标产品 */
  @PostMapping("/{id}/target-product/{productId}")
  public ApiResponse updateTargetProduct(@PathVariable Long id, @PathVariable Long productId) {
    ApiAssert.isTrue(intentionService.updateTargetProduct(id, productId), ApiCode.NOT_FOUND, "意向不存在");
    return ApiResponse.ok(true);
  }
}
