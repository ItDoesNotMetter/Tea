package com.fengjie.model.dbhelper.Statistic;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fengjie.model.dbhelper.Other.TeaDBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xiongda on 2016/5/15.
 */
public class StatisticDBUtil
{
    /**
     * Table Name
     * 表名
     */
    public static final String TB_STAFF_INFO = "tb_staff_info";

    /**
     * Column Name
     * 字段名
     */

    /**
     * uid 员工编号
     */
    private static final String STAFF_ID = "uid";
    /**
     * staff_name 员工名
     */
    private static final String STAFF_NAME = "staff_name";

    /**
     * Sql Command
     * 按人员排序获取列表数据
     */
    private static final String GET_LIST_INFO_BY_NAME = "select s.uid as 'uid',s.staff_name as 'staff_name',sum(p.pick_income) as 'total_money',count(*) as 'count' from tb_staff_info as s left join tb_pick_tea_info as p on s.uid=p.uid where p.pick_ischeckout=1 group by p.uid";
    /**
     * Sql Command
     * 按时间排序获取列表数据
     */
    private static final String GET_LIST_INFO_BY_TIME = "select strftime('%Y-%m-%d',pick_datetime) as m_datetime  ,sum(pick_income) as 'total_money',count(*) as 'count' from  tb_pick_tea_info where pick_ischeckout=1 group by m_datetime order by m_datetime desc";

    private static final String GET_LIST_INFO_BY_TIME_AFTER = "select strftime('%Y-%m-%d',pick_datetime) as m_datetime  ,sum(pick_income) as 'total_money',count(*) as 'count' from  tb_pick_tea_info where pick_ischeckout=1 and checkout_date >=? group by m_datetime order by m_datetime desc";

    private static final String GET_LIST_INFO_BY_TIME_BEFORE = "select strftime('%Y-%m-%d',pick_datetime) as m_datetime  ,sum(pick_income) as 'total_money',count(*) as 'count' from  tb_pick_tea_info where pick_ischeckout=1 and checkout_date <=? group by m_datetime order by m_datetime desc";

    private static final String GET_LIST_INFO_BY_TIME_BETWEEN = "select strftime('%Y-%m-%d',pick_datetime) as m_datetime  ,sum(pick_income) as 'total_money',count(*) as 'count' from  tb_pick_tea_info where pick_ischeckout=1 and checkout_date between ? and ? group by m_datetime order by m_datetime desc";

