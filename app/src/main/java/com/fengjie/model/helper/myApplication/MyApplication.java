package com.fengjie.model.helper.myApplication;

import android.app.Application;
import android.content.res.Configuration;
//import com.tencent.bugly.Bugly;

/**通常将\初始化(数据)方法\各组件数据交互(方法)\全局变量\**/
public class MyApplication extends Application
{

    @Override
    public void onCreate() {            //在创建应用程序时调用此方法
        super.onCreate();

//        Bugly.init(getApplicationContext(), "900053404", false);    //版本更新初始化

    }

    /**
     * 当终止应用程序对象时调用，不保证一定被调用，当程序是被内核终止以便为其他应用程序释放资源，
     * 则不提醒,并且不调用应用程序的对象的onTerminate方法而直接终止进程
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 配置改变时触发这个方法
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 当后台程序已经终止资源还匮乏时会调用这个方法
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
