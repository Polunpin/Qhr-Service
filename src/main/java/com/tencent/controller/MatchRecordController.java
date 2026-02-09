package com.tencent.controller;

import com.tencent.config.ApiResponse;
import com.tencent.dto.UpdateStringStatusRequest;
import com.tencent.model.MatchRecord;
import com.tencent.service.MatchRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.tencent.config.ApiCode;
import com.tencent.config.ApiException;
import com.tencent.config.PageRequest;
import com.tencent.config.PageResult;

@RestController
@RequestMapping("/api/match-records")
public class MatchRecordController {

  private final MatchRecordService matchRecordService;

  public MatchRecordController(@Autowired MatchRecordService matchRecordService) {
    this.matchRecordService = matchRecordService;
  }

  @GetMapping("/{id}")
  public ApiResponse getById(@PathVariable Long id) {
    MatchRecord record = matchRecordService.getById(id);
    if (record == null) {
      throw new ApiException(ApiCode.NOT_FOUND, "匹配记录不存在");
    }
    return ApiResponse.ok(record);
  }

  @GetMapping("/list")
  public ApiResponse list(@RequestParam(required = false) Long enterpriseId,
                          @RequestParam(required = false) Long intentionId,
                          @RequestParam(required = false) String status,
                          @RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer size) {
    int safePage = PageRequest.normalizePage(page);
    int safeSize = PageRequest.normalizeSize(size);
    int offset = PageRequest.offset(safePage, safeSize);
    List<MatchRecord> records = matchRecordService.list(enterpriseId, intentionId, status, offset, safeSize);
    long total = matchRecordService.count(enterpriseId, intentionId, status);
    return ApiResponse.ok(PageResult.of(records, total, safePage, safeSize));
  }

  @PostMapping
  public ApiResponse create(@RequestBody MatchRecord record) {
    Long id = matchRecordService.create(record);
    return ApiResponse.ok(id);
  }

  @PutMapping("/{id}")
  public ApiResponse update(@PathVariable Long id, @RequestBody MatchRecord record) {
    record.setId(id);
    if (!matchRecordService.update(record)) {
      throw new ApiException(ApiCode.NOT_FOUND, "匹配记录不存在");
    }
    return ApiResponse.ok(true);
  }

  @DeleteMapping("/{id}")
  public ApiResponse delete(@PathVariable Long id) {
    if (!matchRecordService.delete(id)) {
      throw new ApiException(ApiCode.NOT_FOUND, "匹配记录不存在");
    }
    return ApiResponse.ok(true);
  }

  @PostMapping("/{id}/status")
  public ApiResponse updateStatus(@PathVariable Long id,
                                  @RequestBody UpdateStringStatusRequest request) {
    if (!matchRecordService.updateStatus(id, request.getStatus())) {
      throw new ApiException(ApiCode.NOT_FOUND, "匹配记录不存在");
    }
    return ApiResponse.ok(true);
  }
}
