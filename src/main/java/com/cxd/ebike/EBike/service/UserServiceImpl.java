package com.cxd.ebike.EBike.service;

import com.cxd.ebike.EBike.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean sendMsg(String countryCode, String phoneNum) {

        int appid = Integer.parseInt(stringRedisTemplate.opsForValue().get("appid")); // 1400开头
        String appkey = stringRedisTemplate.opsForValue().get("appkey");
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(phoneNum);
        int templateId = 7839; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
        String smsSign = "腾讯云";
        //调用腾讯云短信API
        String code = (int)((Math.random()*9+1)*1000)+"";
        SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
        try {
            //向用户发送短信
            SmsSingleSenderResult result = ssender.send(0,countryCode,phoneNumbers.get(0),"您的验证码是"+code,"","");
            //存储（手机号，验证码）到Redis
            stringRedisTemplate.opsForValue().set(phoneNum,code,300,TimeUnit.SECONDS);
        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return false;
    }

    @Override
    public boolean verify(String phoneNum, String verifyCode) {
        boolean success = false;
        String code = stringRedisTemplate.opsForValue().get(phoneNum);
        if(code!=null&&code.equals(verifyCode.trim())){
            success = true;
        }
        return success;
    }

    @Override
    public void register(User user) {
        //一般不在Service而在Controller层捕获异常
        mongoTemplate.insert(user);
    }

    @Override
    public void update(User user) {
        //insert: 不存在则新增，存在则更新，根据_id判断，所以不可用。
        Update update = new Update();
        if(user.getDeposit()!=null){
            update.set("deposit",user.getDeposit());
        }
        if(user.getStatus()!=null){
            update.set("status",user.getStatus());
        }
        if(user.getName()!=null){
            update.set("name",user.getName());
        }
        if(user.getIdNo()!=null){
            update.set("idNo",user.getIdNo());
        }
        mongoTemplate.updateFirst(new Query(Criteria.where("phoneNum").is(user.getPhoneNum())), update,User.class);
    }

}
