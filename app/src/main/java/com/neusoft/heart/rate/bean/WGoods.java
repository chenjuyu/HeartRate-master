package com.neusoft.heart.rate.bean;

public class WGoods {
    String GoodsID;

    String Code;

    String GoodName;

    String Imgpath;

    public  WGoods(){} //JSON.parseArray 必须定义这种格式

    public WGoods(String goodsID, String code, String goodName, String imgpath) {
        GoodsID = goodsID;
        Code = code;
        GoodName = goodName;
        Imgpath = imgpath;
    }

    public String getGoodsID() {
        return GoodsID;
    }

    public void setGoodsID(String goodsID) {
        GoodsID = goodsID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getGoodName() {
        return GoodName;
    }

    public void setGoodName(String goodName) {
        GoodName = goodName;
    }

    public String getImgpath() {
        return Imgpath;
    }

    public void setImgpath(String imgpath) {
        Imgpath = imgpath;
    }
}
