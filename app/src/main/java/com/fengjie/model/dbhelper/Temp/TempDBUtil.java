package com.fengjie.model.dbhelper.Temp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fengjie.model.dbhelper.Other.TeaDBHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xiong on 2016/4/15.
 * 茶叶信息数据库操作类
 */
public class TempDBUtil
{
    /**
     * Table Name
     * 表名
     */
    public static final String TB_TEMP="tb_temp";

    /**
     * Column Name
     * 字段名
     */
    private static final String TEMP_ID = "id";
    private static final String TEMP_MD5 = "md5_value";
    private static final String TEMP_NUMBER = "number_list";

    private static TempDBUtil tempDBUtil;
    private SQLiteDatabase database;

    /**
     * 构造方法私有化
     * @param context
     */
    private TempDBUtil(Context context)
    {
        TeaDBHelper dbHelper=new TeaDBHelper(context,TeaDBHelper.DB_NAME,null,TeaDBHelper.DATABASE_VERSION);
        database=dbHelper.getWritableDatabase();
    }

    /**
     * 获取TempDBUtil实例
     * @param context
     * @return
     */
    public synchronized static TempDBUtil getInstance(Context context)
    {
        return tempDBUtil ==null?new TempDBUtil(context): tempDBUtil;
    }

    /**
     * 添加茶叶信息
     * @param Info 临时信息对象
     * @return >0 成功添加
     * @return <0 添加失败，数据库出错
     * @return  0 添加失败，Info为空
     */
    public int insertTempinfo(TempInfo Info)
    {
        if (Info!=null)
        {
            ContentValues values=new ContentValues();
            values.put(TEMP_MD5,Info.getMd5_Value());
            values.put(TEMP_NUMBER,Info.getAllNumber());
            return (int) database.insert(TB_TEMP,null,values);
        }
        else
        {
            return 0;
        }
    }

    /**
     * 删除
     * @param
     * @return >0 删除的记录数
     * @return <0 删除失败，数据库出错
     */
    public int deleteTea(String MD5)
    {
        return (int)database.delete(TB_TEMP,TEMP_NUMBER+"==?",new String[]{MD5});
    }

    /**
     * 更新茶叶信息
     * @param Info 茶叶信息对象
     * @return >0 更新的记录数<br></><0 更新失败，数据库出错<br></>0 更新失败，传入的参数有误
     */
//    public int updateTea(TempInfo Info)
//    {
//        if((database.query(TB_TEMP,null,TEMP_ID+"==?",new String[]{Info.getTemp_Id()+""},null,null,null)).moveToFirst())
//        {
//            ContentValues values=new ContentValues();
//            values.put(TEMP_MD5,Info.getMd5_Value());
//            values.put(TEMP_NUMBER,Info.getAllNumber());
//            return database.update(TB_TEMP,values,TEMP_ID+"==?",new String[]{Info.getTemp_Id()+""});
//        }
//        return 0;
//        SQLiteDatabase db=null;
//        db.execSQL("UPDATE tb_temp SET " + TEMP_MD5 " = " +Info.getMd5_Value()
//                );
//    }

    /**
     * 按茶叶编号查找茶叶信息
     * @param id 茶叶编号
     * @return 茶叶对象
     */
    public List<String> selectTea( int id, String MD5Value)
    {
        List<String> info=new LinkedList<String>();
        /****开启事务  WHERE条件-索引ID和验证MD5值 ****/
        Cursor cursor=database.query(TB_TEMP,null,TEMP_ID+"==?"+" And "+TEMP_MD5+"==?",new String[]{id+"",MD5Value},null,null,null);
        if (cursor.moveToFirst())       //移动搜索
        {
            String tempString = cursor.getString(cursor.getColumnIndex(TEMP_NUMBER));       //获得所有编号
            for(int i = 0;i<tempString.length()/22;i++){        //未结账编码22位
                info.add(i,tempString.substring(i*22,(i+1)*22));
            }
            cursor.close();
        }
        return info;
    }

    public List<String> selectTea( String MD5Value)
    {
        List<String> info=new ArrayList<>();
        /****开启事务  WHERE条件-索引ID和验证MD5值 ****/
        Cursor cursor=database.query(TB_TEMP,null,TEMP_MD5+"==?",new String[]{MD5Value},null,null,null);
        if (cursor.moveToFirst())       //移动搜索
        {
            String tempString = cursor.getString(cursor.getColumnIndex(TEMP_NUMBER));       //获得所有编号
            for(int i = 0;i<tempString.length()/22;i++){        //未结账编码22位
                info.add(i,tempString.substring(i*22,(i+1)*22));
            }
            cursor.close();
        }
        return info;
    }
    /**
     * 根据茶叶种类查找茶叶信息
     * @param tra_category 茶叶种类
     * @return 茶叶对象
     */
//    public TempInfo selectTea(String tra_category)
//    {
//        TeaInfo info=new TeaInfo();
//        Cursor cursor=database.query(TB_TEMP,null,TEA_CATEGORY+"==?",new String[]{tra_category},null,null,null);
//        if (cursor.moveToFirst())
//        {
//            info.setTea_Id(cursor.getInt(cursor.getColumnIndex(TEA_ID)));
//            info.setTea_Category(cursor.getString(cursor.getColumnIndex(TEA_CATEGORY)));
//            info.setTea_Price(cursor.getFloat(cursor.getColumnIndex(TEA_PRICE)));
//            info.setTea_Datetime(cursor.getString(cursor.getColumnIndex(TEA_DATETIME)));
//            cursor.close();
//        }
//        return info;
//    }

//    /**
//     * 查找所有茶叶信息
//     * @return 茶叶信息对象列表
//     */
//    public List<TeaInfo> seleteAllTea()
//    {
//        List<TeaInfo> list=new ArrayList<>();
//        Cursor cursor=database.query(TB_TEMP,null,null,null,null,null,null);
//        if (cursor.moveToFirst())
//        {
//           do
//           {
//               TeaInfo info=new TeaInfo();
//               info.setTea_Id(cursor.getInt(cursor.getColumnIndex(TEA_ID)));
//               info.setTea_Category(cursor.getString(cursor.getColumnIndex(TEA_CATEGORY)));
//               info.setTea_Price(cursor.getFloat(cursor.getColumnIndex(TEA_PRICE)));
//               info.setTea_Datetime(cursor.getString(cursor.getColumnIndex(TEA_DATETIME)));
//               list.add(info);
//           }
//           while (cursor.moveToNext());
//        }
//        if (cursor!=null)
//        {
//            cursor.close();
//        }
//        return list;
//    }

}
