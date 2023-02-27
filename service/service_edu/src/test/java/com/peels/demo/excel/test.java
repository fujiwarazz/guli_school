package com.peels.demo.excel;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class test {
    public static void main(String[] args) {
        String s = DigestUtils.md5DigestAsHex("100519".getBytes(StandardCharsets.UTF_8));
        boolean equals = s.equals("9df4c609104af31ed0d471b99c637b95");
        System.out.println(equals);
    }
}
