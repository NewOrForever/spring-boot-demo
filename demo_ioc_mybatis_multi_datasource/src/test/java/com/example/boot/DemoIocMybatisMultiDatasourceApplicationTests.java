package com.example.boot;

import com.example.boot.Service.PayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoIocMybatisMultiDatasourceApplicationTests {
    @Autowired
    private PayService payService;

    @Test
    void contextLoads() {
        payService.pay(1,2,10);
    }

}
