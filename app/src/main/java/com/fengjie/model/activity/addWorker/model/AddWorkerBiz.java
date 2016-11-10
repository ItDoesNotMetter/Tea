package com.fengjie.model.activity.addWorker.model;

import android.content.Context;

import com.fengjie.model.dbhelper.Staff.StaffDBUtil;
import com.fengjie.model.dbhelper.Staff.StaffInfo;

import java.util.List;

/**
 * @author Created by FengJie on 2016/11/9-20:59.
 * @brief
 * @attention
 */

public class AddWorkerBiz implements IAddWorkerBiz
{
	/**
	 * test database.
	 **/
	private StaffDBUtil staffDBUtil;            //第一步,声明操作类对象
	private Context mContext;

	public AddWorkerBiz ( Context context )
	{
		mContext = context;
		staffDBUtil = StaffDBUtil.getInstance(context);         //init DB
	}

	/**
	 *
	 * @param info  INSERT INTO DATA
	 * @return  true:NO problem ;
	 */
	@Override
	public boolean isCheckBefore ( final StaffInfo info ,final IIsCheckBeforeToView iIsCheckBeforeToView )
	{

		if ( info.getStaff_Name().equals("") )
		{
			iIsCheckBeforeToView.nameIsNull();
			return false;
		} else if ( isExistSameName(info.getStaff_Name()) )
		{
			iIsCheckBeforeToView.nameExist();
			return false;
		} else if ( info.getStaff_Phone().length() > 0 && info.getStaff_Phone().length() < 11 )
		{
			iIsCheckBeforeToView.phoneNumberError();
			return false;
		} else if ( info.getStaff_NRIC().length() > 0 && info.getStaff_NRIC().length() < 18 )
		{
			iIsCheckBeforeToView.idNumberError();
			return false;
		}
		return true;
	}

	/**
	 * @param info
	 * @param onClickListener
	 */
	@Override
	public void addWorker ( final StaffInfo info, final IAddWorkerToView onClickListener )
	{
		if ( staffDBUtil.insertStaff(info) > 0 )
		{
			onClickListener.clearAllEditText();
			onClickListener.addSucceeed();
		} else
		{
			onClickListener.addFailed();
		}

	}

	/**
	 * @param name need to check name.
	 * @return true: exist;false:no exist.
	 */
	private boolean isExistSameName ( String name )
	{
		List< StaffInfo > list = staffDBUtil.selectAllStaff();
		for ( StaffInfo staffInfo : list )
		{
			if ( staffInfo.getStaff_Name().equals(name) )
			{
				return true;
			}
		}
		return false;
	}

}
