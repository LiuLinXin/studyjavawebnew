package com.mmall.controller.portal;

import com.mmall.common.ServerResponse;
import com.mmall.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by philer on 2019/1/29.
 */
@RequestMapping("/product/")
@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse detail(Integer productId){
        return productService.getProductDetail(productId);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse search(@RequestParam(value = "keyword", required = false)String keyword,
                                 @RequestParam(value = "categoreId", required = false)Integer categoreId,
                                 @RequestParam(value = "pageNum", defaultValue = "1")Integer pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "10")Integer pageSize,
                                 @RequestParam(value = "param", defaultValue = "")String orderBy){
        return productService.getProductByKeyWordCategory(keyword, categoreId, pageNum, pageSize, orderBy);
    }
}
