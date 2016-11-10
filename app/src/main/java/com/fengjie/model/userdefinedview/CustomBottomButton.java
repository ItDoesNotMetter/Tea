package com.fengjie.model.userdefinedview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.fengjie.model.R;


public class CustomBottomButton extends LinearLayout
{

    public final static boolean SHOW = true ;
    public final static boolean HIDE = false ;
    private Button left_button_bottomButton_moudule,right_button_bottomButton_moudule,moreFuntion_button_bottomButton_moudule;

    public CustomBottomButton ( Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_bottombutton,this);   //不能删除此句
        initView();
    }
    /**
     * 实例化组件，进行简单的初始化
     */
    private void initView(){

        left_button_bottomButton_moudule = (Button ) findViewById(R.id.left_button_bottomButton_moudule);
        right_button_bottomButton_moudule = (Button ) findViewById(R.id.right_button_bottomButton_moudule);
        moreFuntion_button_bottomButton_moudule = (Button ) findViewById(R.id.moreFunction_button_bottomButton_moudule);
    }

    /**
     * 初始化方法
     * @param rightText 单独一个按钮text
     * @param onClickListener
     */
    public void init( String rightText, OnClickListener onClickListener){

        right_button_bottomButton_moudule.setText(rightText);

        if(onClickListener!=null){
            right_button_bottomButton_moudule.setOnClickListener(onClickListener);
        }

    }

    /**
     * 初始化方法
     * @param leftText  左边按钮text
     * @param rightText 右边按钮text
     * @param onClickListener
     */
    public void init( String leftText, String rightText, OnClickListener onClickListener){

        left_button_bottomButton_moudule.setText(leftText);
        right_button_bottomButton_moudule.setText(rightText);

        if(onClickListener!=null){
            left_button_bottomButton_moudule.setOnClickListener(onClickListener);
            right_button_bottomButton_moudule.setOnClickListener(onClickListener);
        }
    }

    /**
     * 初始化方法
     * @param leftText   左边按钮text
     * @param rightText 中间按钮text
     * @param funtionButtonText  右边按钮text
     * @param onClickListener
     */
    public void init( String leftText, String rightText, String funtionButtonText, OnClickListener onClickListener){

        left_button_bottomButton_moudule.setText(leftText);
        right_button_bottomButton_moudule.setText(rightText);
        moreFuntion_button_bottomButton_moudule.setText(funtionButtonText);

        if(onClickListener!=null){
            left_button_bottomButton_moudule.setOnClickListener(onClickListener);
            right_button_bottomButton_moudule.setOnClickListener(onClickListener);
            moreFuntion_button_bottomButton_moudule.setOnClickListener(onClickListener);
        }
    }

    /**
     * 更换右边按钮的颜色
     * @param ID
     */
    public void setAllButtonSameColor(int ID){
        right_button_bottomButton_moudule.setBackgroundResource(ID);
        left_button_bottomButton_moudule.setBackgroundResource(ID);
        moreFuntion_button_bottomButton_moudule.setBackgroundResource(ID);
    }
    public void setRightButtonBackground(int ID){
        right_button_bottomButton_moudule.setBackgroundResource(ID);
    }

    public void setLeftButtonBackground(int ID){
        left_button_bottomButton_moudule.setBackgroundResource(ID);
    }


    public void setMoreFuntionButtonBackground(int ID){
        moreFuntion_button_bottomButton_moudule.setBackgroundResource(ID);
    }
    /**
     * 隐藏Button方法
     * @param flag 为真则显示,反之隐藏
     */
    public void setLeftButtonVisibility(boolean flag){
        left_button_bottomButton_moudule.setVisibility(flag? View.VISIBLE: View.GONE);
    }

    public void setRightButtonVisibility(boolean flag){
        right_button_bottomButton_moudule.setVisibility(flag? View.VISIBLE: View.GONE);
    }

    public void setfuntionButtonView(boolean flag){
        moreFuntion_button_bottomButton_moudule.setVisibility(flag? View.VISIBLE: View.GONE);
    }


}
