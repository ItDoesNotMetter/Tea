package com.fengjie.model.userdefinedview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fengjie.model.R;
import com.fengjie.percentlibrary.PercentLinearLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class Menu extends PercentLinearLayout
{

	public final static boolean HIDE = false;
	public final static boolean SHOW = true;
	//
	private ImageButton operate_imageButton_menu;
	private ImageButton back_button_menu;
	private TextView title_textView_menu;
	private PopupWindow mPopupWindow;          // popupwindow
	private ListView listView;                 // popupwindow中的ListView
	private int NUM_OF_VISIBLE_LIST_ROWS;     // 指定popupwindow中Item的数量
	private View layout;
//    private RelativeLayout relativeLayout_menu;

	public Menu ( Context context, AttributeSet attrs )
	{
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_menu, this);       //传入需要加载的布局文件,传入父布局
		layout = LayoutInflater.from(context).inflate(R.layout.listview_common, null);
		initView();
	}

	/**
	 * Find user defined view by id and init left button's onClickListener.
	 */
	private void initView ()
	{

//        LayoutInflater inflater = (LayoutInflater) getContext()
//                .getSystemService(getContext().LAYOUT_INFLATER_SERVICE);

//        layout = LayoutInflater.from(context).inflate(R.layout.listview_popupwindow, null);

//        relativeLayout_menu = (RelativeLayout) findViewById(R.id.relativeLayout_menu);

		listView = ( ListView ) layout.findViewById(R.id.id_listView_common);

		title_textView_menu = ( TextView ) findViewById(R.id.title_textView_menu);

		operate_imageButton_menu = ( ImageButton ) findViewById(R.id.operate_imageButton_menu);

		back_button_menu = ( ImageButton ) findViewById(R.id.back_button_menu);

		back_button_menu.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick ( View v )
			{
				( ( Activity ) getContext() ).finish();
			}
		}); /**默认的按钮点击事件*/
	}

	/**
	 * Right button no popupWindow,only have OnClickListener.
	 *
	 * @param string          get title string object.
	 * @param onClickListener right button onClickListener.
	 */
	public void init ( final String string, final View.OnClickListener onClickListener )
	{

		this.title_textView_menu.setText(string);

		if ( onClickListener != null )
			operate_imageButton_menu.setOnClickListener(onClickListener);
	}


	/**
	 * init title and ListView's OnItemClickListener.
	 *
	 * @param string              get title string object.
	 * @param groups              get ListView's string list.
	 * @param onItemClickListener
	 */
	public void init ( final String string, final List< String > groups, final AdapterView.OnItemClickListener onItemClickListener )
	{

		this.title_textView_menu.setText(string);

		if ( groups != null )
		{
			this.NUM_OF_VISIBLE_LIST_ROWS = groups.size();


			listView.setAdapter(new CommonAdapter< String >(getContext(), R.layout.item_listview_common, groups)
			{
				@Override
				public void convert ( ViewHolder holder, String o, int pos )
				{
					holder.setText(R.id.groupItem_listView_menu, groups.get(pos));
				}

			});

			if ( onItemClickListener != null )
				listView.setOnItemClickListener(onItemClickListener);

			// 控制popupwindow的宽度和高度自适应
			listView.measure(View.MeasureSpec.UNSPECIFIED,
					View.MeasureSpec.UNSPECIFIED + 30);

			initPopupWindow();
		}

	}

	/**
	 * Init PopupWindow
	 */
	private void initPopupWindow ()
	{


		mPopupWindow = new PopupWindow(layout);
//        mPopupWindow = new PopupWindow();

		mPopupWindow.setFocusable(true);// 加上这个popupwindow中的ListView才可以接收点击事件

		mPopupWindow.setWidth(listView.getMeasuredWidth() + 60);

		mPopupWindow.setHeight(( listView.getMeasuredHeight() ) + 90
				                                                          * NUM_OF_VISIBLE_LIST_ROWS);
		mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);  //设置动画
		//控制popupwindow点击屏幕其他地方消失
