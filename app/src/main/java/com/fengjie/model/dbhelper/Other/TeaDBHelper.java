package com.fengjie.model.dbhelper.Other;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xiong on 2016/4/13.
 * 数据库操作辅助类
 */
public class TeaDBHelper extends SQLiteOpenHelper
{

    /**
     * DataBase Name
     * 数据库名称
     */
    public static final String DB_NAME="TeaHelper.db";

    /**
     * Database Version
     * 数据库版本
     */
    public static final int DATABASE_VERSION=1;

    /**
     * Create Table
     * 采茶员工信息表
     */
    public static final String CREATE_TB_STAFF_INFO="create table tb_staff_info (uid integer primary key autoincrement  not null,staff_name text not null,staff_sex text not null,staff_phone text not null default '',staff_NRIC text not null default '',add_time datetime not null default (datetime('now','localtime')))";

    /**
     * Create Table
     * 茶叶信息表
     */
    public static final String CREATE_TB_TEA_INFO="create table tb_tea_info (tid integer primary key autoincrement not null,tea_category text not null,tea_price float not null,tea_datetime datetime not null default (datetime('now','localtime')))";

    /**
     * Create Table
     * 采茶信息表
     */
    public static final String CREATE_TB_PICK_TEA_INFO="create table tb_pick_tea_info (uid integer not null,tid integer not null,pick_datetime not null default (datetime('now','localtime')),checkout_date datetime default 0,pick_price_real float not null default 0,pick_weight float not null default 0,pick_income float not null default 0,pick_isCheckout integer not null default 0,serial_number text not null)";

    /**
     * Create Table
     * 采茶结算表
     */
    public static final String CREATE_TB_PICK_CHECKOUT="create table tb_pick_checkout (id integer not null,checkout_date datetime not null default(datetime('now','localtime')),checkout_money float not null default 0,checkout_serial_numbers text not null,checkout_record_serial text not null)";

    /**
     * Create Table
     * 管理员收入表
     */
    public static final String CREATE_TB_INCOME="create table tb_income (id integer primary key autoincrement not null,admin_name text not null default('管理员'),income_money float not null default 0,income_from text(50) not null,income_date datetime not null default(datetime('now','localtime')))";

    /**
     * Set Staff Started Number
     * 设置员工起始编号为1000
     */
    public static final String SET_TB_STAFF_INFO_UID_AUTOINCREMENT_START_NUMBER="insert into sqlite_sequence (name,seq) values ('tb_staff_info',1000)";

    /**
     * Set temp table.-FengJie
     *
     */
    public static final String CREATE_TB_TEMP =
            "CREATE TABLE tb_temp(" +
                    "id integer PRIMARY KEY AUTOINCREMENT," +      //主键
                    "md5_value char(32) NOT NULL," +                        //MD5值
                    "number_list text NOT NULL" +                  //所有未结账编号连成的字符串
                    ")";
    /**
     * 构造函数
     * @param context 上下文
     * @param name 数据库名称
     * @param factory to use for creating cursor objects, or null for the default
     * @param version 数据库版本
     */
    public TeaDBHelper( Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    /**
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TB_STAFF_INFO);
        db.execSQL(CREATE_TB_TEA_INFO);
        db.execSQL(CREATE_TB_PICK_CHECKOUT);
        db.execSQL(CREATE_TB_PICK_TEA_INFO);
        db.execSQL(CREATE_TB_INCOME);
        db.execSQL(SET_TB_STAFF_INFO_UID_AUTOINCREMENT_START_NUMBER);
        db.execSQL(CREATE_TB_TEMP);     //创建临时表
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion)
    {
//        switch (oldVersion)
//        {
//
//        }
    }


}
