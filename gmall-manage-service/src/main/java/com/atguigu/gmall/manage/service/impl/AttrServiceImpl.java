package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.beans.PmsBaseAttrInfo;
import com.atguigu.gmall.beans.PmsBaseAttrValue;
import com.atguigu.gmall.beans.PmsBaseSaleAttr;
import com.atguigu.gmall.manage.mapper.PmsBaseAttrInfoMapper;
import com.atguigu.gmall.manage.mapper.PmsBaseAttrValueMapper;
import com.atguigu.gmall.manage.mapper.PmsBaseSaleAttrMapper;
import com.atguigu.gmall.service.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Autowired
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;
    @Autowired
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;


    @Override
    public List<PmsBaseAttrInfo> getAttrInfoList(String catalog3Id) {
        Example e  = new Example(PmsBaseAttrInfo.class);
        e.createCriteria().andEqualTo("catalog3Id", catalog3Id);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.selectByExample(e);
        for (PmsBaseAttrInfo pmsBaseAttrInfo : pmsBaseAttrInfos) {
            PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
            pmsBaseAttrInfo.setAttrValueList(pmsBaseAttrValueMapper.select(pmsBaseAttrValue));
        }
        return pmsBaseAttrInfos;


    }

    @Override
    public void savePmsAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        if(StringUtils.isBlank(pmsBaseAttrInfo.getId())){
            //add
            pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            for (PmsBaseAttrValue p :attrValueList) {
                p.setAttrId(pmsBaseAttrInfo.getId());
                pmsBaseAttrValueMapper.insertSelective(p);
            }
        }else {
            //update
            Example example = new Example(PmsBaseAttrInfo.class);
            example.createCriteria().andEqualTo("id",pmsBaseAttrInfo.getId());
            pmsBaseAttrInfoMapper.updateByExampleSelective(pmsBaseAttrInfo,example);

            PmsBaseAttrValue pmsBaseAttrValueDel = new PmsBaseAttrValue();
            pmsBaseAttrValueDel.setAttrId(pmsBaseAttrInfo.getId());
            pmsBaseAttrValueMapper.delete(pmsBaseAttrValueDel);

            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                pmsBaseAttrValueMapper.insertSelective(pmsBaseAttrValue);
            }

        }


    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        Example e  = new Example(PmsBaseAttrValue.class);
        e.createCriteria().andEqualTo("attrId",attrId);

        return
                pmsBaseAttrValueMapper.selectByExample(e);

    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
       return pmsBaseSaleAttrMapper.selectAll();


    }

    @Override
    public List<PmsBaseAttrInfo> getPmsBaseAttrInfo(Set<String> skuValueIds) {

        String valueIds = StringUtils.join(skuValueIds, ",");

        return pmsBaseAttrInfoMapper.selectPmsBaseAttrInfoList(valueIds);



    }
}
