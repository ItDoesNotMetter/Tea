package com.fengjie.model.dbhelper.Staff;


/**
 * Created by xiong on 2016/4/15.
 * 工人信息类
 * 包含员工姓名、员工性别、员工电话、员工身份证等属性
 * 员工编号、添加时间由系统自动生成
 */
public class StaffInfo
{
    private String Staff_Name;
    private String Staff_Sex;
    private String Staff_Phone;
    private String Staff_NRIC;
    private String Add_Time;
    private int staff_id;

    public StaffInfo ()
    {

    }

    public StaffInfo ( String staff_Name, String staff_Phone, String staff_NRIC )
    {
        Staff_Name = staff_Name;
        Staff_Phone = staff_Phone;
        Staff_NRIC = staff_NRIC;
    }

    public StaffInfo ( final String staff_Name, final String staff_Sex, final String staff_Phone, final String staff_NRIC)
    {
        Staff_NRIC = staff_NRIC;
        Staff_Phone = staff_Phone;
        Staff_Sex = staff_Sex;
        Staff_Name = staff_Name;
    }

    /**
     * 获取员工姓名
     * @return 员工姓名
     */
    public String getStaff_Name()
    {
        return Staff_Name;
    }


    /**
     * 设置员工姓名
     * @param staff_Name 员工姓名
     */
    public void setStaff_Name(String staff_Name)
    {
        Staff_Name = staff_Name;
    }

    /**
     * 获取员工性别
     * @return 员工性别
     */
    public String getStaff_Sex()
    {
        return Staff_Sex;
    }

    /**
     * 设置员工性别
     * @param staff_Sex 员工性别
     */
    public void setStaff_Sex(String staff_Sex)
    {
        Staff_Sex = staff_Sex;
    }

    /**
     * 获取员工电话
     * @return 员工电话
     */
    public String getStaff_Phone()
    {
        return Staff_Phone;
    }

    /**
     * 设置员工电话
     * @param staff_Phone 员工电话
     */
    public void setStaff_Phone(String staff_Phone)
    {
        Staff_Phone = staff_Phone;
    }

    /**
     * 获取员工身份证
     * @return
     */
    public String getStaff_NRIC()
    {
        return Staff_NRIC;
    }

    /**
     * 设置员工身份证
     * @param staff_NRIC
     */
    public void setStaff_NRIC(String staff_NRIC)
    {
        Staff_NRIC = staff_NRIC;
    }

    public String getAdd_Time()
    {
        return Add_Time;
    }

    public void setAdd_Time(String add_Time)
    {
        Add_Time = add_Time;
    }

    public int getStaff_id()
    {
        return staff_id;
    }

    public void setStaff_id(int staff_id)
    {
        this.staff_id = staff_id;
    }

}