    /**
     * Sql Command
     * 按结账记录获取列表数据
     */
    private static final String GET_LIST_INFO_BY_RECORD = "select checkout_record_serial,checkout_money,checkout_serial_numbers from tb_pick_checkout order by checkout_date desc";
    /**
     * 按结账日期查询已结账的详细信息
     */
    private static final String GET_LIST_INFO_DETAIL_BY_TIME_ALL ="select s.uid as 'uid',s.staff_name as 'staff_name',tea_category,p_datetime,checkout_date,serial_number,pick_weight,pick_income,pick_price_real from (select t.tea_category as 'tea_category',puid,ptid,p_datetime,checkout_date,serial_number,pick_weight,pick_income,pick_price_real from (select strftime('%Y-%m-%d',p.pick_datetime) as p_datetime,strftime('%Y-%m-%d',p.checkout_date) as 'checkout_date',p.serial_number as serial_number ,p.pick_weight,p.pick_income,p.pick_price_real,p.uid as 'puid',p.tid as 'ptid' from tb_pick_tea_info as p where p.pick_isCheckout=1) left join tb_tea_info as t on ptid=t.tid) left join tb_staff_info as s on puid=uid order by checkout_date desc";
    private static final String GET_LIST_INFO_DETAIL_BY_TIME_Onedate ="select s.uid as 'uid',s.staff_name as 'staff_name',tea_category,p_datetime,checkout_date,serial_number,pick_weight,pick_income,pick_price_real from (select t.tea_category as 'tea_category',puid,ptid,p_datetime,checkout_date,serial_number,pick_weight,pick_income,pick_price_real from (select strftime('%Y-%m-%d',p.pick_datetime) as p_datetime,strftime('%Y-%m-%d',p.checkout_date) as 'checkout_date',p.serial_number as serial_number ,p.pick_weight,p.pick_income,p.pick_price_real,p.uid as 'puid',p.tid as 'ptid' from tb_pick_tea_info as p where p.pick_isCheckout=1 and checkout_date like ?) left join tb_tea_info as t on ptid=t.tid) left join tb_staff_info as s on puid=uid order by serial_number desc";
    private static final String GET_LIST_INFO_DETAIL_BY_TIME_BETWEEN ="select s.uid as 'uid',s.staff_name as 'staff_name',tea_category,p_datetime,checkout_date,serial_number,pick_weight,pick_income,pick_price_real from (select t.tea_category as 'tea_category',puid,ptid,p_datetime,checkout_date,serial_number,pick_weight,pick_income,pick_price_real from (select strftime('%Y-%m-%d',p.pick_datetime) as p_datetime,strftime('%Y-%m-%d',p.checkout_date) as 'checkout_date',p.serial_number as serial_number ,p.pick_weight,p.pick_income,p.pick_price_real,p.uid as 'puid',p.tid as 'ptid' from tb_pick_tea_info as p where p.pick_isCheckout=1 and checkout_date between ? and ?) left join tb_tea_info as t on ptid=t.tid) left join tb_staff_info as s on puid=uid order by checkout_date desc";
    private static final String GET_LIST_INFO_DETAIL_BY_TIME_AFTER ="select s.uid as 'uid',s.staff_name as 'staff_name',tea_category,p_datetime,checkout_date,serial_number,pick_weight,pick_income,pick_price_real from (select t.tea_category as 'tea_category',puid,ptid,p_datetime,checkout_date,serial_number,pick_weight,pick_income,pick_price_real from (select strftime('%Y-%m-%d',p.pick_datetime) as p_datetime,strftime('%Y-%m-%d',p.checkout_date) as 'checkout_date',p.serial_number as serial_number ,p.pick_weight,p.pick_income,p.pick_price_real,p.uid as 'puid',p.tid as 'ptid' from tb_pick_tea_info as p where p.pick_isCheckout=1 and checkout_date >=?) left join tb_tea_info as t on ptid=t.tid) left join tb_staff_info as s on puid=uid order by checkout_date desc";
    private static final String GET_LIST_INFO_DETAIL_BY_TIME_BEFORE ="select s.uid as 'uid',s.staff_name as 'staff_name',tea_category,p_datetime,checkout_date,serial_number,pick_weight,pick_income,pick_price_real from (select t.tea_category as 'tea_category',puid,ptid,p_datetime,checkout_date,serial_number,pick_weight,pick_income,pick_price_real from (select strftime('%Y-%m-%d',p.pick_datetime) as p_datetime,strftime('%Y-%m-%d',p.checkout_date) as 'checkout_date',p.serial_number as serial_number ,p.pick_weight,p.pick_income,p.pick_price_real,p.uid as 'puid',p.tid as 'ptid' from tb_pick_tea_info as p where p.pick_isCheckout=1 and checkout_date <=?) left join tb_tea_info as t on ptid=t.tid) left join tb_staff_info as s on puid=uid order by checkout_date desc";

    /**
    * 按serial_number编号排序的已结账的详细信息集合
    */
    private static final String GET_LIST_INFO_DETAIL_order_BY_serial_number="select s.uid as 'uid',s.staff_name as 'staff_name',tea_category,p_datetime,checkout_date,serial_number,pick_weight,pick_income,pick_price_real from (select t.tea_category as 'tea_category',puid,ptid,p_datetime,checkout_date,serial_number,pick_weight,pick_income,pick_price_real from (select strftime('%Y-%m-%d',p.pick_datetime) as p_datetime,strftime('%Y-%m-%d',p.checkout_date) as 'checkout_date',p.serial_number as serial_number ,p.pick_weight,p.pick_income,p.pick_price_real,p.uid as 'puid',p.tid as 'ptid' from tb_pick_tea_info as p where p.pick_isCheckout=1) left join tb_tea_info as t on ptid=t.tid) left join tb_staff_info as s on puid=uid order by serial_number desc";

