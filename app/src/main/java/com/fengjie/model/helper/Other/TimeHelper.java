package com.fengjie.model.helper.Other;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by xiong on 2016/4/15.
 * 获取手机当前时间
 */
public class TimeHelper
{
    public static String getCurrentDateTime()
    {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate=new Date(System.currentTimeMillis());//获取当前时间
        return format.format(curDate);
    }
}
