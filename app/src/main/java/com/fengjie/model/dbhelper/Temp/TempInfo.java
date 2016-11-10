package com.fengjie.model.dbhelper.Temp;

/**
 * Created by xiong on 2016/4/15.
 * 茶叶信息表
 * 包括茶叶编号tid、茶叶种类tea_category、茶叶价格tea_price,单位:元/千克、定价日期
 */
public class TempInfo
{

    private int temp_Id;
    private String md5_Value;
    private String allNumber;

    public TempInfo( String md5_Value, String allNumber) {
        this.md5_Value = md5_Value;
        this.allNumber = allNumber;
    }

    public String getMd5_Value() {
        return md5_Value;
    }

    public void setMd5_Value(String md5_Value) {
        this.md5_Value = md5_Value;
    }

    public String getAllNumber() {
        return allNumber;
    }

    public void setAllNumber(String allNumber) {
        this.allNumber = allNumber;
    }

    public int getTemp_Id() {
        return temp_Id;
    }

    public void setTemp_Id(int temp_Id) {
        this.temp_Id = temp_Id;
    }




}
