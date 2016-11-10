package com.fengjie.model.helper.log;

import android.util.Log;

/*
 * 自定义的Log工具
 * 提供v(),d(),i(),w(),e()五个自定义方法，在其内部分别调用Log中相应的方法来打印日志
 * 只有当LEVEL常量的值小于或等于对应日志级别值的时候才会将日志打印出来
 * 因此只需要修改LEVEL的值就可以自由地控制日志的打印行为
 * */
public class MyLogUtil
{
    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static final int NOTHING = 6;
    private static final int LEVEL = VERBOSE;

    public static void v( String tag, String msg)
    {
        if (LEVEL <= VERBOSE)
        {
            Log.v(tag, msg);
        }
    }

    public static void d( String tag, String msg)
    {
        if (LEVEL <= DEBUG)
        {
            Log.d(tag, msg);
        }
    }

    public static void i( String tag, String msg)
    {
        if (LEVEL <= INFO)
        {
            Log.i(tag, msg);
        }
    }

    public static void w( String tag, String msg)
    {
        if (LEVEL <= WARN)
        {
            Log.w(tag, msg);
        }
    }

    public static void e( String tag, String msg)
    {
        if (LEVEL <= ERROR)
        {
            Log.e(tag, msg);
        }
    }
}