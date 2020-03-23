package com.atguigu.gamll.user.controller;


import com.atguigu.gmall.beans.UmsMember;
import com.atguigu.gmall.beans.UmsMemberReceiveAddress;
import com.atguigu.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;


    @RequestMapping("index")
    @ResponseBody
    public String index(){
        return "hello gmall";
    }



    @RequestMapping("/selectAll")
    @ResponseBody
    public List<UmsMember> getAllUser(){
        return userService.getAllUser();
    }


    @RequestMapping(value = "/update_user",method = RequestMethod.POST)
    @ResponseBody
    public void editUser(@RequestBody UmsMember umsMember){
        userService.editUserMember(umsMember);
    }


    @RequestMapping("/getMraById")
    @ResponseBody
    public List<UmsMemberReceiveAddress> getAllUser(String memberId){
        return userService.getMemberById(memberId);
    }

    @PostMapping("/add")
    @ResponseBody
    public void addUser(@RequestBody UmsMember umsMember){
        userService.addUser(umsMember);
    }



}
