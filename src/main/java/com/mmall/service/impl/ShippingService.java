package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.annotations.SerializedName;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by philer on 2019/1/30.
 */
@Service("shippingService")
public class ShippingService {

    @Autowired
    ShippingMapper shippingMapper;

    public ServerResponse add(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        int insert = shippingMapper.insert(shipping);
        if(insert > 0){
            Map result = new HashMap();
            result.put("shippingId", shipping.getId());
            return ServerResponse.createBySuccessMessage("新增地址成功", result);
        }
        return ServerResponse.createByErrorMessage("新建地址成功");
    }

    public ServerResponse delete(Integer userId, Integer shippingId){
        if(shippingId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        int i = shippingMapper.deleteByPrimaryKey(shippingId);
        if(i == 1){
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("造作失败");
    }

    public ServerResponse update(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        int i = shippingMapper.updateByPrimaryKey(shipping);
        if(i > 0){
            return ServerResponse.createBySuccessMessage("操作成功");
        }
        return ServerResponse.createByErrorMessage("造作失败");
    }

    public ServerResponse select(Integer userId, Integer shipingId){
        if(shipingId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId, shipingId);
        if(shipping == null){
            return ServerResponse.createByErrorMessage("未找到对应地址");
        }else {
            return ServerResponse.createBySuccessMessage(shipping);
        }
    }

    public ServerResponse list(Integer userId, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippings = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippings);
        return ServerResponse.createBySuccessMessage(pageInfo);
    }
}


















