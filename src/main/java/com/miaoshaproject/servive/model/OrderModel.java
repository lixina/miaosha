package com.miaoshaproject.servive.model;

import java.math.BigDecimal;

/**
 * 用户下单的交易模型
 */
public class OrderModel {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    // 订单号
    private String id;
    // 用户id
    private Integer userId;
    // 商品id
    private Integer itemId;
    // 购买金额
    private BigDecimal itemPrice;
    // 数量
    private Integer amount;
    // 金额
    private BigDecimal orderPrice;
    // 若非空 则为秒杀
    private Integer promoId;

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }
}





