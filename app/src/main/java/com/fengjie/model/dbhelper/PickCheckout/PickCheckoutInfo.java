package com.fengjie.model.dbhelper.PickCheckout;

import java.util.List;

/**
 * Created by xiong on 2016/4/15.
 * 采茶结算信息表
 */
public class PickCheckoutInfo
{
    private int id;
    private String checkout_date;
    private float checkout_money;
    private List<String> checkout_serial_numbers_list;
    private String checkout_record_serial;

    public PickCheckoutInfo() {

    }

    public PickCheckoutInfo( int id, String checkout_date, float checkout_money, List<String> checkout_serial_numbers_list, String checkout_record_serial) {
        this.id = id;
        this.checkout_date = checkout_date;
        this.checkout_money = checkout_money;
        this.checkout_serial_numbers_list = checkout_serial_numbers_list;
        this.checkout_record_serial = checkout_record_serial;
    }

    /**
     * 结算日期
     * @return 结算日期
     */
    public String getCheckout_date()
    {
        return checkout_date;
    }

    public void setCheckout_date(String checkout_date)
    {
        this.checkout_date = checkout_date;
    }

    /**
     * 结算金额
     * @return 结算金额
     */
    public float getCheckout_money()
    {
        return checkout_money;
    }

    public void setCheckout_money(float checkout_money)
    {
        this.checkout_money = checkout_money;
    }

    /**
     * 结算单编号
     * @return
     */
    public String getCheckout_serial()
    {
        return checkout_record_serial;
    }

    public void setCheckout_serial(String checkout_record_serial)
    {
        this.checkout_record_serial = checkout_record_serial;
    }

    /**
     * 结算的记录编号集合
     * @return
     */
    public List<String> getCheckout_serial_numbers_list()
    {
        return checkout_serial_numbers_list;
    }

    public void setCheckout_serial_numbers_list(List<String> checkout_serial_numbers_list)
    {
        this.checkout_serial_numbers_list = checkout_serial_numbers_list;
    }

    /**
     * 管理员编号
     * @return
     */
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
