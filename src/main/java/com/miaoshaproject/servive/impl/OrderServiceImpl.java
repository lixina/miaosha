package com.miaoshaproject.servive.impl;

import com.miaoshaproject.dao.OrderDOMapper;
import com.miaoshaproject.dao.SequenceDOMapper;
import com.miaoshaproject.dataobject.OrderDO;
import com.miaoshaproject.dataobject.SequenceDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.servive.ItemService;
import com.miaoshaproject.servive.OrderService;
import com.miaoshaproject.servive.UserService;
import com.miaoshaproject.servive.model.ItemModel;
import com.miaoshaproject.servive.model.OrderModel;
import com.miaoshaproject.servive.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDOMapper orderDOMapper;
    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userid, Integer itemID, Integer amount, Integer promoId) throws BusinessException {

        // 校验下单状态
        ItemModel itemModel = itemService.getItemById(itemID);
        if (itemModel == null) { // 商品是否存在
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品信息不存在");
        }
        UserModel userModel = userService.getUserById(userid);
        if (userModel == null){// 判断用户信息
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息不存在");
        }
        if (amount <= 0 || amount > 99){// 正确的数量信息
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"数量信息不正确");
        }
        // 校验活动信息
        if (promoId != null){
            // 校验活动是否存在
            if (promoId.intValue() != itemModel.getPromoModel().getId()){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
            }else if(itemModel.getPromoModel().getStatus() != 2){
                // 校验活动是否正在进行中
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动未在进行");
            }



        }
        // 落单减库存
        boolean result = itemService.decreaseStock(itemID, amount);
        if (!result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGTH);
        }
        // 订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userid);
        orderModel.setItemId(itemID);
        orderModel.setAmount(amount);
        orderModel.setPromoId(promoId);
        if (promoId != null){
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));
        // 生成订单号
        orderModel.setId(generOrderNo());
        OrderDO orderDO = convertDOFromModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        // 增加商品的销量
        itemService.addsales(itemID, amount);
        // 返回前端
        return orderModel;
    }
    private OrderDO convertDOFromModel(OrderModel orderModel){

        if (orderModel == null) {
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());

        return orderDO;
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generOrderNo(){
        // 16位  前8时间信息  中间六位自增序列  后两位分库分表位
        StringBuilder stringBuilder = new StringBuilder();

        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);

        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue()+sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKey(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 6-sequenceStr.length(); i++){
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);

        stringBuilder.append("00");

        return stringBuilder.toString();
    }
}
