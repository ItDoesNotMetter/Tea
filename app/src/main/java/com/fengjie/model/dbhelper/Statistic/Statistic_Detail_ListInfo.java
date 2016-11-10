package com.fengjie.model.dbhelper.Statistic;

/**
 * Created by Asus on 2016/5/24.
 *
 *
 *
 *
 */
public class Statistic_Detail_ListInfo {

    private String pick_datetime;
    private String Tea_Category;
    private String checkout_date;
    private int uid;
    private float pick_weight;
    private float pick_income;
    private float pick_price;
    private String Staff_Name;
    private String serial_number;

    public String getStaff_Name() {
        return Staff_Name;
    }

    public void setStaff_Name(String staff_Name) {
        Staff_Name = staff_Name;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(String checkout_date) {
        this.checkout_date = checkout_date;
    }

    public String getPick_datetime() {
        return pick_datetime;
    }

    public void setPick_datetime(String pick_datetime) {
        this.pick_datetime = pick_datetime;
    }

    public String getTea_Category() {
        return Tea_Category;
    }

    public void setTea_Category(String tea_Category) {
        Tea_Category = tea_Category;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public float getPick_weight() {
        return pick_weight;
    }

    public void setPick_weight(float pick_weight) {
        this.pick_weight = pick_weight;
    }

    public float getPick_income() {
        return pick_income;
    }

    public void setPick_income(float pick_income) {
        this.pick_income = pick_income;
    }

    public float getPick_price() {
        return pick_price;
    }

    public void setPick_price(float pick_price) {
        this.pick_price = pick_price;
    }
}
