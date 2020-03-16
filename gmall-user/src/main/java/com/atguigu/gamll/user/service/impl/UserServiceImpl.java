package com.atguigu.gamll.user.service.impl;

import com.atguigu.gamll.user.bean.UmsMember;
import com.atguigu.gamll.user.bean.UmsMemberReceiveAddress;
import com.atguigu.gamll.user.mapper.UmsMemberReceiveAddressMapper;
import com.atguigu.gamll.user.mapper.UserMapper;
import com.atguigu.gamll.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UmsMemberReceiveAddressMapper uMRAMapper;

    @Override
    public List<UmsMember> getAllUser() {
        return userMapper.selectAllUser();
    }

    @Override
    public List<UmsMemberReceiveAddress> getMemberById(String memberId) {
//        UmsMemberReceiveAddress e  = new UmsMemberReceiveAddress();
//        e.setMemberId(memberId);
//
//        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = uMRAMapper.select(e);

        Example e  = new Example(UmsMemberReceiveAddress.class);
        e.createCriteria().andEqualTo("memberId",memberId);

        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = uMRAMapper.selectByExample(e);
        return umsMemberReceiveAddresses;
    }

    @Override
    public UmsMember getUserMember(String id) {

        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void editUserMember(UmsMember umsMember) {
        UmsMember old = userMapper.selectByPrimaryKey(umsMember.getId());
        BeanUtils.copyProperties(umsMember,old,"id");
        userMapper.updateByPrimaryKey(old);

    }

    @Override
    public void addUser(UmsMember umsMember) {
        userMapper.insert(umsMember);
    }
}
