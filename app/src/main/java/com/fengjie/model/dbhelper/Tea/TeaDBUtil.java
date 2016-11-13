package com.fengjie.model.dbhelper.Tea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fengjie.model.dbhelper.Other.TeaDBHelper;
import com.fengjie.model.helper.Other.TimeHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiong on 2016/4/15.
 * 茶叶信息数据库操作类
 */
public class TeaDBUtil
{
    /**
     * Table Name
     * 表名
     */
    public static final String TB_TEA_INFO="tb_tea_info";

    /**
     * Column Name
     * 字段名
     */
    /**
     * tid 茶叶编号
     */
    private static final String TEA_ID="tid";

    /**
     * tea_category 茶叶种类
     */
    private static final String TEA_CATEGORY="tea_category";

    /**
     * tea_price 茶叶价格
     */
    private static final String TEA_PRICE="tea_price";

    /**
     * tea_datetime 定价日期
     */
    private static final String TEA_DATETIME="tea_datetime";

    private static TeaDBUtil teaDBUtil;
    private SQLiteDatabase database;

    /**
     * 构造方法私有化
     * @param context
     */
    private TeaDBUtil(Context context)
    {
        TeaDBHelper dbHelper=new TeaDBHelper(context, TeaDBHelper.DB_NAME,null,TeaDBHelper.DATABASE_VERSION);
        database=dbHelper.getWritableDatabase();
    }

    /**
     * 获取TeaDBUtil实例
     * @param context
     * @return
     */
    public synchronized static TeaDBUtil getInstance(Context context)
    {
        return teaDBUtil==null?new TeaDBUtil(context):teaDBUtil;
    }

    /**
     * 添加茶叶信息
     * @param teaInfo 茶叶信息对象
     * @return >0 添加的茶叶编号
     * @return <0 添加失败，数据库出错
     * @return  0 添加失败，teaInfo为空
     */
    public int insertTea(TeaInfo teaInfo)
    {
        if (teaInfo!=null)
        {
            ContentValues values=new ContentValues();
            values.put(TEA_CATEGORY,teaInfo.getTea_Category());
            values.put(TEA_PRICE,teaInfo.getTea_Price());
            return (int) database.insert(TB_TEA_INFO,null,values);
        }
        else
        {
            return 0;
        }
    }

    /**
     * 删除茶叶信息
     * @param tea_category 删除的茶叶种类
     * @return >0 删除的记录数
     * @return <0 删除失败，数据库出错
     */
    public int deleteTea(String tea_category)
    {
        return (int)database.delete(TB_TEA_INFO,TEA_CATEGORY+"==?",new String[]{tea_category});
    }

    /**
     * 更新茶叶信息
     * @param teaInfo 茶叶信息对象
     * @return >0 更新的记录数<br></><0 更新失败，数据库出错<br></>0 更新失败，传入的参数有误
     */
    public int updateTea(TeaInfo teaInfo)
    {
        if((database.query(TB_TEA_INFO,null,TEA_ID+"==?",new String[]{teaInfo.getTea_Id()+""},null,null,null)).moveToFirst())
        {
            ContentValues values=new ContentValues();
            values.put(TEA_PRICE,teaInfo.getTea_Price());
            values.put(TEA_DATETIME, TimeHelper.getCurrentDateTime());
            return database.update(TB_TEA_INFO,values,TEA_ID+"==?",new String[]{teaInfo.getTea_Id()+""});
        }
        return 0;
    }

    /**
     *
     * @param teaInfo
     * @return
     */
    public int updateTeaUseCategory ( TeaInfo teaInfo)
    {
        if((database.query(TB_TEA_INFO,null,TEA_CATEGORY+"==?",new String[]{teaInfo.getTea_Category()},null,null,null)).moveToFirst())
        {
            ContentValues values=new ContentValues();
            values.put(TEA_PRICE,teaInfo.getTea_Price());
            values.put(TEA_DATETIME, TimeHelper.getCurrentDateTime());
            return database.update(TB_TEA_INFO,values,TEA_CATEGORY+"==?",new String[]{teaInfo.getTea_Category()});
        }
        return 0;
    }

    /**
     * 按茶叶编号查找茶叶信息
     * @param tid 茶叶编号
     * @return 茶叶对象
     */
    public TeaInfo selectTea(int tid)
    {
        TeaInfo info=new TeaInfo();
        Cursor cursor=database.query(TB_TEA_INFO,null,TEA_ID+"==?",new String[]{tid+""},null,null,null);
        if (cursor.moveToFirst())
        {
            info.setTea_Id(cursor.getInt(cursor.getColumnIndex(TEA_ID)));
            info.setTea_Category(cursor.getString(cursor.getColumnIndex(TEA_CATEGORY)));
            info.setTea_Price(cursor.getFloat(cursor.getColumnIndex(TEA_PRICE)));
            info.setTea_Datetime(cursor.getString(cursor.getColumnIndex(TEA_DATETIME)));
            cursor.close();
        }
        return info;
    }

    /**
     * 根据茶叶种类查找茶叶信息
     * @param tra_category 茶叶种类
     * @return 茶叶对象
     */
    public TeaInfo selectTea(String tra_category)
    {
        TeaInfo info=new TeaInfo();
        Cursor cursor=database.query(TB_TEA_INFO,null,TEA_CATEGORY+"==?",new String[]{tra_category},null,null,null);
        if (cursor.moveToFirst())
        {
            info.setTea_Id(cursor.getInt(cursor.getColumnIndex(TEA_ID)));
            info.setTea_Category(cursor.getString(cursor.getColumnIndex(TEA_CATEGORY)));
            info.setTea_Price(cursor.getFloat(cursor.getColumnIndex(TEA_PRICE)));
            info.setTea_Datetime(cursor.getString(cursor.getColumnIndex(TEA_DATETIME)));
            cursor.close();
        }
        return info;
    }

    /**
     * 查找所有茶叶信息
     * @return 茶叶信息对象列表
     */
    public List<TeaInfo> seleteAllTea()
    {
        List<TeaInfo> list=new ArrayList<>();
        Cursor cursor=database.query(TB_TEA_INFO,null,null,null,null,null,null);
        if (cursor.moveToFirst())
        {
           do
           {
               TeaInfo info=new TeaInfo();
               info.setTea_Id(cursor.getInt(cursor.getColumnIndex(TEA_ID)));
               info.setTea_Category(cursor.getString(cursor.getColumnIndex(TEA_CATEGORY)));
               info.setTea_Price(cursor.getFloat(cursor.getColumnIndex(TEA_PRICE)));
               info.setTea_Datetime(cursor.getString(cursor.getColumnIndex(TEA_DATETIME)));
               list.add(info);
           }
           while (cursor.moveToNext());
        }
        if (cursor!=null)
        {
            cursor.close();
        }
        return list;
    }

}
