package com.miaoshaproject.servive;

import com.miaoshaproject.servive.model.PromoModel;

/**
 * 秒杀活动
 */
public interface PromoService {
    // 根据itemId获取即将进行的或者正在进行的秒杀活动
    PromoModel getPromoByItemId(Integer itemId);
}
