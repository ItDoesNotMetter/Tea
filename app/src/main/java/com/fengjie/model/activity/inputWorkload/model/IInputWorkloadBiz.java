package com.fengjie.model.activity.inputWorkload.model;


import com.fengjie.model.dbhelper.Staff.StaffInfo;
import com.fengjie.model.dbhelper.Tea.TeaInfo;

import java.util.List;

/**
 * @author Created by FengJie on 2016/11/12-9:28.
 * @brief
 * @attention
 */

public interface IInputWorkloadBiz
{
	public List< StaffInfo > addWorker ( String filterStr );

	public List< TeaInfo > addAllTea ( String filterStr );

	public void tempChangeMoney ();

	public int changeMoney ( String teaCategory, float money );

	public void printAndSaveData ();


}
