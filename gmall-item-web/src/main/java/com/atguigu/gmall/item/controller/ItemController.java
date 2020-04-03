package com.atguigu.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.beans.PmsProductSaleAttr;
import com.atguigu.gmall.beans.PmsSkuInfo;
import com.atguigu.gmall.beans.PmsSkuSaleAttrValue;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    @Reference
    SkuService skuService;
    @Reference
    SpuService spuService;

    @RequestMapping("index")
    @ResponseBody
    public String index() {
        return "index";

    }

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId, ModelMap map) {
        List<PmsSkuInfo> skuInfoList = skuService.item(skuId);
        PmsSkuInfo pmsSkuInfo = skuInfoList.get(0);
        map.put("skuInfo", pmsSkuInfo);

        //销售属性列表
        List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttrListCheckBySku(pmsSkuInfo.getProductId(), skuId);
        map.put("spuSaleAttrListCheckBySku", pmsProductSaleAttrs);

        List<PmsSkuInfo> pmsSkuInfos = skuService.getSkuSaleAttrList(pmsSkuInfo.getProductId(), skuId);
        Map<String, String> skuSaleAttrValueMap = new HashMap<>();
        for (PmsSkuInfo sku : pmsSkuInfos) {
            String key = "";
            String value = sku.getId();
            for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : sku.getSkuSaleAttrValueList()) {
                key += pmsSkuSaleAttrValue.getSaleAttrValueId()+"|";//"46|50|"
            }
            skuSaleAttrValueMap.put(key,value);
        }
        String s = JSON.toJSONString(skuSaleAttrValueMap);
        map.put("skuSaleAttrHashJsonStr", s);


        return "item";

    }
}
