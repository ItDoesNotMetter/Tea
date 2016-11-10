package com.fengjie.model.dbhelper.ManagerIncome;

/**
 * Created by xiong on 2016/4/16.
 * 管理员收入信息表
 * 包括编号id、管理员姓名admin_name、收入金额income_money、收入来源income_from和日期income_date
 */
public class IncomeInfo
{

    private int id;
    private String admin_name;
    private float income_money;
    private String income_from;
    private String income_date;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getAdmin_name()
    {
        return admin_name;
    }

    public void setAdmin_name(String admin_name)
    {
        this.admin_name = admin_name;
    }

    public float getIncome_money()
    {
        return income_money;
    }

    public void setIncome_money(float income_money)
    {
        this.income_money = income_money;
    }

    public String getIncome_from()
    {
        return income_from;
    }

    public void setIncome_from(String income_from)
    {
        this.income_from = income_from;
    }

    public String getIncome_date()
    {
        return income_date;
    }

    public void setIncome_date(String income_date)
    {
        this.income_date = income_date;
    }
}
