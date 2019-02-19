package com.miaoshaproject.servive.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;

public class PromoModel {
    private Integer id;

    // 活动名称
    private String promoName;

    // 开始时间
    private DateTime startTime;

    // 结束时间
    private DateTime endTime;

    // 秒杀活动的适用商品
    private Integer itemId;

    // 秒杀活动的状态 1:未开始2：正在进行3：已结束
    private Integer status;

    // 秒杀活动的商品价格
    private BigDecimal promoItemPrice;

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