    /**
     * 按指定员工uid查询已结账的详细信息集合
     */
    private static final String GET_LIST_INFO_DETAIL__BY_UID="select s.uid as 'uid',s.staff_name as 'staff_name',tea_category,p_datetime,checkout_date,serial_number,pick_weight,pick_income,pick_price_real from (select t.tea_category as 'tea_category',puid,ptid,p_datetime,checkout_date,serial_number,pick_weight,pick_income,pick_price_real from (select strftime('%Y-%m-%d',p.pick_datetime) as p_datetime,strftime('%Y-%m-%d',p.checkout_date) as 'checkout_date',p.serial_number as serial_number ,p.pick_weight,p.pick_income,p.pick_price_real,p.uid as 'puid',p.tid as 'ptid' from tb_pick_tea_info as p where p.pick_isCheckout=1 and uid=?) left join tb_tea_info as t on ptid=t.tid) left join tb_staff_info as s on puid=uid order by checkout_date desc";
    /**
     * 获得按指定的 结账记录号 checkout_record_serial 来查询结账表里的 单号集合 checkout_serial_numbers
     */
    private static final String GET_checkout_serial_numbers_BY_checkout_record_serial = "select c.checkout_serial_numbers as csns from tb_pick_checkout as c where checkout_record_serial =?";
    /**
     * Test    checkout_record_serial 查询所有的结账记录号 checkout_record_serial
     */
    private static final String GET_checkout_record_serial ="select checkout_record_serial from tb_pick_checkout";

    private static final String GET_salaryHadDone="select sum(p.pick_income) as 'total_money' from tb_pick_tea_info as p where p.pick_ischeckout=1";
    private static final String GET_salaryNotDone="select sum(p.pick_income) as 'total_money' from tb_pick_tea_info as p where p.pick_ischeckout=0";

    private static StatisticDBUtil statisticDBUtil;
    private Context context;
    private SQLiteDatabase database;

