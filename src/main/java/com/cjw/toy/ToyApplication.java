package com.cjw.toy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController         // 테스트 작동 확인.
@SpringBootApplication
public class ToyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToyApplication.class, args);
    }

    @GetMapping
    public String HelloWorld(){
        return "Hello World";
    }

}
