package com.fengjie.model.dbhelper.PickTea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fengjie.model.dbhelper.Other.TeaDBHelper;
import com.fengjie.model.dbhelper.PickCheckout.CheckoutByDate_ListInfo;
import com.fengjie.model.dbhelper.PickCheckout.CheckoutByDate_ListInfo_Detail;
import com.fengjie.model.dbhelper.PickCheckout.CheckoutByStaff_ListInfo;
import com.fengjie.model.dbhelper.PickCheckout.CheckoutByStaff_ListInfo_Detail;
import com.fengjie.model.dbhelper.Staff.StaffDBUtil;
import com.fengjie.model.dbhelper.Tea.TeaDBUtil;
import com.fengjie.model.dbhelper.Tea.TeaInfo;
import com.fengjie.model.helper.Other.TimeHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiong on 2016/4/15.
 * 采茶信息数据库操作类
 */
public class PickTeaDBUtil
{
    /**
     * Table Name
     * 表名
     */
    public static final String TB_PICK_TEA_INFO = "tb_pick_tea_info";
    /**
     * pick_isCheckout 是否已结账
     */
    public static final String PICK_ISCHECKOUT = "pick_isCheckout";

    /**
     * checkout_date 结算日期
     */
    private static final String CHECKOUT_DATE = "checkout_date";


    /**
     * serial_number 当次记录编号
     */
    public static final String SERIAL_NUMBER = "serial_number";
    /**
     * uid 员工编号
     */
    private static final String UID = "uid";
    /**
     * tid 茶叶编号
     */
    private static final String TID = "tid";
    /**
     * pick_datetime 采茶日期
     */
    private static final String PICK_DATETIME = "pick_datetime";
    /**
     * pick_weight 重量
     */
    private static final String PICK_WEIGHT = "pick_weight";
    /**
     * pick_income 收入
     */
    private static final String PICK_INCOME = "pick_income";

    private static final String PICK_PRICE_REAL = "pick_price_real";
    /**
     * sql command
     * 数据库操作命令
     * 按日期查询(所有)每一天未结账的信息
     * 返回的数据集包括采茶日期、采茶总量、采茶总金额、当天未结账的采茶记录数
     */
    private static final String SELECT_NON_CHECKOUT_INFO_BY_DATE_ALL = "select strftime('%Y-%m-%d',pick_datetime) as m_datetime,count(*) as 'count' ,sum(pick_weight) as weight,sum(pick_income) as income from tb_pick_tea_info where pick_isCheckout=0 group by m_datetime";

    /**
     * sql command
     * 数据库操作命令
     * 按指定的日期区间查询每一天未结账的信息
     * 返回的数据集包括采茶日期、采茶总量、采茶总金额、当天未结账的采茶记录数
     * 使用时需要传入两个参数：起始时间和截至时间，格式为 yyyy-MM-dd
     */
    private static final String SELECT_NON_CHECKOUT_INFO_BY_DATE_CUSTOM_BETWEEN = "select strftime('%Y-%m-%d',pick_datetime) as m_datetime,count(*) as 'count' ,sum(pick_weight) as weight,sum(pick_income) as income from tb_pick_tea_info where pick_isCheckout=0 and m_datetime between ? and ? group by m_datetime";

    /**
     * sql command
     * 数据库操作命令
     * 按指定的日期区间查询起始时间之后的每一天未结账的信息
     * 返回的数据集包括采茶日期、采茶总量、采茶总金额、当天未结账的采茶记录数
     * 使用时需要传入两个参数：起始时间，格式为 yyyy-MM-dd
     */
    private static final String SELECT_NON_CHECKOUT_INFO_BY_DATE_CUSTOM_AFTER = "select strftime('%Y-%m-%d',pick_datetime) as m_datetime,count(*) as 'count' ,sum(pick_weight) as weight,sum(pick_income) as income from tb_pick_tea_info where pick_isCheckout=0 and m_datetime >=? group by m_datetime";

