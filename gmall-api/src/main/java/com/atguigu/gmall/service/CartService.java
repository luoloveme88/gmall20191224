package com.atguigu.gmall.service;

import com.atguigu.gmall.beans.OmsCartItem;

import java.util.List;

public interface CartService {
    OmsCartItem getOmsCartItemsByMId(String memberId, String skuId);

    void updateOmsCartItem(OmsCartItem omsCartItemFromDb);

    void addCart(OmsCartItem omsCartItem);

    void flushCartCache(String memberId);

    List<OmsCartItem> cartList(String userId);
}
