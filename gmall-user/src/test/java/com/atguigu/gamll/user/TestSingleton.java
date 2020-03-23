//package com.atguigu.gamll.user;
//
//import com.atguigu.gamll.user.bean.Counter;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = GamllUserApplication.class)//这里是启动类
//public class TestSingleton {
//
//    @Autowired
//    Counter counter;
//
//    @Test
//    public void test1() {
//
//        for (int i = 0; i < 10; i++) {
//            new Thread() {
//                @Override
//                public void run() {
//
//                    for (int j = 0; j < 1000; j++) {
//                        counter.addAndPrint();
//                    }
//                }
//            }.start();
//        }
//
//
//    }
//}
