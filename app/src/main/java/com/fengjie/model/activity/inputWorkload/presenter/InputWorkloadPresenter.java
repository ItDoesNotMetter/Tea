package com.fengjie.model.activity.inputWorkload.presenter;

import android.content.Context;

import com.fengjie.model.activity.inputWorkload.Flag_Toast;
import com.fengjie.model.activity.inputWorkload.model.InputWorkloadBiz;
import com.fengjie.model.activity.inputWorkload.view.IInputWorkloadView;
import com.fengjie.model.helper.suggestion.Suggestion;
import com.fengjie.model.helper.suggestion.TeaSuggestion;

import java.util.List;

/**
 * @author Created by FengJie on 2016/11/12-10:29.
 * @brief
 * @attention
 */

public class InputWorkloadPresenter
{
	private InputWorkloadBiz mBiz;
	private IInputWorkloadView mView;
	private Context mContext;

	public InputWorkloadPresenter ( Context context, IInputWorkloadView view )
	{
		mContext = context;
		mView = view;
		mBiz = new InputWorkloadBiz(mContext);
	}

	public List< Suggestion > filterWorkers ()
	{
		return mBiz.filterWorker(mView.getName());
	}

	public List< TeaSuggestion > filterTea ()
	{
		return mBiz.filterTea(mView.getCategory());
	}


	public void saveTeaUnitPrice ()
	{
		try
		{
			mView.showToastInView(  //This method receive flag that will be show Toast.
					mBiz.updateTeaUnitPrice(mView.getCategory(), mView.getUnitPrice())      //Update succeed will return true,otherwise.
							? Flag_Toast.SAVE_UNIT_PRICE_SUCCEED : Flag_Toast.SAVE_UNIT_PRICE_FAILED);
//		} catch( IllegalArgumentException exception )
		} catch( Exception exception )
		{
			mView.showToastInView(Flag_Toast.SAVE_UNIT_PRICE_FAILED);
			exception.printStackTrace();        //Print error.
		}
	}

	public void printAndSaveData ()
	{
		mView.showToastInView(
				mBiz.printAndSaveData(mView.getName(), mView.getCategory(), mView.getUnitPrice(), mView.getWeight())
		);
	}

	public void exit ()
	{

	}

}
