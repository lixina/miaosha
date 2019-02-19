package com.miaoshaproject.servive;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.servive.model.ItemModel;

import java.util.List;

public interface ItemService {

    // 创建商品
    ItemModel create(ItemModel itemModel);

    // 商品列表浏览
    List<ItemModel> listItem();

    // 商品浏览详情
    ItemModel getItemById(Integer id);

    // 减少库存
    boolean decreaseStock(Integer itemId,Integer amount)throws BusinessException;

    // 增加销量
    void addsales(Integer itemId, Integer amount)throws BusinessException;

}
