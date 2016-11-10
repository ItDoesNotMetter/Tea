package com.fengjie.model.activity.addWorker.model;

import com.fengjie.model.dbhelper.Staff.StaffInfo;

/**
 * @author Created by FengJie on 2016/11/9-21:04.
 * @brief
 * @attention
 */

public interface IAddWorkerBiz
{
	public boolean isCheckBefore ( final StaffInfo info ,final IIsCheckBeforeToView iIsCheckBeforeToView);

	public void addWorker ( final StaffInfo info, final IAddWorkerToView onClickListener );
}
