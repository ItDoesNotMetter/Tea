package com.fengjie.model.userdefinedview;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


/**
 * @author Created by FengJie on 2016/11/29-11:13.
 * @brief
 * @attention
 */

public class CommonDialog extends android.support.v7.app.AlertDialog
{
	private View mView;

	public CommonDialog ( @NonNull Context context )
	{
		super(context);
	}

	public CommonDialog ( @NonNull Context context, final int id )
	{
		super(context);
		mView = LayoutInflater.from(getContext()).inflate(id, null);   //example as id = R.layout.view_dialog_ensure
		super.setView(mView);
	}


	/**
	 * Only have title of dialog.
	 *
	 * @param title
	 */
	public void initCommonDialog ( final String title )
	{
		super.setTitle(title);
	}

	/**
	 * Only have title and content of dialog.
	 *
	 * @param title
	 */
	public void initCommonDialog ( final String title, final String message )
	{

		super.setTitle(title);
		super.setMessage(message);
		/**默认退出按钮为退出*/
		super.setButton(DialogInterface.BUTTON_NEGATIVE, "退出", new OnClickListener()
		{
			@Override
			public void onClick ( DialogInterface dialog, int which )
			{
				dismiss();
			}
		});
	}

	/**
	 * No title and have right button of dialog
	 *
	 * @param rightButtonContent
	 * @param rightOnClickListener
	 */
	public void initCommonDialog ( final String rightButtonContent, OnClickListener rightOnClickListener )
	{
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);


		super.setButton(DialogInterface.BUTTON_POSITIVE, rightButtonContent, rightOnClickListener);
		super.setButton(DialogInterface.BUTTON_NEGATIVE, "退出", new OnClickListener()
		{
			@Override
			public void onClick ( DialogInterface dialog, int which )
			{
				dismiss();
			}
		});

	}

	/**
	 * Have title and right button of dialog
	 *
	 * @param rightButtonContent
	 * @param rightOnClickListener
	 */
	public void initCommonDialog ( final String title, final String rightButtonContent, OnClickListener rightOnClickListener )
	{
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.setTitle(title);

		super.setButton(DialogInterface.BUTTON_POSITIVE, rightButtonContent, rightOnClickListener);
		super.setButton(DialogInterface.BUTTON_NEGATIVE, "退出", new OnClickListener()
		{
			@Override
			public void onClick ( DialogInterface dialog, int which )
			{
				dismiss();
			}
		});

	}

	/**
	 * use id get the widget
	 *
	 * @param viewId
	 * @return
	 */
	public < T extends View > T getView ( final int viewId )
	{

		View view = mView.findViewById(viewId);
		return ( T ) view;
	}

	public void setText ( final int viewId, final String text )
	{
		TextView tv = getView(viewId);
		tv.setText(text);
	}

	public void setButtonListener ( final int viewId, View.OnClickListener onClickListener )
	{
		Button btn = getView(viewId);
		btn.setOnClickListener(onClickListener);
	}




}
