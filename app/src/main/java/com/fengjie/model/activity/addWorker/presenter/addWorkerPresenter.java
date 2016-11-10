package com.fengjie.model.activity.addWorker.presenter;

import android.content.Context;

import com.fengjie.model.activity.addWorker.model.AddWorkerBiz;
import com.fengjie.model.activity.addWorker.model.IAddWorkerBiz;
import com.fengjie.model.activity.addWorker.model.IAddWorkerToView;
import com.fengjie.model.activity.addWorker.model.IIsCheckBeforeToView;
import com.fengjie.model.activity.addWorker.view.IAddWorkerView;
import com.fengjie.model.dbhelper.Staff.StaffInfo;

/**
 * @author Created by FengJie on 2016/11/10-10:28.
 * @brief addWorker();
 * @attention
 */

public class AddWorkerPresenter implements IAddWorkerPresenter
{
	/** Model and view interface */
	private IAddWorkerView mView;
	private IAddWorkerBiz mBiz;

	/**
	 * Constructor realise interface.
	 * @param context
	 * @param mView
	 */
	public AddWorkerPresenter ( Context context, IAddWorkerView mView )
	{
		this.mView = mView;
		mBiz = new AddWorkerBiz(context);
	}


	@Override
	public void CheckDataAndShowDialog ()
	{
		StaffInfo staffInfo = new StaffInfo(mView.getName(), mView.getPhoneNumber(), mView.getIdNumber());
		mView.showCustomDialog(mBiz.isCheckBefore(staffInfo, new IIsCheckBeforeToView()
		{
			@Override
			public void nameIsNull ()
			{
				mView.showNameIsNull();
			}

			@Override
			public void nameExist ()
			{
				mView.showNameExist();
			}

			@Override
			public void phoneNumberError ()
			{
				mView.showPhoneNumberError();
			}

			@Override
			public void idNumberError ()
			{
				mView.showIdNumberError();
			}
		}));
	}

	@Override
	public void addWorker ( StaffInfo staffInfo )
	{
		mBiz.addWorker(staffInfo, new IAddWorkerToView()
		{
			@Override
			public void addSucceeed ()
			{
				mView.showAddSucceed();
				mView.cloneCustomDialog();
			}

			@Override
			public void addFailed ()
			{
				mView.showAddFailed();
				mView.cloneCustomDialog();
			}

			@Override
			public void clearAllEditText ()
			{
				mView.clearAllEditText();
			}
		});
	}


	@Override
	public void exit ()
	{
		mView.promptExit();
	}

}
