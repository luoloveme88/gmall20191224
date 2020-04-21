package com.atguigu.gmall.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PmsSearchSkuInfoParam implements Serializable {

    private String catalog3Id;
    private String keyword;
    private List<PmsSkuAttrValue> skuAttrValueList;
}
