package com.atguigu.gamll.user.service;

import com.atguigu.gamll.user.bean.UmsMember;
import com.atguigu.gamll.user.bean.UmsMemberReceiveAddress;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getMemberById(String memberId);

    UmsMember getUserMember(String id);

    void editUserMember(UmsMember umsMember);

    void addUser(UmsMember umsMember);
}
