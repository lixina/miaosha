package com.miaoshaproject.servive.impl;

import com.miaoshaproject.dao.ItemDOMapper;
import com.miaoshaproject.dao.ItemStockDOMapper;
import com.miaoshaproject.dao.SequenceDOMapper;
import com.miaoshaproject.dataobject.ItemDO;
import com.miaoshaproject.dataobject.ItemStockDO;
import com.miaoshaproject.dataobject.SequenceDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.servive.ItemService;
import com.miaoshaproject.servive.PromoService;
import com.miaoshaproject.servive.model.ItemModel;
import com.miaoshaproject.servive.model.PromoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemDOMapper itemDOMapper;
    @Autowired
    private ItemStockDOMapper itemStockDOMapper;
    @Autowired
    private PromoService promoService;
    /**
     * 创建商品
     * @param itemModel
     * @return
     */
    @Override
    @Transactional
    public ItemModel create(ItemModel itemModel) {
        // 校验入参

        // 转化itemModel->dataObjct
        ItemDO itemDO = this.convertItemDoFromItemModel(itemModel);
        // 写入数据库
        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());

        ItemStockDO itemStockDO = this.convertStockDoFromItemModel(itemModel,itemDO);
        itemStockDOMapper.insertSelective(itemStockDO);

        // 返回创建完成的对象
        return this.getItemById(itemModel.getId());
    }

    /**
     * 获取商品列表
     * @return
     */
    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> itelDOList = itemDOMapper.listItem();
        List<ItemModel> itemModelList = itelDOList.stream().map(itemDO -> {
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel = this.convertModelFromDataObject(itemDO, itemStockDO);
            return itemModel;
        }).collect(Collectors.toList());

        return itemModelList;
    }

    /**
     * 根据商品id获取商品明细
     * @param id
     * @return
     */
    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if (itemDO == null){
            return null;
        }
        // 操作获得库存数量
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());

        ItemModel  itemModel = convertModelFromDataObject(itemDO, itemStockDO);
        // 获取商品活动信息
        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        if (promoModel != null && promoModel.getStatus() != 3){
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }

    /**
     * 减少商品库存
     * @param itemId
     * @param amount
     * @return
     * @throws BusinessException
     */
    @Override
    public boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException {
        int affactedRow = itemStockDOMapper.decreaseStock(itemId, amount);
        if (affactedRow > 0){
            // 更新库存成功
            return true;
        }
        // 更新库存失败
        return false;
    }

    /**
     * 增加商品销量
     * @param itemId
     * @param amount
     * @throws BusinessException
     */
    @Override
    @Transactional
    public void addsales(Integer itemId, Integer amount) throws BusinessException {
        itemDOMapper.addSales(itemId, amount);
    }

    /**
     * 将ItemDO 和 ItemStockDO转化为ItemModel
     * @param itemDO
     * @param itemStockDO
     * @return
     */
    private ItemModel convertModelFromDataObject(ItemDO itemDO, ItemStockDO itemStockDO){
        ItemModel itemModel = new ItemModel();
       /* BeanUtils.copyProperties(itemDO,itemStockDO);*/
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());
        itemModel.setTitle(itemDO.getTitle());
        itemModel.setDescription(itemDO.getDescription());
        itemModel.setImgUrl(itemDO.getImgUrl()  );
        itemModel.setId(itemDO.getId());
        itemModel.setSales(itemDO.getSales());

        return itemModel;
    }

    /**
     * 将ItemModel转化为ItemDO
     * @param itemModel
     * @return
     */
    private ItemDO convertItemDoFromItemModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }

    /**
     * 将ItemModel转化为StockDO
     * @param itemModel
     * @return
     */
    private ItemStockDO convertStockDoFromItemModel(ItemModel itemModel,ItemDO itemDO){
        if(itemModel == null){
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }
}
