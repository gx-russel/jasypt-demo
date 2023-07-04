package com.example.jasyptdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class JasyptDemoApplicationTests {

    @Resource
    private BaseSysUserMapper baseSysUserMapper;

    @Test
    void contextLoads() {
        Long aLong = baseSysUserMapper.selectCount(new QueryWrapper<>());
        System.out.println("aLong = " + aLong);
    }

}
