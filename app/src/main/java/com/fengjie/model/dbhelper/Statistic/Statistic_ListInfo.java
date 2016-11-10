package com.fengjie.model.dbhelper.Statistic;


import com.fengjie.model.baseClass.SortNameData;

/**
 * Created by xiongda on 2016/5/15.
 * uid 员工编号，按人员排序时使用
 * StaffName 员工姓名，按人员排序时使用
 * TotalMoney 金额
 * RecordNumbers 记录数
 * mTime 时间，按时间排序时使用
 * CheckoutRecordSerial 账单编号,按结账记录排序时使用
 */
public class Statistic_ListInfo extends SortNameData
{
    private int uid;
    private String StaffName;
    private float TotalMoney;
    private int RecordNumbers;
    private String mTime;
    private String CheckoutRecordSerial;

    /**
     * 获取员工姓名
     * 按人员排序时使用
     * @return
     */
    public String getStaffName()
    {
        return StaffName;
    }


    public void setStaffName(String staffName)
    {
        StaffName = staffName;
    }

    public float getTotalMoney()
    {
        return TotalMoney;
    }

    public void setTotalMoney(float totalMoney)
    {
        TotalMoney = totalMoney;
    }

    public int getRecordNumbers()
    {
        return RecordNumbers;
    }

    public void setRecordNumbers(int recordNumbers)
    {
        RecordNumbers = recordNumbers;
    }

    public String getmTime()
    {
        return mTime;
    }

    public void setmTime(String mTime)
    {
        this.mTime = mTime;
    }

    public String getCheckoutRecordSerial()
    {
        return CheckoutRecordSerial;
    }

    public void setCheckoutRecordSerial(String checkoutRecordSerial)
    {
        CheckoutRecordSerial = checkoutRecordSerial;
    }

    public int getUid()
    {
        return uid;
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }
}
