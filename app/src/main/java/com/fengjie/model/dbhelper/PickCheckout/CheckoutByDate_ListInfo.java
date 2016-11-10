package com.fengjie.model.dbhelper.PickCheckout;


import com.fengjie.model.baseClass.SortNameData;

/**
 * Created by xiong on 2016/4/18.
 * 按时间排序结账时的列表信息对象类
 * 包含：
 *  m_datetime 采茶日期，对应采茶信息表中的pick_datetime字段
 *  count 当日未结账记录的总数
 *  weight 当日未结账记录的采茶总量
 *  income 当日未结账的总金额
 */
public class CheckoutByDate_ListInfo extends SortNameData
{
    private String m_datetime;
    private int count;
    private float weight;
    private float income;

    public String getM_datetime()
    {
        return m_datetime;
    }

    public void setM_datetime(String m_datetime)
    {
        this.m_datetime = m_datetime;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public float getWeight()
    {
        return weight;
    }

    public void setWeight(float weight)
    {
        this.weight = weight;
    }

    public float getIncome()
    {
        return income;
    }

    public void setIncome(float income)
    {
        this.income = income;
    }
}
