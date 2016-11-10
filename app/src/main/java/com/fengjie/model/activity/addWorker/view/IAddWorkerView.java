package com.fengjie.model.activity.addWorker.view;

/**
 * @author Created by FengJie on 2016/11/9-20:59.
 * @brief
 * @attention
 */

public interface IAddWorkerView
{
	public String getName ();

	public String getPhoneNumber ();

	public String getIdNumber ();

	public String getSex ();

	public void showCustomDialog ( boolean flag );

	public void cloneCustomDialog ();

	public void promptExit ();

	public void showAddSucceed ();

	public void showAddFailed ();

	public void clearAllEditText ();

	public void showNameIsNull ();

	public void showNameExist ();

	public void showPhoneNumberError ();

	public void showIdNumberError ();

}
