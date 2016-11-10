package com.fengjie.model.dbhelper.ManagerIncome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fengjie.model.dbhelper.Other.TeaDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiong on 2016/4/16.
 * 管理员收入信息数据库操作类
 */
public class IncomeDBUtil
{

    /**
     * Table Name
     * 表名
     */
    private final static String TB_INCOME = "tb_income";

    /**
     * Column Name
     * id 编号
     */
    private final static String ID = "id";

    /**
     * Column Name
     * admin_name 管理员姓名
     */
    private final static String ADMIN_NAME = "admin_name";

    /**
     * Column Name
     * income_money 收入金额
     */
    private final static String INCOME_MONEY = "income_money";

    /**
     * Column Name
     * income_from 收入来源
     */
    private final static String INCOME_FROM = "income_from";


    /**
     * Column Name
     * income_date 收入日期
     */
    private final static String INCOME_DATE = "income_date";

    private static IncomeDBUtil incomeDBUtil;

    private SQLiteDatabase database;

    /**
     * 构造方法私有化
     *
     * @param context
     */
    private IncomeDBUtil(Context context)
    {
        TeaDBHelper dbHelper = new TeaDBHelper(context, TeaDBHelper.DB_NAME, null, TeaDBHelper.DATABASE_VERSION);
        database = dbHelper.getWritableDatabase();
    }

    public synchronized static IncomeDBUtil getInstance(Context context)
    {
        return incomeDBUtil == null ? new IncomeDBUtil(context) : incomeDBUtil;
    }

    /**
     * 录入管理员收入信息
     *
     * @param incomeInfo 管理员收入信息对象
     * @return >0 插入的记录数
     * @return  0 插入失败，传入的参数有误
     * @return <0 插入失败，数据库操作有误
     */
    public int insertIncomeInfo(IncomeInfo incomeInfo)
    {
        if (incomeInfo != null)
        {
            ContentValues values = new ContentValues();
            values.put(ID, incomeInfo.getId());
            values.put(INCOME_DATE, incomeInfo.getIncome_date());
            values.put(ADMIN_NAME, incomeInfo.getAdmin_name());
            values.put(INCOME_FROM, incomeInfo.getIncome_from());
            values.put(INCOME_MONEY, incomeInfo.getIncome_money());
            return (int)database.insert(TB_INCOME, null, values);
        }
        return 0;
    }

    /**
     * 删除管理员收入信息
     *
     * @param id 收入信息id
     * @return >0 删除的记录数
     * @return  0 删除失败，id不存在
     * @return <0 删除失败，数据库操作有误
     */
    public int deleteIncomeInfo(int id)
    {
        if ((database.query(TB_INCOME, null,
                ID + "==?", new String[]{id + ""}, null, null, null)).moveToFirst())
        {
            return database.delete(TB_INCOME, ID + "==?", new String[]{id + ""}) ;
        }
        return 0;
    }

    /**
     * 查找管理员收入信息
     * @param admin_name 管理员姓名
     * @return 管理员收入信息集合
     */
    public List<IncomeInfo> selectAllIncomeInfo( String admin_name)
    {
        List<IncomeInfo> list = new ArrayList<>();
        Cursor cursor = database.query(TB_INCOME, null,
                ADMIN_NAME + "==?", new String[]{admin_name}, null, null, null);
        if (cursor.moveToFirst())
        {
            do
            {
                IncomeInfo incomeInfo = new IncomeInfo();
                incomeInfo.setAdmin_name(cursor.getString(cursor.getColumnIndex(ADMIN_NAME)));
                incomeInfo.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                incomeInfo.setIncome_date(cursor.getString(cursor.getColumnIndex(INCOME_DATE)));
                incomeInfo.setIncome_money(cursor.getFloat(cursor.getColumnIndex(INCOME_MONEY)));
                incomeInfo.setIncome_from(cursor.getString(cursor.getColumnIndex(INCOME_FROM)));
                list.add(incomeInfo);
            } while (cursor.moveToNext());
        }
        if (cursor != null)
        {
            cursor.close();
        }
        return list;
    }


}
