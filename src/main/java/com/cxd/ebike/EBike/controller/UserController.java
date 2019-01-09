package com.cxd.ebike.EBike.controller;

import com.cxd.ebike.EBike.entity.User;
import com.cxd.ebike.EBike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired //Spring自动注入
    private UserService userService;

    @RequestMapping("/genCode")
    @ResponseBody
    public boolean genVerifyCode(String countryCode,String phoneNum){

        boolean success = userService.sendMsg(countryCode,phoneNum);
        return true;
    }

    @RequestMapping("/verify")
    @ResponseBody
    public boolean verify(String phoneNum,String verifyCode){
        return  userService.verify(phoneNum,verifyCode);
    }

    @RequestMapping("/register")
    @ResponseBody
    public boolean register(@RequestBody User user){
        boolean success = true;
        try {
            userService.register(user);
        }catch (Exception e){
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    @RequestMapping("/update")
    @ResponseBody
    public boolean update(@RequestBody User user){
        boolean success = true;
        try {
            userService.update(user);
        }catch (Exception e){
            success = false;
        }
        return success;
    }
}
