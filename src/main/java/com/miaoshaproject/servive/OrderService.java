package com.miaoshaproject.servive;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.servive.model.OrderModel;

public interface OrderService {
    OrderModel createOrder(Integer userd, Integer itemID, Integer amount, Integer promoId) throws BusinessException;
}
