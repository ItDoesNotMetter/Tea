package com.fengjie.model.activity.addWorker.model;

/**
 * @author Created by FengJie on 2016/11/9-21:04.
 * @brief   Only have two biz.
 * @attention
 */

public interface IAddWorkerToView
{
	public void addSucceeed ();        //add succeed

	public void addFailed ();   //add failed

	public void clearAllEditText();
}