//        mPopupWindow.setBackgroundDrawable(this.getResources().getDrawable(
//                R.mipmap.bg_popupwindow));// 设置背景图片，不能在布局中设置，要通过代码来设置
		mPopupWindow.setBackgroundDrawable(this.getResources().getDrawable(
				R.drawable.bg_touming_menu));// 设置背景图片，不能在布局中设置，要通过代码来设置
		mPopupWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上

//		int[] location = new int[2];
//		v.getLocationOnScreen(location);
		/**在控件下方*/
//		popupWindow.showAsDropDown(v);
		/**在控件上方*/
		//popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1]-popupWindow.getHeight());
		/**在控件左方*/
//		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0]-popupWindow.getWidth(), location[1]);
		/**在控件右方*/
//		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0]+v.getWidth(), location[1]);
		// 更多操作按钮

		operate_imageButton_menu.setOnClickListener(new oldOnClickListener());

	}

	/**
	 * 赋入OnclickListener 实例化接口,对Back点击事件进行重载
	 *
	 * @param onClickListener
	 */
	public void setBackOnClickListener ( OnClickListener onClickListener )
	{
		back_button_menu.setOnClickListener(onClickListener);
	}

	/**
	 * 构建item点击事件
	 *
	 * @param onItemClickListener
	 */
	public void setOnItemClickListener ( AdapterView.OnItemClickListener onItemClickListener )
	{
		listView.setOnItemClickListener(onItemClickListener);
	}

	/**
	 * 变更标题方法
	 *
	 * @param title
	 */
	public void setTitle_textView_menu ( String title )
	{
		title_textView_menu.setText(title);
	}

	/**
	 * 更改字体大小
	 *
	 * @param textSize
	 */
	public void setTitleSize_textView_menu ( float textSize )
	{
		title_textView_menu.setTextSize(textSize);
	}

	/**
	 * 更改字体颜色方法
	 *
	 * @param textColor
	 */
	public void setTitleColor_textView_menu ( int textColor )
	{
		title_textView_menu.setTextColor(textColor);
	}

	/**
	 * 更换右边按钮的图片
	 *
	 * @param ID
	 */
	public void setRightButtonBackground ( int ID )
	{
		operate_imageButton_menu.setBackgroundResource(ID);
	}

	public void setLeftButtonBackground ( int ID )
	{
		back_button_menu.setImageDrawable(getResources().getDrawable(ID));
	}


	/**
	 * 隐藏Menu-操作Button方法
	 *
	 * @param flag 为真则显示,反之隐藏
	 */
	public void setLeftButtonVisibility ( boolean flag )
	{
		back_button_menu.setVisibility(flag ? View.VISIBLE : View.GONE);
	}

	public void setRightButtonVisibility ( boolean flag )
	{
		operate_imageButton_menu.setVisibility(flag ? View.VISIBLE : View.GONE);
	}

	/**
	 * 重载操作按钮监听事件
	 *
	 * @param newOnClickListener 赋入新监听事件的方法
	 */
	public void setRightButtonOnClickListener ( View.OnClickListener newOnClickListener )
	{
		if ( newOnClickListener != null )
			operate_imageButton_menu.setOnClickListener(newOnClickListener);    //建立新的监听事件
		else
			operate_imageButton_menu.setOnClickListener(new oldOnClickListener());//建立原本的监听事件
	}

	public void setLeftButtonOnClickListener ( View.OnClickListener newOnClickListener )
	{
		if ( newOnClickListener != null )
			back_button_menu.setOnClickListener(newOnClickListener);    //建立新的监听事件
	}

	/**
	 * open/close menu method
	 */
	private class oldOnClickListener implements View.OnClickListener
	{

		@Override
		public void onClick ( View v )
		{
			close();
		}
	}

	/**
	 * open/close menu method
	 */
	public void close ()
	{
		if ( mPopupWindow.isShowing() )
		{
			mPopupWindow.dismiss();// 关闭
		} else
		{
			mPopupWindow.showAsDropDown(operate_imageButton_menu);// 显示
		}
	}

}
