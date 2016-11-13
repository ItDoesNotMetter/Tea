package com.fengjie.model.activity.inputWorkload.model;

import android.content.Context;
import android.text.TextUtils;

import com.fengjie.model.dbhelper.PickTea.PickTeaDBUtil;
import com.fengjie.model.dbhelper.Staff.StaffDBUtil;
import com.fengjie.model.dbhelper.Staff.StaffInfo;
import com.fengjie.model.dbhelper.Tea.TeaDBUtil;
import com.fengjie.model.dbhelper.Tea.TeaInfo;
import com.fengjie.model.helper.characterParser.CharacterParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by FengJie on 2016/11/12-9:29.
 * @brief
 * @attention
 */

public class InputWorkloadBiz implements IInputWorkloadBiz
{
	private StaffDBUtil staffDBUtil;
	private TeaDBUtil teaDBUtil;
	private PickTeaDBUtil pickTeaDBUtil;
	private List<StaffInfo> mStaffInfoList =  new ArrayList<StaffInfo>();
	private List<TeaInfo> mTeaInfoList = new ArrayList<TeaInfo>();
	private CharacterParser mCharacterParser  ;
	public InputWorkloadBiz ( Context context)
	{
		staffDBUtil = StaffDBUtil.getInstance(context);     //Get instance
		teaDBUtil = TeaDBUtil.getInstance(context);
		pickTeaDBUtil = PickTeaDBUtil.getInstance(context);
		mStaffInfoList = staffDBUtil.selectAllStaff();
		mTeaInfoList = teaDBUtil.seleteAllTea();
		mCharacterParser = CharacterParser.getInstance();
	}

	@Override
	public List<StaffInfo> addWorker ( String filterStr)
	{
		List<StaffInfo> filterDateList = new ArrayList<StaffInfo>();
		if ( TextUtils.isEmpty(filterStr)) {   //判断是否为空
			filterDateList = mStaffInfoList;
		} else {
			filterDateList.clear();             //清除数据
			for (StaffInfo info : mStaffInfoList) {      //遍历数据
				if (info.getStaff_Name().indexOf(filterStr) != -1 || mCharacterParser.getSelling(info.getStaff_Name()).startsWith(filterStr)) {
					filterDateList.add(info);
				}
			}
		}
		return filterDateList;
	}

	@Override
	public List<TeaInfo> addAllTea (String filterStr)
	{
		List<TeaInfo> teaList = new ArrayList<TeaInfo>();
		return teaList;
	}

	@Override
	public void tempChangeMoney ()
	{

	}

	/**
	 *  give category and unitPrice update tea's unitPrice
	 * @param teaCategory
	 * @param unitPrice
	 * @return  >0 succeed ,otherwise failed
	 */
	@Override
	public int changeMoney ( String teaCategory,float unitPrice )
	{
		TeaInfo teaInfo = new TeaInfo(teaCategory,unitPrice);
		return teaDBUtil.updateTeaUseCategory(teaInfo);
	}

	@Override
	public void printAndSaveData ()
	{

	}

	public List< StaffInfo > getStaffInfoList ()
	{
		return mStaffInfoList;
	}
}
