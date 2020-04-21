package com.atguigu.gmall.service;


import com.atguigu.gmall.beans.PmsSkuInfo;

import java.util.List;

public interface SkuService {
    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    List<PmsSkuInfo> item(String skuId);

    List<PmsSkuInfo> getSkuSaleAttrList(String productId, String skuId);

    List<PmsSkuInfo> getAllSku();

    PmsSkuInfo getSkuById(String skuId);

}
