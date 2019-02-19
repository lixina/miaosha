package com.miaoshaproject.servive.impl;

import com.miaoshaproject.dao.PromoDOMapper;
import com.miaoshaproject.dataobject.PromoDO;
import com.miaoshaproject.servive.PromoService;
import com.miaoshaproject.servive.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PromoServiceImpl implements PromoService {
    @Autowired
    private PromoDOMapper promoDOMapper;
    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        // 获取对应商品的秒杀活动信息
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);
        PromoModel promoModel = convertModelFromDO(promoDO);
        if (promoModel == null){
            return null;
        }
        // 判断秒杀活动的状态
        if (promoModel.getStartTime().isAfterNow()){
            promoModel.setStatus(1);
        }else if (promoModel.getEndTime().isBeforeNow()){
            promoModel.setStatus(3);
        }else{
            promoModel.setStatus(2);
        }
        return promoModel;
    }
    private PromoModel convertModelFromDO(PromoDO promoDO){
        PromoModel promoModel = new PromoModel();
        if (promoDO == null){
            return null;
        }
        BeanUtils.copyProperties(promoDO, promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setStartTime(new DateTime(promoDO.getStartDate()));
        promoModel.setEndTime(new DateTime(promoDO.getEndDate()));
        // 判断当前时间
        return promoModel;
    }
}
