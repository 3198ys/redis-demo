package com.ys.demoredis;

import com.ys.demoredis.entity.people;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoRedisApplicationTests {

    @Test
    public void contextLoads() {

        new Thread(()-> System.out.println("aaaa")).start();
    }



    @Test
    public void contextLoads1() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("aaaa");
            }
        }).start();
    }



}
