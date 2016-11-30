package com.fengjie.model.activity.inputWorkload.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.fengjie.model.R;
import com.fengjie.model.activity.inputWorkload.Flag;
import com.fengjie.model.activity.inputWorkload.model.InputWorkloadBiz;
import com.fengjie.model.activity.inputWorkload.view.IInputWorkloadView;
import com.fengjie.model.helper.printer.PrintService;
import com.fengjie.model.helper.printer.PrinterClass;
import com.fengjie.model.helper.printer.PrinterClassSerialPort;
import com.fengjie.model.helper.suggestion.Suggestion;

import java.util.List;

/**
 * @author Created by FengJie on 2016/11/12-10:29.
 * @brief
 * @attention
 */

public class InputWorkloadPresenter
{
	/** interface */
	private InputWorkloadBiz mBiz;
	private IInputWorkloadView mView;
	/** instance */
	private Context mContext;

	/** parameter **/
	private Flag IS_PRINTER_EXIST = Flag.PRINTER_EXIST;


	public static PrinterClassSerialPort printerClass = null;          //PrinterClassSerialPort

	public InputWorkloadPresenter ( Context context, IInputWorkloadView view )
	{
		mContext = context;
		mView = view;
		mBiz = new InputWorkloadBiz(mContext);
		openPrinter();
	}

	public List< Suggestion > filterWorkers ()
	{
		return mBiz.filterWorker(mView.getName());
	}

	public List< Suggestion > filterTea ()
	{
		return mBiz.filterTea(mView.getCategory());
	}


	public void saveTeaUnitPrice ()
	{
		try
		{
			mView.showToastInView(  //This method receive flag that will be show Toast.
					mBiz.updateTeaUnitPrice(mView.getCategory(), mView.getUnitPrice())      //Update succeed will return true,otherwise.
							? Flag.SAVE_UNIT_PRICE_SUCCEED : Flag.SAVE_UNIT_PRICE_FAILED);
			mBiz.updateTeaUnitPrice();
//		} catch( IllegalArgumentException exception )
		} catch( Exception exception )
		{
			mView.showToastInView(Flag.SAVE_UNIT_PRICE_FAILED);
			exception.printStackTrace();        //Print error.
		}
	}

	/**
	 * print data and save data.
	 */
	public void printAndSaveData ()
	{
		mView.showToastInView(
				mBiz.printAndSaveData(mView.getName(), mView.getCategory(), mView.getUnitPrice(), mView.getWeight())  //this method will return FLAG.
		);
	}

	public boolean isExistName(String name)
	{
		if( mBiz.isExistName(name))
		{
			mView.showToastInView(Flag.NAME_EXIST);
			return true;
		}
		return false;
	}

	public float isExistTea ( String tea )
	{
		if ( mBiz.isExistTea(tea) > 0.0f )
		{
			mView.showToastInView(Flag.CATEGORY_EXIST);
			return mBiz.isExistTea(tea);
		}
		else
			return -0.1f;
	}

	public void exit ()
	{

	}

	/**
	 * Use Handle init Print Device.
	 */
	private void openPrinter ()
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
								ShowMsg(mContext.getResources().getString(
										R.string.str_printer_state)
										        + ":"
										        + mContext.getResources().getString(
										R.string.str_printer_nopaper));
							} else if ( readBuf[0] == 0x01 )
							{
								// ShowMsg(mContext.getResources().getString(R.string.str_printer_state)+":"+mContext.getResources().getString(R.string.str_printer_printing));
							} else if ( readBuf[0] == 0x04 )
							{
								ShowMsg(mContext.getResources().getString(
										R.string.str_printer_state)
										        + ":"
										        + mContext.getResources().getString(
										R.string.str_printer_hightemperature));
							} else if ( readBuf[0] == 0x02 )
							{
								ShowMsg(mContext.getResources().getString(
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
			mView.showToastInView(Flag.PRINTER_EXIST);
		} catch( UnsatisfiedLinkError e )
		{
			e.printStackTrace();
			IS_PRINTER_EXIST = Flag.PRINTER_NOT_EXIST;
			mView.showToastInView(Flag.PRINTER_NOT_EXIST);
		}

	}

	private void ShowMsg ( String msg )
	{
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * close Printer , prevent internal storage  let out .
	 */
	public void closePrinter ()
	{
		if ( IS_PRINTER_EXIST == Flag.PRINTER_EXIST )
			printerClass.close(mContext);
	}
}
