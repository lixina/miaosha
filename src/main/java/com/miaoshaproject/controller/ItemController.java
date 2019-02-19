package com.miaoshaproject.controller;

import com.miaoshaproject.controller.ViewProject.ItemVo;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.response.CommenReturnType;
import com.miaoshaproject.servive.ItemService;
import com.miaoshaproject.servive.model.ItemModel;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller("item")
@RequestMapping("/item")
@CrossOrigin(allowedHeaders = "*",allowCredentials = "true")
public class ItemController extends BaseController {
    @Autowired
    private ItemService itemService;

    // 创建商品
    @RequestMapping(value = "create",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_RORMED})
    @ResponseBody
    public CommenReturnType creatItem(@RequestParam(name = "title")String title,
                                      @RequestParam(name = "description")String description,
                                      @RequestParam(name = "price")BigDecimal price,
                                      @RequestParam(name = "stock")Integer stock,
                                      @RequestParam(name = "imgurl")String imgurl
                                      ){
        // 封装service请求用来创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgurl);
        itemModel.setPrice(price);
        itemModel.setDescription(description);
        itemModel.setTitle(title);


        ItemModel itemModelForReturn = itemService.create(itemModel);
        ItemVo itemVo = convertVoFromModel(itemModelForReturn);

        return CommenReturnType.create(itemVo);
    }
    // 商品详情浏览
    @RequestMapping(value = "get",method = {RequestMethod.GET})
    @ResponseBody
    public CommenReturnType creatItem(@RequestParam(name = "id")Integer id){
        ItemModel itemModel = itemService.getItemById(id);

        ItemVo itemVo = convertVoFromModel(itemModel);

        return CommenReturnType.create(itemVo);
    }
    // 商品列表浏览
    @RequestMapping(value = "list",method = {RequestMethod.GET})
    @ResponseBody
    public CommenReturnType itemList(){
        List<ItemModel> itemModelList = itemService.listItem();
        List<ItemVo> itemVoList = itemModelList.stream().map(itemModel -> {
            ItemVo itemVo = this.convertVoFromModel(itemModel);
            return itemVo;
        }).collect(Collectors.toList());
        return CommenReturnType.create(itemVoList);
    }

    private ItemVo convertVoFromModel(ItemModel itemModel){
        if (itemModel == null){
            return null;
        }
        ItemVo itemVo = new ItemVo();
        BeanUtils.copyProperties(itemModel, itemVo);
        if (itemModel.getPromoModel() != null){
            itemVo.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVo.setPromoId(itemModel.getPromoModel().getId());
            itemVo.setStartDate(itemModel.getPromoModel().getStartTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVo.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{
            itemVo.setPromoStatus(0);
        }
        return itemVo;
    }


}
