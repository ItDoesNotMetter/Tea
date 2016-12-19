package com.fengjie.model.activity.inputWorkload;

/**
 * @author Created by FengJie on 2016/11/18-9:18.
 * @brief
 * @attention
 */

public enum Flag
{
	/** printAndSaveData() method use.*/
	ADD_WORKLOAD_SUCCEED, ADD_WORKLOAD_FAILED,
	WORKER_NOT_EXIST, CATEGORY_NOT_EXIST, UNIT_PRICE_CANNOT_ZERO, WEIGHT_CANNOT_ZERO,
	PRINT_FIALD,PRINT_SUCCEED,

	/** update tea unit-price.*/
	SAVE_UNIT_PRICE_SUCCEED, SAVE_UNIT_PRICE_FAILED,

	/** search bar checkout tea or name exist*/
	CATEGORY_EXIST,NAME_EXIST,

	/** checkout printer exist.*/
	PRINTER_NOT_EXIST,PRINTER_EXIST,

	/** show suggestion */
	SHOW_NAME_SUGGESTION,SHOW_TEA_SUGGESTION

}
