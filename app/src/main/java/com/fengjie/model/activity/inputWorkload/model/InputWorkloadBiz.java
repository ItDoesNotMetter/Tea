package com.fengjie.model.activity.inputWorkload.model;

import android.content.Context;
import android.text.TextUtils;

import com.fengjie.model.activity.inputWorkload.Flag_Toast;
import com.fengjie.model.dbhelper.PickTea.PickTeaDBUtil;
import com.fengjie.model.dbhelper.PickTea.PickTeaInfo;
import com.fengjie.model.dbhelper.Staff.StaffDBUtil;
import com.fengjie.model.dbhelper.Staff.StaffInfo;
import com.fengjie.model.dbhelper.Tea.TeaDBUtil;
import com.fengjie.model.dbhelper.Tea.TeaInfo;
import com.fengjie.model.helper.Other.TimeHelper;
import com.fengjie.model.helper.characterParser.CharacterParser;
import com.fengjie.model.helper.suggestion.Suggestion;
import com.fengjie.model.helper.suggestion.TeaSuggestion;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by FengJie on 2016/11/12-9:29.
 * @brief
 * @attention
 */

public class InputWorkloadBiz
{
	private StaffDBUtil staffDBUtil;
	private TeaDBUtil teaDBUtil;
	private PickTeaDBUtil pickTeaDBUtil;
	private List< StaffInfo > mStaffInfoList = new ArrayList< StaffInfo >();
	private List< TeaInfo > mTeaInfoList = new ArrayList< TeaInfo >();
	private CharacterParser mCharacterParser;
	
	public InputWorkloadBiz ( Context context )
	{
		/**get Instance**/
		staffDBUtil = StaffDBUtil.getInstance(context);
		teaDBUtil = TeaDBUtil.getInstance(context);
		pickTeaDBUtil = PickTeaDBUtil.getInstance(context);
		mCharacterParser = CharacterParser.getInstance();
		/**get all database's data**/
		mStaffInfoList = staffDBUtil.selectAllStaff();
		mTeaInfoList = teaDBUtil.seleteAllTea();
	}
	
	/**
	 * use view transmit string.
	 *
	 * @param filterStr get view's input string.
	 * @return
	 */
	public List< Suggestion > filterWorker ( String filterStr )
	{
		List< Suggestion > filterDateList = new ArrayList< Suggestion >();
//		if ( TextUtils.isEmpty(filterStr) )
		if ( filterStr.equals("") || TextUtils.isEmpty(filterStr) )
		{   //判断是否为空
			for ( StaffInfo info : mStaffInfoList )
				filterDateList.add(new Suggestion(info.getStaff_Name(), info.getStaff_id()));
		} else
		{
			filterDateList.clear();             //清除数据
			for ( StaffInfo info : mStaffInfoList )
			{      //遍历数据
				if ( info.getStaff_Name().indexOf(filterStr) != - 1 || mCharacterParser.getSelling(info.getStaff_Name()).startsWith(filterStr) )
				{
					filterDateList.add(new Suggestion(info.getStaff_Name(), info.getStaff_id()));
				}
			}
		}
		return filterDateList;
	}
	
	public List< TeaSuggestion > filterTea ( String filterStr )
	{
		List< TeaSuggestion > filterDateList = new ArrayList< TeaSuggestion >();
//		if ( TextUtils.isEmpty(filterStr) )
		if ( filterStr.equals("") || TextUtils.isEmpty(filterStr) )
		{   //判断是否为空
			for ( TeaInfo info : mTeaInfoList )
				filterDateList.add(new TeaSuggestion(info.getTea_Category(), info.getTea_Id(), info.getTea_Price()));
		} else
		{
			filterDateList.clear();             //清除数据
			for ( TeaInfo info : mTeaInfoList )
			{      //遍历数据
				if ( info.getTea_Category().indexOf(filterStr) != - 1 || mCharacterParser.getSelling(info.getTea_Category()).startsWith(filterStr) )
				{
					filterDateList.add(new TeaSuggestion(info.getTea_Category(), info.getTea_Id(), info.getTea_Price()));
				}
			}
		}
		return filterDateList;
	}
	
	public boolean updateTeaUnitPrice ( String teaCategory, float unitPrice )
	{
		return teaDBUtil.updateTeaUseCategory(new TeaInfo(teaCategory, unitPrice)) > 0;//? true : false
	}
	
	
	/**
	 * give category and unitPrice update tea's unitPrice
	 *
	 * @param teaCategory
	 * @param unitPrice
	 * @return >0 succeed ,otherwise failed
	 */
	public int changeMoney ( String teaCategory, float unitPrice )
	{
		TeaInfo teaInfo = new TeaInfo(teaCategory, unitPrice);
		return teaDBUtil.updateTeaUseCategory(teaInfo);
	}
	
	public Flag_Toast printAndSaveData ( final String name, final String tea, final float uintPrice, final float weight )
	{
		int uid = 0, tid = 0;
		String time = TimeHelper.getCurrentDateTime();
		if ( uintPrice == 0.0f )
			return Flag_Toast.UNIT_PRICE_CANNOT_ZERO;
		else if ( weight == 0.0f )
			return Flag_Toast.WEIGHT_CANNOT_ZERO;
		/**start-checkout worker name*/
		for ( StaffInfo staffInfo : mStaffInfoList )
			if ( staffInfo.getStaff_Name().equals(name) )
			{
				uid = staffInfo.getStaff_id();
				break;
			}
		if ( uid == 0 ) return Flag_Toast.WORKER_NOT_EXIST;
		/**end-checkout worker name*/
		
		/**start-checkout tea name*/
		for ( TeaInfo teaInfo : mTeaInfoList )
			if ( teaInfo.getTea_Category().equals(tea) )
			{
				tid = teaInfo.getTea_Id();
				break;
			}
		if ( tid == 0 ) return Flag_Toast.TEA_NOT_EXIST;
		/**end-checkout tea name*/
		
		
		return pickTeaDBUtil.insertPickTeaInfo(
				new PickTeaInfo(uid, tid, time, weight, uintPrice,
						               String.valueOf(uid) + String.valueOf(tid) + changeValue(weight) +
								               time.substring(5, 7) + time.substring(8, 10) + time.substring(11, 13) +
								               time.substring(14, 16) + time.substring(17, 19) + changeValue(weight * uintPrice),
						               weight * uintPrice)
		) > 0 ? Flag_Toast.ADD_WORKLOAD_SUCCEED : Flag_Toast.ADD_WORKLOAD_FAILED;
	}
	
	public List< StaffInfo > getStaffInfoList ()
	{
		return mStaffInfoList;
	}
	
	
	/**
	 * 将不足位数的数进行补零操作,
	 *
	 * @param temp
	 * @return 补零后的字符串
	 */
	private String changeValue ( float temp )
	{
		
		switch ( ( temp + "" ).length() )
		{
			case 0:
				return "0000";
			case 1:
				return "000" + temp;
			case 2:
				return "00" + temp;
			case 3:
				return "0" + temp;
			case 4:
				return "" + temp;
			default:
				return ( temp + "" ).substring(0, 4);
		}
	}
}
