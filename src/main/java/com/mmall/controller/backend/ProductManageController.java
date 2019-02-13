package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.impl.FileService;
import com.mmall.service.impl.ProductService;
import com.mmall.service.impl.UserServiceImpl;
import com.mmall.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by philer on 2019/1/28.productService
 */
@Controller
@RequestMapping("manage/product/")
public class ProductManageController {

    @Autowired
    private UserServiceImpl iUserService;
    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;

    //保存或修改商品
    @RequestMapping(value = "save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆,请登陆管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return productService.saveOrUpdateProduct(product);
        } else {
            return ServerResponse.createByErrorMessage("当前身份无对应权限");
        }
    }

    //修改商品在售状态
    @RequestMapping("setSaleStatus.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆,请登陆管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return productService.setSaleStatus(productId, status);
        } else {
            return ServerResponse.createByErrorMessage("当前身份无对应权限");
        }
    }

    //获取商品详情
    @RequestMapping("getDetail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆,请登陆管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return productService.getDetail(productId);
        } else {
            return ServerResponse.createByErrorMessage("当前身份无对应权限");
        }
    }

    /**
     * 获取商品列表
     * @param pageNum 第几页
     * @param pageSize 每页数量
     * @return
     */
    @RequestMapping("getList.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session,
                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆,请登陆管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return productService.getProductList(pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("当前身份无对应权限");
        }
    }

    @RequestMapping("searchProduct.do")
    @ResponseBody
    public ServerResponse searchProduct(HttpSession session, Integer productId, String productName,
                                        @RequestParam(value = "pageNum", defaultValue = "1")Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10")Integer pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆,请登陆管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return productService.searchProduct(productId, productName, pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("当前身份无对应权限");
        }
    }

    @RequestMapping("uploadFile.do")
    @ResponseBody
    public ServerResponse uploadFile(HttpSession session,
                                     @RequestParam(value = "upload_file", required = false)MultipartFile upload_file,
                                     HttpServletRequest request){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = fileService.upload(upload_file,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;

            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url",url);
            return ServerResponse.createBySuccessMessage(fileMap);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }
}














