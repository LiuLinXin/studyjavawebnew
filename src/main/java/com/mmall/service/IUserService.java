package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import org.omg.CORBA.Object;

/**
 * Created by philer on 2019/1/23.
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);
}
