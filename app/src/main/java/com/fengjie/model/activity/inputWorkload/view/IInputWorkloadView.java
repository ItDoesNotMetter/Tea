package com.fengjie.model.activity.inputWorkload.view;

import com.fengjie.model.dbhelper.Staff.StaffInfo;

import java.util.List;

/**
 * @author Created by FengJie on 2016/11/11-21:50.
 * @brief
 * @attention
 */

public interface IInputWorkloadView
{

	public String getName ();

	public String getCategory ();

	public float getUnitPrice ();

	public float getWeight ();

	public float getMoney ();

	public void showOrHideWorkerInfoWindow ();

	public void showOrHideTeaInfoWindow ();

	public void showOrHideDialog ();

	public void showAddSucceed ();

	public void showAddFailed ();

	public void showWorkerInfoWindow ( List< StaffInfo > staffInfoList );
}
