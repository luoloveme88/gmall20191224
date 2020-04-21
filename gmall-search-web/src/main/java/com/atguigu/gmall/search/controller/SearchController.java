package com.atguigu.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.beans.PmsBaseAttrInfo;
import com.atguigu.gmall.beans.PmsSearchSkuInfo;
import com.atguigu.gmall.beans.PmsSearchSkuInfoParam;
import com.atguigu.gmall.beans.PmsSkuAttrValue;
import com.atguigu.gmall.service.AttrService;
import com.atguigu.gmall.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SearchController {

    @Reference
    SearchService searchService;
    @Reference
    AttrService attrService;

    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @RequestMapping("list")
    public String list(PmsSearchSkuInfoParam searchSkuInfoParam, ModelMap modelMap) {
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = searchService.list(searchSkuInfoParam);
        modelMap.put("skuLsInfoList", pmsSearchSkuInfos);


        //
        Set<String> skuValueIds = new HashSet<>();
        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfos) {
            List<PmsSkuAttrValue> skuAttrValueList = pmsSearchSkuInfo.getSkuAttrValueList();
            for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                skuValueIds.add(pmsSkuAttrValue.getValueId());
            }
        }
        List<PmsBaseAttrInfo> pmsBaseAttrInfos
                = attrService.getPmsBaseAttrInfo(skuValueIds);

        modelMap.put("attrList", pmsBaseAttrInfos);


        return "list";
    }


}