    /**
     * 私有化构造方法,用于实现单例模式访问
     *
     * @param context
     */
    private StatisticDBUtil(Context context)
    {
        this.context = context;
        TeaDBHelper dbHelper = new TeaDBHelper(context, TeaDBHelper.DB_NAME, null, TeaDBHelper.DATABASE_VERSION);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * 获取StatisticDBUtil实例
     *
     * @param context 上下文
     * @return StatisticDBUtil实例
     */
    public synchronized static StatisticDBUtil getInstance(Context context)
    {
        return statisticDBUtil == null ? new StatisticDBUtil(context) : statisticDBUtil;
    }

    /**
     * 获取按人员排序的列表信息
     *
     * @param sortType 排序方式<br></br> 1 按人员排序<br></br> 2 按时间排序<br></> 3 按结账单编号排序
     * @return Statistic_ListInfo对象集合
     */
    public List<Statistic_ListInfo> getListInfo( int sortType)
    {
        switch (sortType)
        {
            case 1:
                return getListInfoByName();
            case 2:
                return getListInfoByTime();
            case 3:
                return getListInfoByRecord();
            default:
                return getListInfoByName();
        }
    }

    private List<Statistic_ListInfo> getListInfoByName()
    {
        List<Statistic_ListInfo> infoList = new ArrayList<>();
        Cursor cursor;
        String UID = "uid";
        String STAFF_NAME = "staff_name";
        String TOTAL_MONEY = "total_money";
        String COUNT = "count";
        cursor = database.rawQuery(GET_LIST_INFO_BY_NAME, null);
        if (cursor.moveToFirst())
        {
            do
            {
                Statistic_ListInfo info = new Statistic_ListInfo();
                info.setUid(cursor.getInt(cursor.getColumnIndex(UID)));
                info.setStaffName(cursor.getString(cursor.getColumnIndex(STAFF_NAME)));
                info.setTotalMoney(cursor.getFloat(cursor.getColumnIndex(TOTAL_MONEY)));
                info.setRecordNumbers(cursor.getInt(cursor.getColumnIndex(COUNT)));
                infoList.add(info);
            } while (cursor.moveToNext());
        }
        if (cursor != null)
        {
            cursor.close();
        }
        return infoList;
    }

    /**
     *
     * @return 已结账的按时间排序(全部) 集合infoList
     */
    private List<Statistic_ListInfo> getListInfoByTime()
    {
        List<Statistic_ListInfo> infoList = new ArrayList<>();
        Cursor cursor;
        String M_DATETIME = "m_datetime";
        String TOTAL_MONEY = "total_money";
        String COUNT = "count";
        cursor = database.rawQuery(GET_LIST_INFO_BY_TIME, null);
        if (cursor.moveToFirst())
        {
            do
            {
                Statistic_ListInfo info = new Statistic_ListInfo();
                info.setmTime(cursor.getString(cursor.getColumnIndex(M_DATETIME)));
                info.setTotalMoney(cursor.getFloat(cursor.getColumnIndex(TOTAL_MONEY)));
                info.setRecordNumbers(cursor.getInt(cursor.getColumnIndex(COUNT)));
                infoList.add(info);
            } while (cursor.moveToNext());
        }
        if (cursor != null)
        {
            cursor.close();
        }
        return infoList;
    }


    /**
     *        返回已结账的按时间信息集合
     * @param start_checkout_date 查询的起始结账时间 如果不指定请传入null
     * @param end_checkout_date  查询的截止结账时间 如果不指定请传入null
     * @return infoList返回已结账的按时间查询信息集合
     *         如果start_checkout_date为null而end_checkout_date不为null，则查询end_checkout_date之前的记录，按天显示。反之则查询start_checkout_date之后的
     *         如果两者都为null则也将返回所有按时间排序集合infoList，如果都不为null则查询两者之间
     */
    public List<Statistic_ListInfo> getListInfoByTime( String start_checkout_date, String end_checkout_date)
    {
        List<Statistic_ListInfo> infoList = new ArrayList<>();
        Cursor cursor;
        String M_DATETIME = "m_datetime";
        String TOTAL_MONEY = "total_money";
        String COUNT = "count";
        if (isNULLorEmpty(start_checkout_date) && !isNULLorEmpty(end_checkout_date))//查询end_checkout_date之前的
        {
            cursor = database.rawQuery(GET_LIST_INFO_BY_TIME_BEFORE, new String[]{
                    end_checkout_date+" 23:59:59"});
        } else if (!isNULLorEmpty(start_checkout_date) && isNULLorEmpty(end_checkout_date))//查询start_checkout_date之后的
        {
            cursor = database.rawQuery(GET_LIST_INFO_BY_TIME_AFTER, new String[]{
                    start_checkout_date});
        } else if (isNULLorEmpty(start_checkout_date) && isNULLorEmpty(end_checkout_date))//查询所有
        {
            cursor= database.rawQuery(GET_LIST_INFO_BY_TIME,null);
        } else//查询start_checkout_date和end_checkout_date之间
        {
            cursor = database.rawQuery(GET_LIST_INFO_BY_TIME_BETWEEN, new String[]{start_checkout_date, end_checkout_date+" 23:59:59"});
        }
        if (cursor.moveToFirst())
        {
            do
            {
                Statistic_ListInfo info = new Statistic_ListInfo();
                info.setmTime(cursor.getString(cursor.getColumnIndex(M_DATETIME)));
                info.setTotalMoney(cursor.getFloat(cursor.getColumnIndex(TOTAL_MONEY)));
                info.setRecordNumbers(cursor.getInt(cursor.getColumnIndex(COUNT)));
                infoList.add(info);
            } while (cursor.moveToNext());
        }
        if (cursor != null)
        {
            cursor.close();
        }
        return infoList;
    }


    private List<Statistic_ListInfo> getListInfoByRecord()
    {
        List<Statistic_ListInfo> infoList = new ArrayList<>();
        Cursor cursor;
        String CHECKOUT_RECORD_SERIAL = "checkout_record_serial";
        String TOTAL_MONEY = "checkout_money";
        String CHECKOUT_SERIAL_NUMBERS = "checkout_serial_numbers";
        String COUNT = "count";
        cursor = database.rawQuery(GET_LIST_INFO_BY_RECORD, null);
        if (cursor.moveToFirst())
        {
            do
            {
                Statistic_ListInfo info = new Statistic_ListInfo();
                info.setCheckoutRecordSerial(cursor.getString(cursor.getColumnIndex(CHECKOUT_RECORD_SERIAL)));
                info.setTotalMoney(cursor.getFloat(cursor.getColumnIndex(TOTAL_MONEY)));
                String str = cursor.getString(cursor.getColumnIndex(CHECKOUT_SERIAL_NUMBERS));
                info.setRecordNumbers(Arrays.asList(str.split("\\|")).size());
                infoList.add(info);
            } while (cursor.moveToNext());
        }
        if (cursor != null)
        {
            cursor.close();
        }
        return infoList;
    }


    /**
     *
     * @param start_checkout_date 查询的起始结账时间 如果不指定请传入null
     * @param end_checkout_date  查询的截止结账时间 如果不指定请传入null
     * @return List<Statistic_Detail_ListInfo> 返回所有的已结账的详细信息集合
     *         如果start_checkout_date为null而end_checkout_date不为null，则查询end_checkout_date之前的所有记录，反之则查询start_checkout_date之后的所有记录
     *         如果两者都为null则查询所有记录，如果都不为null则查询两者之间的所有信息
     */
    public List<Statistic_Detail_ListInfo> getListInfo_Detail_BY_TIME( String start_checkout_date, String end_checkout_date)
    {   Cursor cursor;
        List<Statistic_Detail_ListInfo> List =new ArrayList<>();

        if (isNULLorEmpty(start_checkout_date) && !isNULLorEmpty(end_checkout_date))//查询end_checkout_date之前的所有记录
        {
            cursor = database.rawQuery(GET_LIST_INFO_DETAIL_BY_TIME_BEFORE, new String[]{
                    end_checkout_date+" 23:59:59"});
        } else if (!isNULLorEmpty(start_checkout_date) && isNULLorEmpty(end_checkout_date))//查询start_checkout_date之后的所有记录
        {
            cursor = database.rawQuery(GET_LIST_INFO_DETAIL_BY_TIME_AFTER, new String[]{
                    start_checkout_date});
        } else if (isNULLorEmpty(start_checkout_date) && isNULLorEmpty(end_checkout_date))//查询所有记录
        {
            cursor= database.rawQuery(GET_LIST_INFO_DETAIL_BY_TIME_ALL,null);
        } else//查询start_checkout_date和end_checkout_date之间的所有信息
        {
            cursor = database.rawQuery(GET_LIST_INFO_DETAIL_BY_TIME_BETWEEN, new String[]{start_checkout_date, end_checkout_date+" 23:59:59"});
        }

        if (cursor.moveToFirst())
        {
            do
            {
                Statistic_Detail_ListInfo info = new Statistic_Detail_ListInfo();
                info.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
                info.setStaff_Name(cursor.getString(cursor.getColumnIndex("staff_name")));
                info.setTea_Category(cursor.getString(cursor.getColumnIndex("tea_category")));
                info.setPick_datetime(cursor.getString(cursor.getColumnIndex("p_datetime")));
                info.setSerial_number(cursor.getString(cursor.getColumnIndex("serial_number")));
                info.setPick_weight(cursor.getInt(cursor.getColumnIndex("pick_weight")));
                info.setPick_income(cursor.getFloat(cursor.getColumnIndex("pick_income")));
                info.setPick_price(cursor.getFloat(cursor.getColumnIndex("pick_price_real")));
                info.setCheckout_date(cursor.getString(cursor.getColumnIndex("checkout_date")));
                List.add(info);
            } while (cursor.moveToNext());
        }
        if (cursor != null)
        {
            cursor.close();
        }
        return List;

    }

    /**
     * 只查询某天的已结账的详细信息集合
     * @param checkout_date 要查询的指定日期如2016-05-16
     * @return  详细信息集合List
     */
    public List<Statistic_Detail_ListInfo> getListInfo_Detail_BY_TIME( String checkout_date)
    {   Cursor cursor;
        List<Statistic_Detail_ListInfo> List =new ArrayList<>();
        cursor = database.rawQuery(GET_LIST_INFO_DETAIL_BY_TIME_Onedate, new String[]{checkout_date+"%"});
        if (cursor.moveToFirst())
        {
            do
            {
                Statistic_Detail_ListInfo info = new Statistic_Detail_ListInfo();
                info.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
                info.setStaff_Name(cursor.getString(cursor.getColumnIndex("staff_name")));
                info.setTea_Category(cursor.getString(cursor.getColumnIndex("tea_category")));
                info.setPick_datetime(cursor.getString(cursor.getColumnIndex("p_datetime")));
                info.setSerial_number(cursor.getString(cursor.getColumnIndex("serial_number")));
                info.setPick_weight(cursor.getInt(cursor.getColumnIndex("pick_weight")));
                info.setPick_income(cursor.getFloat(cursor.getColumnIndex("pick_income")));
                info.setPick_price(cursor.getFloat(cursor.getColumnIndex("pick_price_real")));
                info.setCheckout_date(cursor.getString(cursor.getColumnIndex("checkout_date")));
                List.add(info);
            } while (cursor.moveToNext());
        }
        if (cursor != null)
        {
            cursor.close();
        }
        return List;

    }

    /**
     *
     * @param  checkout_record_serial 结算单编号
     *         通过结算单编号查询已结账的详细信息集合
     * @return ListByRecord  详细信息集合
     */
    public List<Statistic_Detail_ListInfo> getListInfo_Detail_ByRecord( String checkout_record_serial)
    {
        List<Statistic_Detail_ListInfo> ListByRecord =new ArrayList<>();

        List<Statistic_Detail_ListInfo> List_order_by_serial_number = get_Detail_ListInfo_order_by_serial_number();

        Cursor cursor=database.rawQuery(GET_checkout_serial_numbers_BY_checkout_record_serial,new String[]{checkout_record_serial});
        if(cursor.moveToFirst())
        {  do
             {
               for (Statistic_Detail_ListInfo info : List_order_by_serial_number)
               {
                  if (cursor.getString(cursor.getColumnIndex("csns")).contains(info.getSerial_number()))
                   {
                     ListByRecord.add(info);
                   }
               }
             }while (cursor.moveToNext());
        }
        if (cursor != null)
        {
            cursor.close();
        }
        return ListByRecord;
    }


    /**  按采茶单编号serial_number来排序的所有已结账的详细信息集合
     *
     * @return   ListInfo详细信息集合
     */
    public List<Statistic_Detail_ListInfo> get_Detail_ListInfo_order_by_serial_number()
    {
        List<Statistic_Detail_ListInfo> ListInfo =new ArrayList<>();
        Cursor cursor=database.rawQuery(GET_LIST_INFO_DETAIL_order_BY_serial_number,null);
        if(cursor.moveToFirst())
        {
            do
            {   Statistic_Detail_ListInfo info =new Statistic_Detail_ListInfo();
                info.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
                info.setStaff_Name(cursor.getString(cursor.getColumnIndex("staff_name")));
                info.setTea_Category(cursor.getString(cursor.getColumnIndex("tea_category")));
                info.setPick_datetime(cursor.getString(cursor.getColumnIndex("p_datetime")));
                info.setSerial_number(cursor.getString(cursor.getColumnIndex("serial_number")));
                info.setPick_weight(cursor.getInt(cursor.getColumnIndex("pick_weight")));
                info.setPick_income(cursor.getFloat(cursor.getColumnIndex("pick_income")));
                info.setPick_price(cursor.getFloat(cursor.getColumnIndex("pick_price_real")));
                info.setCheckout_date(cursor.getString(cursor.getColumnIndex("checkout_date")));
                ListInfo.add(info);
            }while (cursor.moveToNext());
        }
        if (cursor != null)
        {
            cursor.close();
        }
        return ListInfo;
    }

    /**        通过指定uid查询出其已结账详细信息集合
     * @param  uid 员工id
     * @return ListInfo 详细信息集合
     */
    public List<Statistic_Detail_ListInfo> getListInfo_Detail_BY_uid( int uid)
    {
        List<Statistic_Detail_ListInfo> ListInfo =new ArrayList<>();
        Cursor cursor=database.rawQuery(GET_LIST_INFO_DETAIL__BY_UID,new String[]{ String.valueOf(uid)});
        if(cursor.moveToFirst())
        {
            do
            {   Statistic_Detail_ListInfo info =new Statistic_Detail_ListInfo();
                info.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
                info.setStaff_Name(cursor.getString(cursor.getColumnIndex("staff_name")));
                info.setTea_Category(cursor.getString(cursor.getColumnIndex("tea_category")));
                info.setPick_datetime(cursor.getString(cursor.getColumnIndex("p_datetime")));
                info.setSerial_number(cursor.getString(cursor.getColumnIndex("serial_number")));
                info.setPick_weight(cursor.getInt(cursor.getColumnIndex("pick_weight")));
                info.setPick_income(cursor.getFloat(cursor.getColumnIndex("pick_income")));
                info.setPick_price(cursor.getFloat(cursor.getColumnIndex("pick_price_real")));
                info.setCheckout_date(cursor.getString(cursor.getColumnIndex("checkout_date")));
                ListInfo.add(info);
            }while (cursor.moveToNext());
        }
        if (cursor != null)
        {
            cursor.close();
        }
        return ListInfo;
    }

    public String GET_salaryHadDone() {
        String str = new String();

        Cursor cursor = database.rawQuery(GET_salaryHadDone, null);
        if (cursor.moveToFirst()) {
            do {
                str = cursor.getString(cursor.getColumnIndex("total_money"));

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        if (str == null) {
            return "无";
        } else return str+"元";

    }

    public String GET_salaryNotDone() {
        String str = new String();
        Cursor cursor = database.rawQuery(GET_salaryNotDone, null);
        if (cursor.moveToFirst()) {
            do {
                str = cursor.getString(cursor.getColumnIndex("total_money"));

            } while (cursor.moveToNext());

        }
        if (cursor != null) {
            cursor.close();
        }
        if (str == null) {
            return "无";
        } else return str+"元";

    }








    /**
     * 辅助方法，为按结账时间排序的起始时间参数和截止时间参数是否为null或empty做判断
     * @param str 传入的时间参数
     * @return true or false
     */
    private boolean isNULLorEmpty(String str)
    {
        if (str == null)
        {
            return true;
        } else if (str.isEmpty())
        {
            return true;
        } else
        {
            return false;
        }
    }




/**
     * Test
      * @return 获得所有结账记录号checkout_record_serial
     */

    public List<String> getcheckout_record_serial() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(GET_checkout_record_serial, null);
        if (cursor.moveToFirst()) {
            do
            {
                String string=new String();
                string= cursor.getString(cursor.getColumnIndex("checkout_record_serial"));
                list.add(string);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        return list;
    }
}
