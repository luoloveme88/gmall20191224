package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.beans.PmsBaseAttrInfo;
import com.atguigu.gmall.beans.PmsBaseAttrValue;
import com.atguigu.gmall.beans.PmsBaseCatalog1;
import com.atguigu.gmall.beans.PmsBaseSaleAttr;
import com.atguigu.gmall.service.AttrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AttrController {

    @Reference
    AttrService attrService;


    @RequestMapping("attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> getAttrInfoList(String catalog3Id){
        return attrService.getAttrInfoList(catalog3Id);
    }


    //saveAttrInfo
    @RequestMapping(value = "saveAttrInfo")
    @ResponseBody
    public void savePmsAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
        attrService.savePmsAttrInfo(pmsBaseAttrInfo);
    }

    //getAttrValueList
    @RequestMapping(value = "getAttrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue> getAttrValueList(String attrId){
      return   attrService.getAttrValueList(attrId);
    }


    //baseSaleAttrList
    @RequestMapping(value = "baseSaleAttrList")
    @ResponseBody
    public List<PmsBaseSaleAttr> baseSaleAttrList(){
        return   attrService.baseSaleAttrList();
    }
}
