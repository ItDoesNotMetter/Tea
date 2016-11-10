package com.fengjie.model.activity.addWorker.presenter;

import com.fengjie.model.dbhelper.Staff.StaffInfo;

/**
 * @author Created by FengJie on 2016/11/10-10:35.
 * @brief
 * @attention
 */

public interface IAddWorkerPresenter
{
	public void CheckDataAndShowDialog ();
	public void addWorker( StaffInfo staffInfo);
	public void exit();
}
