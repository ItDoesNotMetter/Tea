package com.fengjie.model.activity.inputWorkload.presenter;

import android.content.Context;

import com.fengjie.model.activity.inputWorkload.Flag;
import com.fengjie.model.activity.inputWorkload.model.InputWorkloadBiz;
import com.fengjie.model.activity.inputWorkload.view.IInputWorkloadView;
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
	/** parameters */
	private Context mContext;


	public InputWorkloadPresenter ( Context context, IInputWorkloadView view )
	{
		mContext = context;
		mView = view;
		mBiz = new InputWorkloadBiz(mContext);
	}

	public List< Suggestion > filterWorkers ()
	{
		return mBiz.getFilterWorker(mView.getName());
	}

	public List< Suggestion > filterTea ()
	{
		return mBiz.getFilterTea(mView.getCategory());
	}


	public void saveTeaUnitPrice ()
	{
		try
		{
			mView.showToastInView(  //This method receive flag that will be show Toast.
					mBiz.updateTeaUnitPrice(mView.getCategory(), mView.getUnitPrice())      //Update succeed will return true,otherwise.
							? Flag.SAVE_UNIT_PRICE_SUCCEED : Flag.SAVE_UNIT_PRICE_FAILED);
			mBiz.updateTeaUnitPrice();
		} catch( Exception exception )
		{
			mView.showToastInView(Flag.SAVE_UNIT_PRICE_FAILED);
			exception.printStackTrace();        //Print error.
		}
	}

	/**
	 * print image and save data.
	 */
	public void printAndSaveData ()
	{

		mView.showToastInView(
				mBiz.printAndSaveData(mView.getName(), mView.getCategory(), mView.getUnitPrice(), mView.getWeight(),mView.getBitMapDialog())  //this method will return FLAG.
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
	 * Printer get stance.
	 */
	public void openPrinter()
	{
		mView.showToastInView(
				mBiz.openPrinter()
		);
	}

	public void closePrinter()
	{
		mBiz.closePrinter();
	}

	public int getWorkerSize()
	{
		return mBiz.getWorkerSize();
	}

	public int getTeaSize()
	{
		return mBiz.getTeaSize();
	}

}
