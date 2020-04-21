package com.atguigu.gmall.service;

import com.atguigu.gmall.beans.PmsSearchSkuInfo;
import com.atguigu.gmall.beans.PmsSearchSkuInfoParam;

import java.util.List;

public interface SearchService {
    List<PmsSearchSkuInfo> list(PmsSearchSkuInfoParam searchSkuInfoParam);
}
