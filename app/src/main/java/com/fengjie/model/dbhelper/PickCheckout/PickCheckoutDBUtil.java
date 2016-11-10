package com.fengjie.model.dbhelper.PickCheckout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fengjie.model.dbhelper.Other.TeaDBHelper;
import com.fengjie.model.dbhelper.PickTea.PickTeaDBUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xiong on 2016/4/15.
 * 采茶结算表数据库操作类
 */
public class PickCheckoutDBUtil
{
    /**
     * Table Name
     * 表名
     */
    private static final String TB_PICK_CHECKOUT = "tb_pick_checkout";

    /**
     * uid 员工编号
     */
    private static final String ID = "id";

    /**
     * checkout_date 结算日期
     */
    private static final String CHECKOUT_DATE = "checkout_date";

    /**
     * 结算的记录编号集合
     */
    private static final String CHECKOUT_SERIAL_NUMBERS = "checkout_serial_numbers";
    /**
     * checkout_money 结算金额
     */
    private static final String CHECKOUT_MONEY = "checkout_money";

    /**
     * checkout_serial 结算单编号
     */
    private static final String CHECKOUT_RECORD_SERIAL = "checkout_record_serial";

    private static final String SELECT_CHECOUT_SERIAL_NUMBERS_LIST="select checkout_date from tb_pick_checkout where checkout_serial_numbers like ?";

    private static PickCheckoutDBUtil pickCheckoutDBUtil;

    private SQLiteDatabase database;


    /**
     * 构造方法私有化
     *
     * @param context
     */
    private PickCheckoutDBUtil(Context context)
    {

        TeaDBHelper dbHelper = new TeaDBHelper(context, TeaDBHelper.DB_NAME, null, TeaDBHelper.DATABASE_VERSION);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * 获取PickCheckoutDBUtil实例
     *
     * @param context
     * @return
     */
    public synchronized static PickCheckoutDBUtil getInstance(Context context)
    {
        return pickCheckoutDBUtil == null ? new PickCheckoutDBUtil(context) : pickCheckoutDBUtil;
    }

    /**
     * 插入一条PickCheckoutInfo
     * <p>
     * info 需要结账的采茶信息
     *
     * @return 0 插入出错，传入的参数有误<br></> <0 插入失败，数据库操作出错
     */
    public int insertPickCheckoutInfo(PickCheckoutInfo info)
    {
        if (info != null)
        {
            database.beginTransaction();//开启一个事务，保证插入采茶结算信息和更新采茶信息更新的同步
            ContentValues values = new ContentValues();
            values.put(ID, info.getId());
            values.put(CHECKOUT_DATE, info.getCheckout_date());
            String csns = "";
            List<String> NumberList = info.getCheckout_serial_numbers_list();
            int y = 0;
            int x = 0;
            for (int i = 0; i < NumberList.size(); i++)
            {
                String csn = NumberList.get(i);
                if ((y = PickTeaDBUtil.updatePickTeaInfo(database, csn, 1,info.getCheckout_date())) < 0)//更新该条记录为已结帐,并插入结账时间
                {
                    database.endTransaction();
                    return y;
                }
                csns += i < NumberList.size() - 1 ? csn + "|" : csn;
            }
            values.put(CHECKOUT_SERIAL_NUMBERS, csns);
            values.put(CHECKOUT_MONEY, info.getCheckout_money());
            values.put(CHECKOUT_RECORD_SERIAL, info.getCheckout_serial());
            if (database.insert(TB_PICK_CHECKOUT, null, values) > 0)
            {
                x += 1;
            } else
            {
                database.endTransaction();
                return x;
            }
            if (x == y && y > 0 && y > 0)
            {
                database.setTransactionSuccessful();
                database.endTransaction();
                return x;
            }
        }
        return 0;
    }

    /**
     * 查询采茶结算表信息
     * @param id 管理员编号
     * @return 采茶结算记录对象集合
     */
    public List<PickCheckoutInfo> selectPickCheckoutInfo( int id)
    {
        List<PickCheckoutInfo> list = new ArrayList<>();
        Cursor cursor = database.query(TB_PICK_CHECKOUT, null,
                ID + "==?", new String[]{id+""}, null, null, null);
        if (cursor.moveToFirst())
        {
            do
            {
                PickCheckoutInfo info = new PickCheckoutInfo();
                info.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                info.setCheckout_date(cursor.getString(cursor.getColumnIndex(CHECKOUT_DATE)));
                String str=cursor.getString(cursor.getColumnIndex(CHECKOUT_SERIAL_NUMBERS));
                String tmp[]=str.split("\\|");//在正则表达式中有特殊的含义的字符，使用的时候必须进行转义,字符"|","*","+"都得加上转义字符，前面加上"\\"
                List<String> strList= Arrays.asList(tmp);
                info.setCheckout_serial_numbers_list(strList);
                info.setCheckout_money(cursor.getFloat(cursor.getColumnIndex(CHECKOUT_MONEY)));
                info.setCheckout_serial(cursor.getString(cursor.getColumnIndex(CHECKOUT_RECORD_SERIAL)));
                list.add(info);

            } while (cursor.moveToNext());
        }
        if (cursor != null)
        {
            cursor.close();
        }
        return list;
    }


    /**
     *
     * @param serial_number 采茶单号
     * @return checkout_date 根据指定的采茶单号serial_number查询到对应的PickCheckou表记录，再把返回里面的checkout_date
     *                      若未结账则返回"未结账"
     */
    public String select_checkout_date_of_pickteainfo( String serial_number) {
        String checkout_date;
        if (!(database.rawQuery(SELECT_CHECOUT_SERIAL_NUMBERS_LIST, new String[]{"%"+serial_number+"%"})).moveToFirst()) {
            return "未结账";
        } else {
            Cursor cursor = database.rawQuery(SELECT_CHECOUT_SERIAL_NUMBERS_LIST, new String[]{"%"+serial_number+"%"});
            cursor.moveToFirst();
            checkout_date = cursor.getString(cursor.getColumnIndex(CHECKOUT_DATE));
            if (cursor != null) {
                cursor.close();
            }
            return checkout_date;
        }
    }

}
