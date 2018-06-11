package org.lemon.yhj.controller;

import org.lemon.yhj.cipher.Base64;
import org.lemon.yhj.cipher.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TestController {

    @ResponseBody
    @RequestMapping("password")
    public String getPassword(String str){
        try {
            return MD5.md5(str);
        } catch (Exception e) {
            return "出错了";
        }
    }

    public static void main(String[] args) {
        String str = "1000000";
        String encode = Base64.encode(str.getBytes());
        System.out.println(encode);
        System.out.println(new String(Base64.decode(encode)));
    }
}
