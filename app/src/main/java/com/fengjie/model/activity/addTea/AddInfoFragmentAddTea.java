package com.fengjie.model.activity.addTea;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fengjie.model.R;
import com.fengjie.model.dbhelper.Tea.TeaDBUtil;
import com.fengjie.model.dbhelper.Tea.TeaInfo;
import com.fengjie.model.flag.addTea.FlagTeaInfoFragment;
import com.fengjie.model.helper.Other.TimeHelper;
import com.fengjie.model.userdefinedview.CommonDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class AddInfoFragmentAddTea extends Fragment
{

    /**
     * 适配
     */
    private Context mContext;
    /**
     * 以下为工人信息组件
     */
    private View view;
    private EditText variety_EditText_tjcy,price_EditText_tjcy;
    private Button submit_button_tjcy,cancel_button_tjcy;
    /**
     * 测试数据库
     */
    private TeaDBUtil teaDBUtil;
    private TeaInfo info=new TeaInfo();
    /**
     * 自定义控件
     */
    private CommonDialog mCommonDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();

//        EventBus.getDefault().register(this);       //订阅事件

        view = inflater.inflate(R.layout.fragment_addtea_addtea, container,false);
        findView();
        initDialag();

        return view;
    }
    /**
     * 实例化组件
     */
    private void findView() {

        variety_EditText_tjcy = (EditText ) view.findViewById(R.id.category_EditText_addTea);
        price_EditText_tjcy = (EditText ) view.findViewById(R.id.price_EditText_addTea);

        submit_button_tjcy=(Button ) view.findViewById(R.id.submit_button_addTea);
        cancel_button_tjcy=(Button ) view.findViewById(R.id.cancel_button_addTea);
        submit_button_tjcy.setOnClickListener(new mButtonListener());
        cancel_button_tjcy.setOnClickListener(new mButtonListener());

        teaDBUtil=TeaDBUtil.getInstance(mContext);  //取得实例

    }

    /**
     * 取消总线的注册
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
//        FlagGlgrxxActivity flag = new FlagGlgrxxActivity(1);
//        EventBus.getDefault().post(flag);
//
//        EventBus.getDefault().unregister(this);
    }


    @Subscribe(sticky = true)      //缓存事件
    public void onEvent() {

    }

    /**
     * 初始化跳出确认对话框
     */
    private void initDialag(){
        mCommonDialog = new CommonDialog(mContext,R.layout.view_dialog_addtea);
        mCommonDialog.setButtonListener(R.id.submit_button_dialog_tjcy, new View.OnClickListener()
        {
            @Override
            public void onClick ( View v )
            {
                if(teaDBUtil.insertTea(info)>0){
                        FlagTeaInfoFragment flag = new FlagTeaInfoFragment(FlagTeaInfoFragment.UPDATE);
                        EventBus.getDefault().post(flag);
                        Toast.makeText(mContext, "添加成功!", Toast.LENGTH_SHORT).show();
                        variety_EditText_tjcy.setText("");
                        price_EditText_tjcy.setText("");
                    }
                    else
                        Toast.makeText(mContext,"添加失败!", Toast.LENGTH_SHORT).show();
                    mCommonDialog.dismiss();
            }
        });
        mCommonDialog.setButtonListener(R.id.cancel_button_dialog_tjcy, new View.OnClickListener()
        {
            @Override
            public void onClick ( View v )
            {
                mCommonDialog.dismiss();
            }
        });

    }
    /**
     * 初始化按钮监听
     */
    private class mButtonListener implements View.OnClickListener{
        public void onClick(View v){
            switch(v.getId()){
                case R.id.submit_button_addTea:
                    if(variety_EditText_tjcy.getText().toString().equals("")||price_EditText_tjcy.getText().toString().equals("")){
                        Toast.makeText(mContext,"名字或者价格为空,录入失败!", Toast.LENGTH_SHORT).show();
                    }
                    else if (!allTeaCategory(variety_EditText_tjcy.getText().toString())){
                        Toast.makeText(mContext, "品名已存在，录入失败!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else{
                        info.setTea_Category(variety_EditText_tjcy.getText().toString());
                        info.setTea_Price(Float.parseFloat(price_EditText_tjcy.getText().toString()));
                        info.setTea_Datetime(TimeHelper.getCurrentDateTime());

                        mCommonDialog.setText(R.id.category_textView_dialog_tjcy,info.getTea_Category());
                        mCommonDialog.setText(R.id.price_textView_dialog_tjcy,info.getTea_Price()+"");
                        mCommonDialog.show();
                    }
                    break;
                case R.id.cancel_button_addTea:
                    if(!variety_EditText_tjcy.getText().toString().equals("")||!price_EditText_tjcy.getText().toString().equals(""))
                        promptExit();
                    else
                        getActivity().finish();
                    break;
                case R.id.back_button_menu:
                    if(!variety_EditText_tjcy.getText().toString().equals("")||!price_EditText_tjcy.getText().toString().equals(""))
                        promptExit();
                    else
                        getActivity().finish();
                    break;
                default:break;
            }
        }
    }

    private boolean allTeaCategory(String Category){
        List<TeaInfo> list=teaDBUtil.seleteAllTea();
        for (TeaInfo teaInfo:list){
            if (teaInfo.getTea_Category().equals(Category)){
                return false;
            }
        }
        return true;
    }

    private void promptExit(){
        CommonDialog commonDialog = new CommonDialog(mContext);
        commonDialog.initCommonDialog("提示", " 您有信息尚未保存是否退出？", "确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick ( DialogInterface dialog, int which )
            {
                getActivity().finish();
            }
        });
        commonDialog.setCancelable(false);  //设置外部点击失效
        commonDialog.show();
    }




}
