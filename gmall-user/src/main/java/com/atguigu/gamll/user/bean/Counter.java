package com.atguigu.gamll.user.bean;

import org.springframework.stereotype.Component;

@Component
public class Counter {

    private int num;

     public void addAndPrint(){
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(++num);
         System.out.println("test checkout 008");
         System.out.println("111111");
         System.out.println("5555555");
    }

}