    /**
     * sql command
     * 数据库操作命令
     * 按指定的日期区间查询截至时间之前的每一天未结账的信息
     * 返回的数据集包括采茶日期、采茶总量、采茶总金额、当天未结账的采茶记录数
     * 使用时需要传入两个参数：截至时间，格式为 yyyy-MM-dd
     */
    private static final String SELECT_NON_CHECKOUT_INFO_BY_DATE_CUSTOM_BEFORE = "select strftime('%Y-%m-%d',pick_datetime) as m_datetime,count(*) as 'count' ,sum(pick_weight) as weight,sum(pick_income) as income from tb_pick_tea_info where pick_isCheckout=0 and m_datetime <=? group by m_datetime";

    /**
     * sql command
     * 数据库操作命令
     * 获取所有的未结账的详细信息
     */
    private static final String SELECT_NON_CHECKOUT_INFO_BY_DATE_ALL_DETAILS = "select p.pick_isCheckout,p.uid,a.staff_name,t.tea_category,p.pick_price_real,p.pick_weight,p.pick_income,p.serial_number from  tb_tea_info as t left join tb_pick_tea_info as p on t.tid=p.tid inner join tb_staff_info as a  on a.uid=p.uid where p.pick_isCheckout=0";

    /**
     * sql command
     * 数据库操作命令
     * 获取指定日期当天的所有未结账的详细信息
     * 使用时需要传入一个参数：指定的日期，格式为 yyyy-MM-dd
     */
    private static final String SELECT_NON_CHECKOUT_INFO_BY_DATE_CUSTOM_DETAILS =
            SELECT_NON_CHECKOUT_INFO_BY_DATE_ALL_DETAILS +
            " and strftime('%Y-%m-%d',p.pick_datetime)=?";

    /**
     * sql command
     * 数据库操作命令
     * 按员工查询(所有)所有员工未结账的信息
     */
    private static final String SELECT_NON_CHECK_INFO_BY_STAFF_ALL = "select a.uid,a.staff_name, count(*) as 'count' ,sum(b.pick_weight) as weight,sum(b.pick_income) as income from tb_staff_info as a left join tb_pick_tea_info as b on a.uid=b.uid where b.pick_isCheckout=0 group  by a.uid";

    /**
     * sql command
     * 按员工查询(所有)所有员工未结账的详细信息
     */
    private static final String SELECT_NON_CHECKOUT_INFO_BY_STAFF_ALL_DETAILS = "select p.pick_datetime,t.tea_category,p.pick_price_real,p.pick_weight,p.pick_income,p.serial_number,p.pick_isCheckout from  tb_tea_info as t left join tb_pick_tea_info as p on t.tid=p.tid inner join tb_staff_info as a  on a.uid=p.uid where p.pick_isCheckout=0";

    /**
     * sql command
     * 按员工查询(所有)指定员工未结账的详细信息
     */
    private static final String SELECT_NON_CHECKOUT_INFO_BY_STAFF_CUSTOM_STAFF_DETAILS =
            SELECT_NON_CHECKOUT_INFO_BY_STAFF_ALL_DETAILS + " and p.uid=?";

    /**
     * sql command
     * 按员工查询(所有)指定员工在指定时间段内的未结账的详细信息
     */
    private static final String SELECT_NON_CHECKOUT_INFO_BY_STAFF_CUSTOM_BETWEEN_DETAILS =
            SELECT_NON_CHECKOUT_INFO_BY_STAFF_CUSTOM_STAFF_DETAILS +
            " and p.pick_datetime between ? and  ?";

    /**
     * sql command
     * 按员工查询(所有)指定员工在指定时间之前的未结账的详细信息
     */
    private static final String SELECT_NON_CHECKOUT_INFO_BY_STAFF_CUSTOM_BEFORE_DETAILS =
            SELECT_NON_CHECKOUT_INFO_BY_STAFF_CUSTOM_STAFF_DETAILS + " and p.pick_datetime<=?";

    /**
     * sql command
     * 按员工查询(所有)指定员工在指定时间之后的未结账的详细信息
     */
    private static final String SELECT_NON_CHECKOUT_INFO_BY_STAFF_CUSTOM_AFTER_DETAILS =
            SELECT_NON_CHECKOUT_INFO_BY_STAFF_CUSTOM_STAFF_DETAILS + " and p.pick_datetime>=?";

