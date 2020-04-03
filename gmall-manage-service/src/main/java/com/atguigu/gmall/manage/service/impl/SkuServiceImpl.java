package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.atguigu.gmall.beans.PmsSkuAttrValue;
import com.atguigu.gmall.beans.PmsSkuImage;
import com.atguigu.gmall.beans.PmsSkuInfo;
import com.atguigu.gmall.beans.PmsSkuSaleAttrValue;
import com.atguigu.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuImageMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuInfoMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {

        // 插入skuInfo
        int i = pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();

        // 插入平台属性关联
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }

        // 插入销售属性关联
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }

        // 插入图片信息
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }


    }

    @Override
    public List<PmsSkuInfo> item(String skuId) {
        //获取redis连接
        Jedis jedis = redisUtil.getJedis();

        //查询缓存
        String key ="sku:"+skuId+":info";//sku:108:info
        String value = jedis.get(key);
        //如果查到就返回结果,没有就查询数据库，并且放到缓存中
        List<PmsSkuInfo> pmsSkuInfos;
        if(StringUtils.isBlank(value)){
             pmsSkuInfos = itemFromDb(skuId);
            jedis.set(key,JSON.toJSON(pmsSkuInfos).toString());

        }else {
             pmsSkuInfos = JSON.parseArray(jedis.get(key), PmsSkuInfo.class);
        }

        return pmsSkuInfos;
    }


    private List<PmsSkuInfo> itemFromDb(String skuId) {
        PmsSkuInfo temp = new PmsSkuInfo();
        temp.setId(skuId);
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoMapper.selectOne(temp);
        List<PmsSkuInfo> list = new ArrayList<PmsSkuInfo>();
        list.add(pmsSkuInfo);
        return list;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrList(String productId, String skuId) {

        return pmsSkuInfoMapper.selectSkuSaleAttrList(productId, skuId);

    }
}
