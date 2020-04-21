package com.atguigu.gmall.cart.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.beans.OmsCartItem;
import com.atguigu.gmall.cart.mapper.OmsCartItemMapper;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    OmsCartItemMapper omsCartItemMapper;

    @Override
    public OmsCartItem getOmsCartItemsByMId(String memberId, String skuId) {
        OmsCartItem o = new OmsCartItem();
        o.setMemberId(memberId);
        o.setProductSkuId(skuId);
        return omsCartItemMapper.selectOne(o);

    }

    @Override
    public void updateOmsCartItem(OmsCartItem omsCartItemFromDb) {
        Example e = new Example(OmsCartItem.class);
        e.createCriteria().andEqualTo("id", omsCartItemFromDb.getId());

        omsCartItemMapper.updateByExampleSelective(omsCartItemFromDb, e);
    }

    @Override
    public void addCart(OmsCartItem omsCartItem) {
        if (StringUtils.isNotBlank(omsCartItem.getMemberId())) {
            omsCartItemMapper.insertSelective(omsCartItem);//避免添加空值
        }
    }

    @Override
    public void flushCartCache(String memberId) {
        Jedis jedis = redisUtil.getJedis();
        OmsCartItem temp = new OmsCartItem();
        temp.setMemberId(memberId);
        List<OmsCartItem> omsCartItems = omsCartItemMapper.select(temp);
        Map<String, String> map = new HashMap<>();
        for (OmsCartItem o : omsCartItems) {
            map.put(o.getProductSkuId(), JSON.toJSONString(o));
        }

        String key = "user:" + memberId + ":cart";
        jedis.del(key);
        jedis.hmset(key, map);
        jedis.close();

    }

    @Override
    public List<OmsCartItem> cartList(String userId) {
        List<OmsCartItem>  omsCartItemList  = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            List<String> OmsCartItems = jedis.hvals("user:"+userId+":cart");
            String s = jedis.hget("user:" + userId + ":cart", "11");
            for (String omsCartItemJsonStr : OmsCartItems) {
                OmsCartItem omsCartItem = JSON.parseObject(omsCartItemJsonStr, OmsCartItem.class);
                omsCartItemList.add(omsCartItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }

        return omsCartItemList;
    }
}
