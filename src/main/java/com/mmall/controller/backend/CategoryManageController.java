package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.impl.CategoryServiceIml;
import com.mmall.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by philer on 2019/1/24.
 */
@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private UserServiceImpl iUserService;


    @Autowired
    private CategoryServiceIml categoryService;

    @RequestMapping(value = "add_category.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户为登陆");
        }
        if(iUserService.checkAdminRole(user).isSuccess()) {
            return categoryService.addCategory(categoryName, parentId);
        }
        return ServerResponse.createByErrorMessage("无对应权限");
    }

    @RequestMapping(value = "get_children_parallel_category.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session, Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户为登陆");
        }
        if(iUserService.checkAdminRole(user).isSuccess()) {
            return categoryService.getChildrenParallelCategory(categoryId);
        }
        return ServerResponse.createByErrorMessage("无对应权限");
    }
}