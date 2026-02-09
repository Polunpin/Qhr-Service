package com.tencent.dao;

import com.tencent.model.CreditProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CreditProductsMapper {

  CreditProduct getById(@Param("id") Long id);

  int insert(CreditProduct product);

  int update(CreditProduct product);

  int delete(@Param("id") Long id);

  int updateStatus(@Param("id") Long id, @Param("status") Integer status);

  List<CreditProduct> list(@Param("status") Integer status,
                           @Param("productType") String productType,
                           @Param("bankName") String bankName,
                           @Param("offset") Integer offset,
                           @Param("size") Integer size);

  long count(@Param("status") Integer status,
             @Param("productType") String productType,
             @Param("bankName") String bankName);

  List<CreditProduct> findEligibleProducts(@Param("expectedAmount") BigDecimal expectedAmount,
                                           @Param("expectedTerm") Integer expectedTerm,
                                           @Param("regionCode") String regionCode,
                                           @Param("productType") String productType,
                                           @Param("offset") Integer offset,
                                           @Param("size") Integer size);

  long countEligibleProducts(@Param("expectedAmount") BigDecimal expectedAmount,
                             @Param("expectedTerm") Integer expectedTerm,
                             @Param("regionCode") String regionCode,
                             @Param("productType") String productType);
}
