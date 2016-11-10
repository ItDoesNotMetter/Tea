package com.fengjie.model.dbhelper.Staff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fengjie.model.dbhelper.Other.TeaDBHelper;
import com.fengjie.model.dbhelper.PickTea.PickTeaDBUtil;
import com.fengjie.model.helper.Other.TimeHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiong on 2016/4/15.
 * 员工信息数据库操作类
 */
public class StaffDBUtil
{
    /**
     * Table Name
     * 表名
     */
    public static final String TB_STAFF_INFO="tb_staff_info";

    /**
     * Column Name
     * 字段名
     */

    /**
     * uid 员工编号
     */
    private static final String STAFF_ID="uid";
    /**
     * staff_name 员工名
     */
    private static final String STAFF_NAME="staff_name";

    /**
     * staff_sex 性别
     */
    private static final String STAFF_SEX="staff_sex";

    /**
     * staff_phone 员工电话
     */
    private static final String STAFF_PHONE="staff_phone";

    /**
     * staff_NRIC 员工身份证
     */
    private static final String STAFF_NRIC="staff_NRIC";

    /**
     * add_time 添加时间
     */
    private static final String ADD_TIME="add_time";

    private static StaffDBUtil staffDBUtil;
    private SQLiteDatabase database;

    /**
     * 构造方法私有化
     * 创建数据库
     * @param context context
     */
    private StaffDBUtil(Context context)
    {
        TeaDBHelper dbHelper=new TeaDBHelper(context,TeaDBHelper.DB_NAME,null,TeaDBHelper.DATABASE_VERSION);
        database=dbHelper.getWritableDatabase();
    }

    /**
     * 获取StaffDBUtil实例
     */
    public synchronized static StaffDBUtil getInstance(Context context)
    {
        return staffDBUtil==null?new StaffDBUtil(context):staffDBUtil;
    }

    /**
     * 添加工人
     * @param staffInfo 工人对象
     * @return >0 插入的员工的id <br/> =0 插入失败，staffInfo为空 <br/> <<0 插入失败，数据库出错
     */
    public int insertStaff(StaffInfo staffInfo)
    {
        if(staffInfo!=null)
        {
            ContentValues values=new ContentValues();
            values.put(STAFF_NAME,staffInfo.getStaff_Name());
            values.put(STAFF_SEX,staffInfo.getStaff_Sex());
            values.put(STAFF_PHONE,staffInfo.getStaff_Phone());
            values.put(STAFF_NRIC,staffInfo.getStaff_NRIC());
            return (int) database.insert(TB_STAFF_INFO,null,values);
        }
        else
        {
            return  0;
        }
    }

    /**
     * 删除工人
     * @param uid 工人编号
     * @return  0 删除失败，采茶信息表中含有编号为uid的员工记录 <br/> <0 删除失败，数据库出错 <br/> >0 实际删除的记录数
     */
    public int deleteStaff(int uid)
    {
        if (database.query(PickTeaDBUtil.TB_PICK_TEA_INFO,new String[]{STAFF_ID},STAFF_ID+"==?",new String[]{uid+""},null,null,null).moveToFirst())
        {
            return 0;
        }
        return database.delete(TB_STAFF_INFO,STAFF_ID+"==?",new String[]{uid+""});
    }


    /**
     * 更新工人信息
     * @param staffInfo 工人信息
     * @return >0 更新的记录数
     * @return =0 更新失败，staffInfo对象为空
     * @return <0 更新失败，数据库出错
     */
    public int updateStaff(StaffInfo staffInfo)
    {
        if ((database.query(TB_STAFF_INFO,null,STAFF_ID+"==?",new String[]{staffInfo.getStaff_id()+""},null,null,null)).moveToFirst())
        {
            ContentValues values=new ContentValues();
            values.put(STAFF_NAME,staffInfo.getStaff_Name());
            values.put(STAFF_SEX,staffInfo.getStaff_Sex());
            values.put(STAFF_PHONE,staffInfo.getStaff_Phone());
            values.put(STAFF_NRIC,staffInfo.getStaff_NRIC());
            values.put(ADD_TIME, TimeHelper.getCurrentDateTime());
            return database.update(TB_STAFF_INFO,values, STAFF_ID + "==?",new String[]{staffInfo.getStaff_id()+""});
        }
        return 0;
    }

    /**
     * 查找工人
     * @param uid 工人编号
     * @return 查找结果:工人信息对象
     */
    public StaffInfo selectStaff(int uid)
    {
        StaffInfo info=new StaffInfo();
        Cursor cursor= database.query(TB_STAFF_INFO,null,"uid==?",new String[]{uid+""},null,null,null);
        if(cursor.moveToFirst())
        {
            info.setStaff_id(cursor.getInt(cursor.getColumnIndex(STAFF_ID)));
            info.setStaff_Name(cursor.getString(cursor.getColumnIndex(STAFF_NAME)));
            info.setStaff_Sex(cursor.getString(cursor.getColumnIndex(STAFF_SEX)));
            info.setStaff_Phone(cursor.getString(cursor.getColumnIndex(STAFF_PHONE)));
            info.setStaff_NRIC(cursor.getString(cursor.getColumnIndex(STAFF_NRIC)));
            info.setAdd_Time(cursor.getString(cursor.getColumnIndex(ADD_TIME)));
            cursor.close();
        }
        return  info;
    }

//    /**
//     * 查找工人信息
//     * @param staff_name 工人姓名
//     * @return 查找结果:工人信息对象
//     */
//    public StaffInfo selectStaff(String staff_name)
//    {
//        StaffInfo info=new StaffInfo();
//        Cursor cursor= database.query(TB_STAFF_INFO,null, STAFF_NAME + "==?",new String[]{staff_name},null,null,null);
//        if(cursor.moveToFirst())
//        {
//            info.setStaff_id(cursor.getInt(cursor.getColumnIndex(STAFF_ID))+100);//编号从100开始
//            info.setStaff_Name(staff_name);
//            info.setStaff_Sex(cursor.getString(cursor.getColumnIndex(STAFF_SEX)));
//            info.setStaff_Phone(cursor.getString(cursor.getColumnIndex(STAFF_PHONE)));
//            info.setStaff_NRIC(cursor.getString(cursor.getColumnIndex(STAFF_NRIC)));
//            info.setAdd_Time(cursor.getString(cursor.getColumnIndex(ADD_TIME)));
//            cursor.close();
//
//        }
//        return  info;
//    }
    
    /**
     * 查找所有工人信息
     * @return  List<StaffInfo> 工人对象列表
     */
    public List<StaffInfo> selectAllStaff()
    {
       List<StaffInfo> list= new ArrayList<>();
        Cursor cursor= database.query(TB_STAFF_INFO,null, null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            do
            {
                StaffInfo info=new StaffInfo();
                info.setStaff_id(cursor.getInt(cursor.getColumnIndex(STAFF_ID)));
                info.setStaff_Name(cursor.getString(cursor.getColumnIndex(STAFF_NAME)));
                info.setStaff_Sex(cursor.getString(cursor.getColumnIndex(STAFF_SEX)));
                info.setStaff_Phone(cursor.getString(cursor.getColumnIndex(STAFF_PHONE)));
                info.setStaff_NRIC(cursor.getString(cursor.getColumnIndex(STAFF_NRIC)));
                info.setAdd_Time(cursor.getString(cursor.getColumnIndex(ADD_TIME)));
                list.add(info);
            }
            while(cursor.moveToNext());
        }
        if (cursor!=null)
        {
            cursor.close();
        }
        return list;
    }


}
