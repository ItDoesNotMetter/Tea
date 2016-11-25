package com.fengjie.model.dbhelper.PickTea;

/**
 * Created by xiong on 2016/4/15. 采茶信息表
 */
public class PickTeaInfo
{
	private int uid;
	private int tid;
	private String pick_datetime;
	private String checkout_date;
	private float pick_weight;
	private float pick_income;
	private int pick_isCheckout;
	private String serial_number;
	private float pick_price;

	public PickTeaInfo ()
	{

	}

	public PickTeaInfo ( int uid, int tid, String pick_datetime, float pick_weight, float pick_income, String serial_number, float pick_price )
	{
		this.uid = uid;
		this.tid = tid;
		this.pick_datetime = pick_datetime;
		this.pick_weight = pick_weight;
		this.pick_income = pick_income;
		this.serial_number = serial_number;
		this.pick_price = pick_price;
	}

	public int getUid ()
	{
		return uid;
	}

	public void setUid ( int uid )
	{
		this.uid = uid;
	}

	public int getTid ()
	{
		return tid;
	}

	public void setTid ( int tid )
	{
		this.tid = tid;
	}

	public String getPick_datetime ()
	{
		return pick_datetime;
	}

	public void setPick_datetime ( String pick_datetime )
	{
		this.pick_datetime = pick_datetime;
	}

	public String getCheckout_date ()
	{
		return checkout_date;
	}

	public void setCheckout_date ( String checkout_date )
	{
		this.checkout_date = checkout_date;
	}

	public float getPick_weight ()
	{
		return pick_weight;
	}

	public void setPick_weight ( float pick_weight )
	{
		this.pick_weight = pick_weight;
	}

	public float getPick_income ()
	{
		return pick_income;
	}

	public void setPick_income ( float pick_income )
	{
		this.pick_income = pick_income;
	}

	public int getPick_isCheckout ()
	{
		return pick_isCheckout;
	}

	public void setPick_isCheckout ( int pick_isCheckout )
	{
		this.pick_isCheckout = pick_isCheckout;
	}

	public String getSerial_number ()
	{
		return serial_number;
	}

	public void setSerial_number ( String serial_number )
	{
		this.serial_number = serial_number;
	}

	public float getPick_price ()
	{
		return pick_price;
	}

	public void setPick_price ( float pick_price )
	{
		this.pick_price = pick_price;
	}
}
