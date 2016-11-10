package com.fengjie.model.dbhelper.PickCheckout;

/**
 * Created by xiong on 2016/4/18.
 * 按员工排序结账时指定员工的详细信息对象类
 * 包含：
 * pick_datetime 采茶日期，对应tb_pick_tea_Info的pick_datetime字段
 * tea_category 茶叶种类，对应tb_tea_Info的tea_category字段
 * tea_price 茶叶价格，对应tb_tea_Info的tea_price字段
 * pick_weight 茶叶重量，对应tb_pick_tea_Info的pick_weight字段
 * pick_income 当次采茶收入，对应tb_pick_tea_Info的pick_income字段
 * serial_number 当次记录编号，对应tb_pick_tea_Info的serial_number字段
 * pick_isCheckout 是否已结帐，对应tb_pick_tea_info的pick_isCheckout字段
 */
public class CheckoutByStaff_ListInfo_Detail
{
    private int pick_isCheckout;
    private String pick_datetime;
    private String tea_category;
    private float tea_price;
    private float pick_weight;
    private float pick_income;
    private String serial_number;

    public int getPick_isCheckout()
    {
        return pick_isCheckout;
    }

    public void setPick_isCheckout(int pick_isCheckout)
    {
        this.pick_isCheckout = pick_isCheckout;
    }

    public String getPick_datetime()
    {
        return pick_datetime;
    }

    public void setPick_datetime(String pick_datetime)
    {
        this.pick_datetime = pick_datetime;
    }

    public String getTea_category()
    {
        return tea_category;
    }

    public void setTea_category(String tea_category)
    {
        this.tea_category = tea_category;
    }

    public float getTea_price()
    {
        return tea_price;
    }

    public void setTea_price(float tea_price)
    {
        this.tea_price = tea_price;
    }

    public float getPick_weight()
    {
        return pick_weight;
    }

    public void setPick_weight(float pick_weight)
    {
        this.pick_weight = pick_weight;
    }

    public float getPick_income()
    {
        return pick_income;
    }

    public void setPick_income(float pick_income)
    {
        this.pick_income = pick_income;
    }

    public String getSerial_number()
    {
        return serial_number;
    }

    public void setSerial_number(String serial_number)
    {
        this.serial_number = serial_number;
    }
}
