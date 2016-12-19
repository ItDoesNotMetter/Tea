package com.fengjie.model.activity.inputWorkload.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.fengjie.model.R;
import com.fengjie.model.activity.inputWorkload.Flag;
import com.fengjie.model.dbhelper.PickTea.PickTeaDBUtil;
import com.fengjie.model.dbhelper.PickTea.PickTeaInfo;
import com.fengjie.model.dbhelper.Staff.StaffDBUtil;
import com.fengjie.model.dbhelper.Staff.StaffInfo;
import com.fengjie.model.dbhelper.Tea.TeaDBUtil;
import com.fengjie.model.dbhelper.Tea.TeaInfo;
import com.fengjie.model.helper.Other.TimeHelper;
import com.fengjie.model.helper.characterParser.CharacterParser;
import com.fengjie.model.helper.printer.PrintService;
import com.fengjie.model.helper.printer.PrinterClass;
import com.fengjie.model.helper.printer.PrinterClassSerialPort;
import com.fengjie.model.helper.suggestion.Suggestion;
import com.fengjie.model.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

import static com.fengjie.model.activity.inputWorkload.Flag.PRINT_FIALD;

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

	/** parameter **/
	private Context mContext;
	private Flag IS_PRINTER_EXIST = Flag.PRINTER_EXIST;     //
	private static PrinterClassSerialPort printerClass = null;          //PrinterClassSerialPort
	
	public InputWorkloadBiz ( Context context )
	{
		/**get Instance**/
		mContext = context ;
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
	public List< Suggestion > getFilterWorker ( String filterStr )
	{
		List< Suggestion > filterDateList = new ArrayList< Suggestion >();
//		if ( TextUtils.isEmpty(filterStr) )
		if ( filterStr.equals("") || TextUtils.isEmpty(filterStr) )
		{   //判断是否为空
			for ( StaffInfo info : mStaffInfoList )
				filterDateList.add(new Suggestion(info.getStaff_Name()));
		} else
		{
			filterDateList.clear();             //清除数据
			for ( StaffInfo info : mStaffInfoList )
			{      //遍历数据
				if ( info.getStaff_Name().indexOf(filterStr) != - 1 || mCharacterParser.getSelling(info.getStaff_Name()).startsWith(filterStr) )
				{
					filterDateList.add(new Suggestion(info.getStaff_Name()));
				}
			}
		}
		return filterDateList;
	}
	
	public List< Suggestion > getFilterTea ( String filterStr )
	{
		List< Suggestion > filterDateList = new ArrayList< Suggestion >();
//		if ( TextUtils.isEmpty(filterStr) )
		if ( filterStr.equals("") || TextUtils.isEmpty(filterStr) )
		{   //判断是否为空
			for ( TeaInfo info : mTeaInfoList )
				filterDateList.add(new Suggestion(info.getTea_Category()));
		} else
		{
			filterDateList.clear();             //清除数据
			for ( TeaInfo info : mTeaInfoList )
			{      //遍历数据
				if ( info.getTea_Category().indexOf(filterStr) != - 1 || mCharacterParser.getSelling(info.getTea_Category()).startsWith(filterStr) )
				{
					filterDateList.add(new Suggestion(info.getTea_Category()));
				}
			}
		}
		return filterDateList;
	}

	/**
	 * @return all worker number.
	 */
	public int getWorkerSize()
	{
		return staffDBUtil.selectAllStaff().size();
	}

	/**
	 * @return all tea number.
	 */
	public int getTeaSize()
	{
		return teaDBUtil.seleteAllTea().size();
	}

	public float isExistTea ( String tea )
	{
		for ( TeaInfo info : mTeaInfoList )
			if(info.getTea_Category().equals(tea))
				return info.getTea_Price();
		return 0.0f;
	}

	public boolean isExistName(String name)
	{
		for ( StaffInfo info : mStaffInfoList )
			if(info.getStaff_Name().equals(name))
				return true;
		return false;
	}

	public boolean updateTeaUnitPrice ( String teaCategory, float unitPrice )
	{
		return teaDBUtil.updateTeaUseCategory(new TeaInfo(teaCategory, unitPrice)) > 0;//? true : false
	}
	
	public void updateTeaUnitPrice ()
	{
		mTeaInfoList = teaDBUtil.seleteAllTea();
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
	
	public Flag printAndSaveData ( final String name, final String tea, final float uintPrice, final float weight , Bitmap bitmap)
	{
		int uid = 0, tid = 0;
		String time = TimeHelper.getCurrentDateTime();
		if ( uintPrice == 0.0f )
			return Flag.UNIT_PRICE_CANNOT_ZERO;
		else if ( weight == 0.0f )
			return Flag.WEIGHT_CANNOT_ZERO;
		/**start-checkout worker name*/
		for ( StaffInfo staffInfo : mStaffInfoList )
			if ( staffInfo.getStaff_Name().equals(name) )
			{
				uid = staffInfo.getStaff_id();
				break;
			}
		if ( uid == 0 ) return Flag.WORKER_NOT_EXIST;
		/**end-checkout worker name*/
		
		/**start-checkout tea name*/
		for ( TeaInfo teaInfo : mTeaInfoList )
			if ( teaInfo.getTea_Category().equals(tea) )
			{
				tid = teaInfo.getTea_Id();
				break;
			}
		if ( tid == 0 ) return Flag.CATEGORY_NOT_EXIST;
		/**end-checkout tea name*/
		
		try{
			printerClass.printImage(bitmap);
		} catch( Exception e )
		{
			e.printStackTrace();
			Log.d("Debug", "printAndSaveData: Fail Print");
			return PRINT_FIALD;
		}

		return pickTeaDBUtil.insertPickTeaInfo(
				new PickTeaInfo(uid, tid, time, weight, uintPrice,
						               String.valueOf(uid) + String.valueOf(tid) + changeValue(weight) +
								               time.substring(5, 7) + time.substring(8, 10) + time.substring(11, 13) +
								               time.substring(14, 16) + time.substring(17, 19) + changeValue(weight * uintPrice),
						               weight * uintPrice)
		) > 0 ? Flag.ADD_WORKLOAD_SUCCEED : Flag.ADD_WORKLOAD_FAILED;
	}

	/**
	 * Use Handle init Print Device.
	 */
	public Flag openPrinter ()
	{

		try
		{
			Handler mhandler = new Handler()
			{
				public void handleMessage ( Message msg )
				{
					switch ( msg.what )
					{
						case PrinterClass.MESSAGE_READ:
							byte[] readBuf = ( byte[] ) msg.obj;
//							Log.i(TAG, "readBuf:" + readBuf[0]);
							if ( readBuf[0] == 0x13 )
							{
								// PrintService.isFUll = true;
								// ShowMsg(mContext.getResources().getString(R.string.str_printer_state)+":"+mContext.getResources().getString(R.string.str_printer_bufferfull));
							} else if ( readBuf[0] == 0x11 )
							{
								// PrintService.isFUll = false;
								// ShowMsg(mContext.getResources().getString(R.string.str_printer_state)+":"+mContext.getResources().getString(R.string.str_printer_buffernull));
							} else if ( readBuf[0] == 0x08 )
							{
								MyToast.showLong(mContext,mContext.getResources().getString(
										R.string.str_printer_state)
										                 + ":"
										                 + mContext.getResources().getString(
										R.string.str_printer_nopaper));
							} else if ( readBuf[0] == 0x01 )
							{
								// ShowMsg(mContext.getResources().getString(R.string.str_printer_state)+":"+mContext.getResources().getString(R.string.str_printer_printing));
							} else if ( readBuf[0] == 0x04 )
							{
								MyToast.showLong(mContext,mContext.getResources().getString(
										R.string.str_printer_state)
										                          + ":"
										                          + mContext.getResources().getString(
										R.string.str_printer_hightemperature));
							} else if ( readBuf[0] == 0x02 )
							{
								MyToast.showLong(mContext,mContext.getResources().getString(
										R.string.str_printer_state)
										                          + ":"
										                          + mContext.getResources().getString(
										R.string.str_printer_lowpower));
							} else
							{
								String readMessage = new String(readBuf, 0, msg.arg1);
								if ( readMessage.contains("800") )// 80mm paper
								{
									PrintService.imageWidth = 72;
									Toast.makeText(mContext, "80mm",
											Toast.LENGTH_SHORT).show();
								} else if ( readMessage.contains("580") )// 58mm paper
								{
									PrintService.imageWidth = 48;
									Toast.makeText(mContext, "58mm",
											Toast.LENGTH_SHORT).show();
								} else
								{

								}
							}
							break;
						case PrinterClass.MESSAGE_STATE_CHANGE:// 6��l��״
							switch ( msg.arg1 )
							{
								case PrinterClass.STATE_CONNECTED:// �Ѿ�l��
									break;
								case PrinterClass.STATE_CONNECTING:// ����l��
									Toast.makeText(mContext,
											"STATE_CONNECTING", Toast.LENGTH_SHORT).show();
									break;
								case PrinterClass.STATE_LISTEN:
								case PrinterClass.STATE_NONE:
									break;
								case PrinterClass.SUCCESS_CONNECT:
									printerClass.write(new byte[]{ 0x1b, 0x2b });// ����ӡ���ͺ�
									Toast.makeText(mContext,
											"SUCCESS_CONNECT", Toast.LENGTH_SHORT).show();
									break;
								case PrinterClass.FAILED_CONNECT:
									Toast.makeText(mContext,
											"FAILED_CONNECT", Toast.LENGTH_SHORT).show();

									break;
								case PrinterClass.LOSE_CONNECT:
									Toast.makeText(mContext, "LOSE_CONNECT",
											Toast.LENGTH_SHORT).show();
							}
							break;
						case PrinterClass.MESSAGE_WRITE:

							break;
					}
					super.handleMessage(msg);
				}
			};

			printerClass = new PrinterClassSerialPort(mhandler);
			printerClass.open(mContext);
		return Flag.PRINTER_EXIST;
//			mView.showToastInView(Flag.PRINTER_EXIST);
		} catch( UnsatisfiedLinkError e )
		{
			e.printStackTrace();
			IS_PRINTER_EXIST = Flag.PRINTER_NOT_EXIST;
			return Flag.PRINTER_NOT_EXIST;
//			mView.showToastInView(Flag.PRINTER_NOT_EXIST);
		}

	}
	/**
	 * close Printer , prevent internal storage  let out .
	 */
	public void closePrinter ()
	{
		if ( IS_PRINTER_EXIST == Flag.PRINTER_EXIST )
			printerClass.close(mContext);
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
