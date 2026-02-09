package com.tencent.controller;

import com.tencent.config.ApiResponse;
import com.tencent.dto.MatchProductsRequest;
import com.tencent.dto.UpdateProductStatusRequest;
import com.tencent.model.CreditProduct;
import com.tencent.service.CreditProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.tencent.config.ApiCode;
import com.tencent.config.ApiException;
import com.tencent.config.PageRequest;
import com.tencent.config.PageResult;

@RestController
@RequestMapping("/api/credit-products")
public class CreditProductController {

  private final CreditProductService creditProductService;

  public CreditProductController(@Autowired CreditProductService creditProductService) {
    this.creditProductService = creditProductService;
  }

  @GetMapping("/{id}")
  public ApiResponse getById(@PathVariable Long id) {
    CreditProduct product = creditProductService.getById(id);
    if (product == null) {
      throw new ApiException(ApiCode.NOT_FOUND, "产品不存在");
    }
    return ApiResponse.ok(product);
  }

  @GetMapping("/list")
  public ApiResponse list(@RequestParam(required = false) Integer status,
                          @RequestParam(required = false) String productType,
                          @RequestParam(required = false) String bankName,
                          @RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer size) {
    int safePage = PageRequest.normalizePage(page);
    int safeSize = PageRequest.normalizeSize(size);
    int offset = PageRequest.offset(safePage, safeSize);
    List<CreditProduct> products = creditProductService.list(status, productType, bankName, offset, safeSize);
    long total = creditProductService.count(status, productType, bankName);
    return ApiResponse.ok(PageResult.of(products, total, safePage, safeSize));
  }

  @PostMapping
  public ApiResponse create(@RequestBody CreditProduct product) {
    Long id = creditProductService.create(product);
    return ApiResponse.ok(id);
  }

  @PutMapping("/{id}")
  public ApiResponse update(@PathVariable Long id, @RequestBody CreditProduct product) {
    product.setId(id);
    if (!creditProductService.update(product)) {
      throw new ApiException(ApiCode.NOT_FOUND, "产品不存在");
    }
    return ApiResponse.ok(true);
  }

  @DeleteMapping("/{id}")
  public ApiResponse delete(@PathVariable Long id) {
    if (!creditProductService.delete(id)) {
      throw new ApiException(ApiCode.NOT_FOUND, "产品不存在");
    }
    return ApiResponse.ok(true);
  }

  @PostMapping("/{id}/status")
  public ApiResponse updateStatus(@PathVariable Long id,
                                  @RequestBody UpdateProductStatusRequest request) {
    if (!creditProductService.updateStatus(id, request.getStatus())) {
      throw new ApiException(ApiCode.NOT_FOUND, "产品不存在");
    }
    return ApiResponse.ok(true);
  }

  @PostMapping("/match")
  public ApiResponse matchProducts(@RequestBody MatchProductsRequest request,
                                   @RequestParam(required = false) Integer page,
                                   @RequestParam(required = false) Integer size) {
    int safePage = PageRequest.normalizePage(page);
    int safeSize = PageRequest.normalizeSize(size);
    int offset = PageRequest.offset(safePage, safeSize);
    List<CreditProduct> products = creditProductService.findEligibleProducts(
        request.getExpectedAmount(), request.getExpectedTerm(), request.getRegionCode(), request.getProductType(),
        offset, safeSize);
    long total = creditProductService.countEligibleProducts(
        request.getExpectedAmount(), request.getExpectedTerm(), request.getRegionCode(), request.getProductType());
    return ApiResponse.ok(PageResult.of(products, total, safePage, safeSize));
  }
}
