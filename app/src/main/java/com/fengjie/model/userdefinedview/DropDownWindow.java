package com.fengjie.model.userdefinedview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.fengjie.model.R;
import com.fengjie.model.dbhelper.Staff.StaffInfo;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

/**
 * @author Created by FengJie on 2016/11/11-11:56.
 * @brief
 * @attention
 */

public class DropDownWindow< T > extends AutoLinearLayout
{

	private ListView listView;                 // popupwindow中的ListView
	private PopupWindow mPopupWindow;          // popupwindow
	private View layout;
	private View mView;
	public static final boolean HIDE = false;
	public static final boolean SHOW = true;
	private List< StaffInfo > list;
	private CommonAdapter listAdapter;

	public DropDownWindow ( Context context, final View view, final int width, final int screenHeight, final List< StaffInfo > list )
	{
		super(context);
//		LayoutInflater.from(context).inflate(R.layout.view_menu, this);       //传入需要加载的布局文件,传入父布局
		layout = LayoutInflater.from(context).inflate(R.layout.listview_common, this);
		listView = ( ListView ) layout.findViewById(R.id.id_listView_common);
		this.list = list;
		listAdapter = new CommonAdapter< StaffInfo >(getContext(), R.layout.item_listview_common, this.list)
		{
			@Override
			public void convert ( ViewHolder holder, StaffInfo o, int pos )
			{
				holder.setText(R.id.groupItem_listView_menu, list.get(pos).getStaff_Name());
			}

		};
		listView.setAdapter(listAdapter);
		listView.measure(View.MeasureSpec.UNSPECIFIED,    // 控制popupWindow的宽度和高度自适应
				View.MeasureSpec.UNSPECIFIED + 30);
		/****line between***/
		this.mView = view;
		mPopupWindow = new PopupWindow(layout);
//		mPopupWindow.showAsDropDown(view);  //set popup window down in view.
		mPopupWindow.setFocusable(true);// only add this method popupWindow's listView can receive onClickListener.
		mPopupWindow.setWidth(width * 6 / 11);
//		mPopupWindow.setHeight(listView.getMeasuredHeight() * list.size());       //get screen height.
		mPopupWindow.setHeight(screenHeight / 2);
		mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);  //设置动画
		mPopupWindow.setBackgroundDrawable(this.getResources().getDrawable(
				R.drawable.bg_touming_menu));           // 设置背景图片，不能在布局中设置，要通过代码来设置
		mPopupWindow.setOutsideTouchable(true);     //touch popupWindow outside can close it.
	}

	public void updateListView ( final List< StaffInfo > list )
	{
		this.list = list;
//		mPopupWindow.setHeight(listView.getMeasuredHeight() * list.size());       //get screen height.
		listAdapter.notifyDataSetChanged();
	}


	public void showOrHideWindow ()
	{
		if ( mPopupWindow.isShowing() )
		{
			mPopupWindow.dismiss();// 关闭

		} else
		{
			mPopupWindow.showAsDropDown(mView);// 显示

		}
	}

	public void show ()
	{
		mPopupWindow.showAsDropDown(mView);// 显示
	}

	public boolean isShowing ()
	{
		return mPopupWindow.isShowing() ? SHOW : HIDE;
	}
}
