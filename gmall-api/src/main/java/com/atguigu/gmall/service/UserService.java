package com.atguigu.gmall.service;

import com.atguigu.gmall.beans.UmsMember;
import com.atguigu.gmall.beans.UmsMemberReceiveAddress;

import java.util.List;


public interface UserService {
    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getMemberById(String memberId);

    UmsMember getUserMember(String id);

    void editUserMember(UmsMember umsMember);

    void addUser(UmsMember umsMember);
}
