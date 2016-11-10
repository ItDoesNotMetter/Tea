package com.fengjie.model.activity.addWorker.model;

/**
 * @author Created by FengJie on 2016/11/10-12:36.
 * @brief
 * @attention
 */

public interface IIsCheckBeforeToView
{
	public void nameIsNull ();

	public void nameExist ();

	public void phoneNumberError ();

	public void idNumberError ();

}
