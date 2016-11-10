package com.fengjie.model.dbhelper.Tea;

/**
 * Created by xiong on 2016/4/15.
 * 茶叶信息表
 * 包括茶叶编号tid、茶叶种类tea_category、茶叶价格tea_price,单位:元/千克、定价日期
 */
public class TeaInfo
{

    private int Tea_Id;
    private String Tea_Category;
    private float  Tea_Price;
    private String Tea_Datetime;
    public TeaInfo() {

    }

    public TeaInfo( final String tea_Category, final float Tea_Price) {
        Tea_Category = tea_Category;
        this.Tea_Price = Tea_Price;
    }

    public int getTea_Id()
    {
        return Tea_Id;
    }

    public void setTea_Id(int tea_Id)
    {
        Tea_Id = tea_Id;
    }

    public String getTea_Category()
    {
        return Tea_Category;
    }

    public void setTea_Category(String tea_Category)
    {
        Tea_Category = tea_Category;
    }

    public float getTea_Price()
    {
        return Tea_Price;
    }

    public void setTea_Price(float tea_Price)
    {
        Tea_Price = tea_Price;
    }

    public String getTea_Datetime()
    {
        return Tea_Datetime;
    }

    public void setTea_Datetime(String tea_Datetime)
    {
        Tea_Datetime = tea_Datetime;
    }



}
