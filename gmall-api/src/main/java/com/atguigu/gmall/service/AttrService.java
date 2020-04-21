package com.atguigu.gmall.service;

import com.atguigu.gmall.beans.PmsBaseAttrInfo;
import com.atguigu.gmall.beans.PmsBaseAttrValue;
import com.atguigu.gmall.beans.PmsBaseSaleAttr;

import java.util.List;
import java.util.Set;

public interface AttrService {
    List<PmsBaseAttrInfo> getAttrInfoList(String catalog3Id);

    void savePmsAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    List<PmsBaseSaleAttr> baseSaleAttrList();

    List<PmsBaseAttrInfo> getPmsBaseAttrInfo(Set<String> skuValueIds);

}
