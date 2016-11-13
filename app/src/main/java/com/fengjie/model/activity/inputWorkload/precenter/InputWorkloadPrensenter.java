package com.fengjie.model.activity.inputWorkload.precenter;

import android.content.Context;

import com.fengjie.model.activity.inputWorkload.model.IInputWorkloadBiz;
import com.fengjie.model.activity.inputWorkload.model.InputWorkloadBiz;
import com.fengjie.model.activity.inputWorkload.view.IInputWorkloadView;

/**
 * @author Created by FengJie on 2016/11/12-10:29.
 * @brief
 * @attention
 */

public class InputWorkloadPrensenter implements IIputWorkloadPrensenter
{
	private IInputWorkloadBiz mBiz ;
	private IInputWorkloadView mView;
	private Context mContext;

	public InputWorkloadPrensenter ( Context context,IInputWorkloadView view )
	{
		mContext = context;
		mView = view;
		mBiz = new InputWorkloadBiz(mContext);
	}


	@Override
	public void showWorkers (String s)
	{
		mView.showWorkerInfoWindow(mBiz.addWorker(s));
	}

	@Override
	public void showOrHideTea ()
	{

	}

	@Override
	public void saveTeaUnitPrice ()
	{

	}

	@Override
	public void printAndSaveData ()
	{

	}

	@Override
	public void exit ()
	{

	}
}
