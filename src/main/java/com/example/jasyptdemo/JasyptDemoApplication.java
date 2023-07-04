package com.example.jasyptdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@SpringBootApplication
@RestController
public class JasyptDemoApplication {
    @Resource
    private BaseSysUserMapper baseSysUserMapper;

    public static void main(String[] args) {
        SpringApplication.run(JasyptDemoApplication.class, args);
    }

    @GetMapping("/test")
    public Long test() {
        Long aLong = baseSysUserMapper.selectCount(new QueryWrapper<>());
        System.out.println("aLong = " + aLong);
        return aLong;
    }

}
