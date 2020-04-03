package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.beans.PmsSkuInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsSkuInfoMapper extends Mapper<PmsSkuInfo>{
    List<PmsSkuInfo> selectSkuSaleAttrList(@Param("productId") String productId, @Param("skuId") String skuId);
}
