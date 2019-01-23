package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by philer on 2019/1/23.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUserName(username);
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        //// TODO: 2019/1/23 密码登陆md5
        User user = userMapper.selectLogin(username, password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccessMessage("登陆成功", user);
    }

    public ServerResponse<String> regist(User user){
        int resultCount1 = userMapper.checkUserName(user.getUsername());
        if (resultCount1 > 0){
            return ServerResponse.createByErrorMessage("用户已存在");
        }

        int resultCount2 = userMapper.checkEmail(user.getEmail());
        if (resultCount2 > 0){
            return ServerResponse.createByErrorMessage("email已存在");
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        return null;
    }
}















