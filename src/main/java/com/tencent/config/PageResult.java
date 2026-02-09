package com.tencent.config;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class PageResult<T> {

  private List<T> items;
  private long total;
  private int page;
  private int size;

  public static <T> PageResult<T> of(List<T> items, long total, int page, int size) {
    PageResult<T> result = new PageResult<>();
    result.items = items == null ? Collections.emptyList() : items;
    result.total = total;
    result.page = page;
    result.size = size;
    return result;
  }
}