    private static PickTeaDBUtil pickTeaDBUtil;
    private SQLiteDatabase database;
    private Context context;

    /**
     * 构造方法私有化
     *
     * @param context
     */
    private PickTeaDBUtil(Context context)
    {
        this.context = context;
        TeaDBHelper dbHelper = new TeaDBHelper(context, TeaDBHelper.DB_NAME, null, TeaDBHelper.DATABASE_VERSION);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * 获取PickTeaDBUtil实例
     *
     * @param context
     * @return
     */
    public synchronized static PickTeaDBUtil getInstance(Context context)
    {

        return pickTeaDBUtil == null ? new PickTeaDBUtil(context) : pickTeaDBUtil;
    }

    /**
     * 更新采茶信息：根据采茶信息编号来更新是否已结账并插入结账时间******************************************************************************************************************************
     *
     * @param serial_number 采茶信息编号
     * @param isCheckOut    1 已结账 0 未结账
     * @param Checkout_date  结账时间
     * @return -1 更新失败，不存在该记录编号的采茶信息<br></> -2 更新失败,该记录已结帐<br></> >0 结账的的采茶记录数
     */
    public static int updatePickTeaInfo( SQLiteDatabase database, String serial_number, int isCheckOut, String Checkout_date)
    {
        if (!(database.query(TB_PICK_TEA_INFO, null, SERIAL_NUMBER + "==?", new String[]{
                serial_number}, null, null, null)).moveToFirst())
        {
            return -1;
        }
        Cursor cursor = database.query(TB_PICK_TEA_INFO, new String[]{PICK_ISCHECKOUT},
                SERIAL_NUMBER + "==?", new String[]{serial_number}, null, null, null);
        if (cursor.moveToFirst() && cursor.getInt(cursor.getColumnIndex(PICK_ISCHECKOUT)) == 1)
        {
            cursor.close();
            return -2;
        }
        ContentValues values = new ContentValues();
        values.put(PICK_ISCHECKOUT, isCheckOut);
        values.put(CHECKOUT_DATE, Checkout_date);
        return database.update(TB_PICK_TEA_INFO, values,
                SERIAL_NUMBER + "==?", new String[]{serial_number});
    }

    /**
     * 更新采茶信息：根据采茶信息编号来更新是否已结账
     *
     * @param serial_number_list 采茶信息编号集合
//     * @param isCheckOut         1 已结账 0 未结账
     * @return -1 更新失败，不存在该记录编号的采茶信息<br></> -2 更新失败,该记录已结帐<br></> >0 结账的的采茶记录数
     */
    public static int updatePickTeaInfo( SQLiteDatabase database, List<String> serial_number_list)
    {
        int x = 0;
        for (String serial_number : serial_number_list)
        {
            if (!(database.query(TB_PICK_TEA_INFO, null, SERIAL_NUMBER + "==?", new String[]{
                    serial_number}, null, null, null)).moveToFirst())
            {
                return -1;
            }
            Cursor cursor = database.query(TB_PICK_TEA_INFO, new String[]{PICK_ISCHECKOUT},
                    SERIAL_NUMBER + "==?", new String[]{serial_number}, null, null, null);
            if (cursor.moveToFirst() && cursor.getInt(cursor.getColumnIndex(PICK_ISCHECKOUT)) == 1)
            {
                cursor.close();
                return -2;
            }
            ContentValues values = new ContentValues();
            values.put(PICK_ISCHECKOUT, 1);
            x += database.update(TB_PICK_TEA_INFO, values,
                    SERIAL_NUMBER + "==?", new String[]{serial_number});
        }
        return x;
    }

    /**
     * 录入采茶信息
     *
     * @param pickTeaInfo 采茶信息对象
     *                    pickTeaInfo.getUid() 获取pickTeaInfo中的员工编号，该编号从员工信息表获得
     *                    pickTeaInfo.getTid() 获取pickTeaInfo中的茶叶编号,该编号从茶叶信息表获得
     * @return >0 录入的行数<br></>  0 录入失败，传入的参数有误<br></> -1 录入失败，数据库错误<br></> -2 录入失败，编号为UID的员工不存在<br></> -3 录入失败，编号为TID的茶叶不存在<br></> -4录入成功，但更新价格失败
     */
    public int insertPickTeaInfo(PickTeaInfo pickTeaInfo)
    {


        if (!(database.query(StaffDBUtil.TB_STAFF_INFO, new String[]{UID},
                UID + "==?", new String[]{
                        pickTeaInfo.getUid() + ""}, null, null, null)).moveToFirst())
        {
            return -2;
        }
        if (!(database.query(TeaDBUtil.TB_TEA_INFO, new String[]{TID}, TID + "==?", new String[]{
                pickTeaInfo.getTid() + ""}, null, null, null)).moveToFirst())
        {
            return -3;
        }
        if (pickTeaInfo != null)
        {
            ContentValues values = new ContentValues();
            values.put(UID, pickTeaInfo.getUid());
            values.put(TID, pickTeaInfo.getTid());
            values.put(PICK_DATETIME,
                    pickTeaInfo.getPick_datetime() != null ? pickTeaInfo.getPick_datetime()
                                                           : TimeHelper.getCurrentDateTime());
            values.put(PICK_WEIGHT, pickTeaInfo.getPick_weight());
            values.put(PICK_INCOME, pickTeaInfo.getPick_income());
            values.put(PICK_ISCHECKOUT, pickTeaInfo.getPick_isCheckout());
            values.put(SERIAL_NUMBER, pickTeaInfo.getSerial_number());
            values.put(PICK_PRICE_REAL, pickTeaInfo.getPick_price());
            int result = (int) database.insert(TB_PICK_TEA_INFO, null, values);
            if (result > 0)
            {
                TeaDBUtil teaDBUtil = TeaDBUtil.getInstance(context);
                TeaInfo info = new TeaInfo();
                info.setTea_Id(pickTeaInfo.getTid());
                info.setTea_Price(pickTeaInfo.getPick_price());
                if (teaDBUtil.updateTea(info) > 0)
                {
                    return result;
                } else
                {
                    return -4;
                }
            }
        }
        return 0;
    }

    /**
     * 删除采茶信息
     *
     * @param uid 员工编号,uid==0时删除编号为tid的茶叶的所有采茶信息
     * @param tid 茶叶编号,tid==0时删除编号为uid的员工的所有采茶信息
     *            当uid、tid都为0时删除数据库中所有采茶信息
     * @return <0 删除失败，数据库出错
     */
    public int deletePickTeaInfo(int uid, int tid)
    {
        if (uid == 0 && tid != 0)
        {
            return database.delete(TB_PICK_TEA_INFO, TID + "==?", new String[]{tid + ""});
        } else if (uid != 0 && tid == 0)
        {
            return database.delete(TB_PICK_TEA_INFO, UID + "==?", new String[]{uid + ""});
        } else if (uid == 0 && tid == 0)
        {
            return database.delete(TB_PICK_TEA_INFO, null, null);
        } else
        {
            return database.delete(TB_PICK_TEA_INFO,
                    UID + "==? and " + TID + "==?", new String[]{uid + "", tid + ""});
        }
    }

    /**
     * 查询编号为uid的员工的所采茶叶编号为tid的采茶信息
     *
     * @param uid 员工编号
     * @param tid 茶叶编号
     * @return 查询结果集
     */
    public List<PickTeaInfo> selectPickTeaInfo( int uid, int tid)
    {
        List<PickTeaInfo> list = new ArrayList<>();
        String selection = "";
        String args[] = null;
        if (uid != 0 && tid != 0)
        {
            selection = UID + "==? and " + TID + "==?";
            args = new String[]{uid + "", tid + ""};
        }
        if (uid == 0 && tid != 0)
        {
            selection = TID + "==?";
            args = new String[]{tid + ""};
        }
        if (tid == 0 && uid != 0)
        {
            selection = UID + "==?";
            args = new String[]{uid + ""};
        }
        if (uid == 0 & tid == 0)
        {
            selection = null;
            args = null;
        }
        Cursor cursor = database.query(TB_PICK_TEA_INFO, null, selection, args, null, null, null);
        if (cursor.moveToFirst())
        {
            do
            {
                PickTeaInfo info = new PickTeaInfo();
                info.setUid(cursor.getInt(cursor.getColumnIndex(UID)));
                info.setTid(cursor.getInt(cursor.getColumnIndex(TID)));
                info.setPick_datetime(cursor.getString(cursor.getColumnIndex(PICK_DATETIME)));
                info.setPick_income(cursor.getFloat(cursor.getColumnIndex(PICK_INCOME)));
                info.setPick_isCheckout(cursor.getInt(cursor.getColumnIndex(PICK_ISCHECKOUT)));
                info.setPick_weight(cursor.getFloat(cursor.getColumnIndex(PICK_WEIGHT)));
                info.setSerial_number(cursor.getString(cursor.getColumnIndex(SERIAL_NUMBER)));
                info.setPick_price(cursor.getFloat(cursor.getColumnIndex(PICK_PRICE_REAL)));
                info.setCheckout_date(cursor.getString(cursor.getColumnIndex(CHECKOUT_DATE)));
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
     * 查询编号为uid的员工的所有采茶信息
     *
     * @param uid 员工编号
     * @return 查询结果集
     */
    public List<PickTeaInfo> selectPickTeaInfoByUid( int uid)
    {
        return selectPickTeaInfo(uid, 0);
    }

    /**
     * 查询茶叶编号为tid的所有采茶信息
     *
     * @param tid 茶叶编号
     * @return 查询结果集
     */
    public List<PickTeaInfo> selectPickTeaInfoByTid( int tid)
    {
        return selectPickTeaInfo(0, tid);
    }

    /**
     * 查询数据库中所有的采茶信息
     *
     * @return 查询结果集
     */
    public List<PickTeaInfo> selectAllPickTeaInfo()
    {
        return selectPickTeaInfo(0, 0);
    }

    //    /**
    //     * 查询已结账或者未结账的采茶信息
    //     *
    //     * @param isCheckout 1未结账 0已结账
    //     * @return 查询结果集
    //     */
    //    public List<PickTeaInfo> getCheckOutPickTeaInfo(int isCheckout)
    //    {
    //        List<PickTeaInfo> list = new ArrayList<>();
    //
    //        return list;
    //    }

    /**
     * 获取按时间排序结账时的列表信息
     *
     * @param startDatetime 指定起始时间，如果不指定请传入null
     * @param endDatetime   指定结束时间，如果不指定请传入null
     *                      如果startDatetime为null而endDatetime不为null，则查询endDatetime之前的所有记录，反之则查询startDatetime之后的所有记录
     *                      如果两者都为null则查询所有记录，如果都不为null则查询两者之间的所有信息
     * @return
     */
    public List<CheckoutByDate_ListInfo > getCheckoutByDate_ListInfo( String startDatetime, String endDatetime)
    {
        List<CheckoutByDate_ListInfo> listInfos = new ArrayList<>();
        Cursor cursor;
        String M_DATETIME = "m_datetime";
        String COUNT = "count";
        String WEIGHT = "weight";
        String INCOME = "income";
        if (isNULLorEmpty(startDatetime) && !isNULLorEmpty(endDatetime))//查询endDatetime之前的所有记录
        {
            cursor = database.rawQuery(SELECT_NON_CHECKOUT_INFO_BY_DATE_CUSTOM_BEFORE, new String[]{
                    endDatetime});
        } else if (!isNULLorEmpty(startDatetime) &&
                   isNULLorEmpty(endDatetime))//查询startDatetime之后的所有记录
        {
            cursor = database.rawQuery(SELECT_NON_CHECKOUT_INFO_BY_DATE_CUSTOM_AFTER, new String[]{
                    startDatetime});
        } else if (isNULLorEmpty(startDatetime) && isNULLorEmpty(endDatetime))//查询所有记录
        {
            cursor = database.rawQuery(SELECT_NON_CHECKOUT_INFO_BY_DATE_ALL, null);
        } else//查询startDatetime和endDatetime之间的所有信息
        {
            cursor = database.rawQuery(SELECT_NON_CHECKOUT_INFO_BY_DATE_CUSTOM_BETWEEN, new String[]{
                    startDatetime, endDatetime});
        }
        if (cursor.moveToFirst())
        {
            do
            {
                CheckoutByDate_ListInfo checkoutByDate_listInfo = new CheckoutByDate_ListInfo();
                checkoutByDate_listInfo.setM_datetime(cursor.getString(cursor.getColumnIndex(M_DATETIME)));
                checkoutByDate_listInfo.setCount(cursor.getInt(cursor.getColumnIndex(COUNT)));
                checkoutByDate_listInfo.setWeight(cursor.getFloat(cursor.getColumnIndex(WEIGHT)));
                checkoutByDate_listInfo.setIncome(cursor.getFloat(cursor.getColumnIndex(INCOME)));
                listInfos.add(checkoutByDate_listInfo);
            } while (cursor.moveToNext());
        }
        if (cursor != null)
        {
            cursor.close();
        }
        return listInfos;
    }

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
     * 获取日期为date的未结账的详细信息
     *
     * @param date 指定日期，格式为 yyyy-MM-dd
     * @return date为null时返回所有未结账的详细信息<br></>否则返回date当天的未结账的详细信息
     */
    public List<CheckoutByDate_ListInfo_Detail > getCheckoutByDate_ListInfo_Detail( String date)
    {
        List<CheckoutByDate_ListInfo_Detail> listInfo_details = new ArrayList<>();
        Cursor cursor;
        String UID = "uid";
        String STAFF_NAME = "staff_name";
        String TEA_CATEGORY = "tea_category";
        String PICK_PRICE_REAL = "pick_price_real";
        String PICK_WEIGHT = "pick_weight";
        String PICK_INCOME = "pick_income";
        String SERIAL_NUMBER = "serial_number";
        if (date == null || date.isEmpty())
        {
            cursor = database.rawQuery(SELECT_NON_CHECKOUT_INFO_BY_DATE_ALL_DETAILS, null);
        } else
        {
            cursor = database.rawQuery(SELECT_NON_CHECKOUT_INFO_BY_DATE_CUSTOM_DETAILS, new String[]{
                    date});
        }
        if (cursor.moveToFirst())
        {
            do
            {
                CheckoutByDate_ListInfo_Detail info_detail = new CheckoutByDate_ListInfo_Detail();
                info_detail.setUid(cursor.getInt(cursor.getColumnIndex(UID)));
                info_detail.setStaff_name(cursor.getString(cursor.getColumnIndex(STAFF_NAME)));
                info_detail.setTea_category(cursor.getString(cursor.getColumnIndex(TEA_CATEGORY)));
                info_detail.setTea_price(cursor.getFloat(cursor.getColumnIndex(PICK_PRICE_REAL)));
                info_detail.setPick_weight(cursor.getFloat(cursor.getColumnIndex(PICK_WEIGHT)));
                info_detail.setPick_income(cursor.getFloat(cursor.getColumnIndex(PICK_INCOME)));
                info_detail.setSerial_number(cursor.getString(cursor.getColumnIndex(SERIAL_NUMBER)));
                listInfo_details.add(info_detail);
            } while (cursor.moveToNext());
        }
        if (cursor != null)
        {
            cursor.close();
        }
        return listInfo_details;
    }

    /**
     * 获取按员工排序查询的未结账的列表信息
     *
     * @return 返回所有未结账的详细信息
     */
    public List<CheckoutByStaff_ListInfo > getCheckoutByStaff_ListInfo()
    {
        List<CheckoutByStaff_ListInfo> listInfos = new ArrayList<>();
        Cursor cursor;
        String UID = "uid";
        String STAFF_NAME = "staff_name";
        String COUNT = "count";
        String WEIGHT = "weight";
        String INCOME = "income";
        cursor = database.rawQuery(SELECT_NON_CHECK_INFO_BY_STAFF_ALL, null);
        if (cursor.moveToFirst())
        {
            do
            {
                CheckoutByStaff_ListInfo info = new CheckoutByStaff_ListInfo();
                info.setUid(cursor.getInt(cursor.getColumnIndex(UID)));
                info.setStaff_name(cursor.getString(cursor.getColumnIndex(STAFF_NAME)));
                info.setCount(cursor.getInt(cursor.getColumnIndex(COUNT)));
                info.setWeight(cursor.getFloat(cursor.getColumnIndex(WEIGHT)));
                info.setIncome(cursor.getFloat(cursor.getColumnIndex(INCOME)));
                listInfos.add(info);

            } while (cursor.moveToNext());
        }
        if (cursor != null)
        {
            cursor.close();
        }
        return listInfos;

    }

    /**
     * 获取按员工排序结账时的详细信息
     *
     * @param uid           员工编号，不能为0
     * @param startDatetime 指定起始时间，如果不指定请传入null
     * @param endDatetime   指定结束时间，如果不指定请传入null
     *                      如果startDatetime为null而endDatetime不为null，则查询endDatetime之前的所有记录，反之则查询startDatetime之后的所有记录
     *                      如果两者都为null则查询所有记录，如果都不为null则查询两者之间的所有信息
     * @return List<CheckoutByStaff_ListInfo_Detail> 按员工排序结账时的详细信息集合
     */
    public List<CheckoutByStaff_ListInfo_Detail > getCheckoutByStaff_ListInfo_Detail( int uid, String startDatetime, String endDatetime)
    {
        List<CheckoutByStaff_ListInfo_Detail> listInfos = new ArrayList<>();
        Cursor cursor;
        String PICK_DATETIME = "pick_datetime";
        String TEA_CATEGORY = "tea_category";
        String PICK_PRICE_REAL = "pick_price_real";
        String PICK_WEIGHT = "pick_weight";
        String PICK_INCOME = "pick_income";
        String SERIAL_NUMBER = "serial_number";
        String PICK_ISCHECKOUT = "pick_isCheckout";
        if (uid == 0)
        {
            return null;
        } else
        {
            if (isNULLorEmpty(startDatetime) && !isNULLorEmpty(endDatetime))
            {
                cursor = database.rawQuery(SELECT_NON_CHECKOUT_INFO_BY_STAFF_CUSTOM_BEFORE_DETAILS, new String[]{
                        uid + "", endDatetime});
            } else if (!isNULLorEmpty(startDatetime) && isNULLorEmpty(endDatetime))
            {
                cursor = database.rawQuery(SELECT_NON_CHECKOUT_INFO_BY_STAFF_CUSTOM_AFTER_DETAILS, new String[]{
                        uid + "", startDatetime});
            } else if (isNULLorEmpty(startDatetime) && isNULLorEmpty(endDatetime))
            {
                cursor = database.rawQuery(SELECT_NON_CHECKOUT_INFO_BY_STAFF_CUSTOM_STAFF_DETAILS, new String[]{
                        uid + ""});
            } else
            {
                cursor = database.rawQuery(SELECT_NON_CHECKOUT_INFO_BY_STAFF_CUSTOM_BETWEEN_DETAILS, new String[]{
                        uid + "", startDatetime, endDatetime});
            }
        }
        if (cursor.moveToFirst())
        {
            do
            {
                CheckoutByStaff_ListInfo_Detail info_detail = new CheckoutByStaff_ListInfo_Detail();
                info_detail.setPick_datetime(cursor.getString(cursor.getColumnIndex(PICK_DATETIME)));
                info_detail.setTea_category(cursor.getString(cursor.getColumnIndex(TEA_CATEGORY)));
                info_detail.setTea_price(cursor.getFloat(cursor.getColumnIndex(PICK_PRICE_REAL)));
                info_detail.setPick_weight(cursor.getFloat(cursor.getColumnIndex(PICK_WEIGHT)));
                info_detail.setPick_income(cursor.getFloat(cursor.getColumnIndex(PICK_INCOME)));
                info_detail.setSerial_number(cursor.getString(cursor.getColumnIndex(SERIAL_NUMBER)));
                info_detail.setPick_isCheckout(cursor.getInt(cursor.getColumnIndex(PICK_ISCHECKOUT)));
                listInfos.add(info_detail);

            } while (cursor.moveToNext());
        }
        if (cursor != null)
        {
            cursor.close();
        }
        return listInfos;
    }

}
