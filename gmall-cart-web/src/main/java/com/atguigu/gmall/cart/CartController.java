package com.atguigu.gmall.cart;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.beans.OmsCartItem;
import com.atguigu.gmall.beans.PmsSkuInfo;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CartController {


    @Reference
    SkuService skuService;
    @Reference
    CartService cartService;


    @RequestMapping("cartList")
    public List<OmsCartItem> cartList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        List<OmsCartItem> omsCartItems = new ArrayList<>();
        String userId = "1";
        if (StringUtils.isNotBlank(userId)) {//登录
            omsCartItems= cartService.cartList(userId);

        } else {//未登录,从cookie中取数据
            String cartListCookie = CookieUtil.getCookieValue(request,
                    "cartListCookie", true);
            if (StringUtils.isNotBlank(cartListCookie)) {
                omsCartItems = JSON.parseArray(cartListCookie, OmsCartItem.class);
            }
        }
        modelMap.put("cartList",omsCartItems);


        return omsCartItems;
    }


    @RequestMapping("addToCart")
    public String addToCart(String skuId, int quantity, HttpServletRequest request, HttpServletResponse response) {
        List<OmsCartItem> omsCartItems = new ArrayList<>();
        //查询SkuInfo,封装到购物车详情中
        PmsSkuInfo skuInfo = skuService.getSkuById(skuId);
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setCreateDate(new Date());
        omsCartItem.setDeleteStatus(0);
        omsCartItem.setModifyDate(new Date());
        omsCartItem.setPrice(skuInfo.getPrice());
        omsCartItem.setProductAttr("");
        omsCartItem.setProductBrand("");
        omsCartItem.setProductCategoryId(skuInfo.getCatalog3Id());
        omsCartItem.setProductId(skuInfo.getProductId());
        omsCartItem.setProductName(skuInfo.getSkuName());
        omsCartItem.setProductPic(skuInfo.getSkuDefaultImg());
        omsCartItem.setProductSkuCode("11111111111");
        omsCartItem.setProductSkuId(skuId);
        omsCartItem.setQuantity(quantity);

        //判断用户是否登录
        String memberId = "1";

        //未登录
        if (StringUtils.isBlank(memberId)) {
            //cookie 是否存在数据
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if (StringUtils.isNotBlank(cartListCookie)) {
                //存在数据
                omsCartItems = JSON.parseArray(cartListCookie, OmsCartItem.class);
                boolean isExist = if_cart_exist(omsCartItems, omsCartItem);
                if (isExist) {//存在相同的skuId,更新购买数量
                    for (OmsCartItem omsCartItem1 : omsCartItems) {
                        if (omsCartItem1.getProductSkuId().equals(skuId)) {
                            omsCartItem1.setQuantity(omsCartItem1.getQuantity() + omsCartItem.getQuantity());
                            break;
                        }
                    }
                } else {//不存在相同的skuId，则添加到购物车
                    omsCartItems.add(omsCartItem);
                }
            } else {//不存在数据则添加到购物车
                omsCartItems.add(omsCartItem);
            }
            //设置cookie
            String omsCartItemJsonString = JSON.toJSONString(omsCartItems);
            CookieUtil.setCookie(request, response, "cartListCookie",
                    omsCartItemJsonString, 60 * 60 * 72, true);
        } else {//登录
            OmsCartItem omsCartItemFromDb = cartService.getOmsCartItemsByMId(memberId, skuId);
            if (omsCartItemFromDb != null) {
                omsCartItemFromDb.setQuantity(omsCartItemFromDb.getQuantity() + quantity);
                cartService.updateOmsCartItem(omsCartItemFromDb);
            } else {
                omsCartItem.setMemberId(memberId);
                omsCartItem.setMemberNickname("test小明");
                omsCartItem.setQuantity(quantity);
                cartService.addCart(omsCartItem);
            }
            //同步缓存
            cartService.flushCartCache(memberId);

        }
        return "redirect:/success.html";
    }

    private boolean if_cart_exist(List<OmsCartItem> omsCartItems, OmsCartItem omsCartItem) {
        boolean b = false;
        for (OmsCartItem cartItem : omsCartItems) {
            String productSkuId = cartItem.getProductSkuId();
            if (productSkuId.equals(omsCartItem.getProductSkuId())) {
                b = true;
            }
        }
        return b;
    }


}

