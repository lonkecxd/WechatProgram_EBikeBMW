package com.cxd.ebike.EBike.service;

import com.cxd.ebike.EBike.entity.User;

public interface UserService {
    boolean sendMsg(String countryCode, String phoneNum);

    boolean verify(String phoneNum, String verifyCode);

    void register(User user);

    void update(User user);
}
