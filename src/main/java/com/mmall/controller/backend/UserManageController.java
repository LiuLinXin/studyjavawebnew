package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by philer on 2019/1/24.
 */
@Controller
@RequestMapping("/manage/user/")
public class UserManageController {

    @Autowired
    private UserServiceImpl iUserService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String userName, String password, HttpSession httpSession){
        ServerResponse<User> response = iUserService.login(userName, password);
        if(response.isSuccess()){
            User data = response.getData();
            if(data.getRole() == Const.Role.ADMIN){
                httpSession.setAttribute(Const.CURRENT_USER, data);
            }else {
                return ServerResponse.createByErrorMessage("不是管理员，无法登陆");
            }
        }
        return response;
    }
}
