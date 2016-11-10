package com.fengjie.model.dbhelper.PickCheckout;


import com.fengjie.model.baseClass.SortNameData;

/**
 * Created by xiong on 2016/4/18.
 * 按员工排序结账时的列表信息对象类
 * 包含：
 * uid 员工编号，对应tb_staff_Info的uid字段
 * staff_name 员工姓名，对应tb_staff_Info的staff_name字段
 *  count  员工未结账记录的总数
 *  weight 员工未结账记录的采茶总量
 *  income 员工未结账的总金额
 */
public class CheckoutByStaff_ListInfo  extends SortNameData
{
    private int uid;
    private String staff_name;
    private int count;
    private float weight;
    private float income;

    public int getUid()
    {
        return uid;
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }

    public String getStaff_name()
    {
        return staff_name;
    }

    public void setStaff_name(String staff_name)
    {
        this.staff_name = staff_name;
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
