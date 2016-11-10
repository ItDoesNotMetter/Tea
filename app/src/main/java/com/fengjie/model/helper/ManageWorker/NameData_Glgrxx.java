package com.fengjie.model.helper.ManageWorker;


import com.fengjie.model.baseClass.SortNameData;

public class NameData_Glgrxx extends SortNameData
{

	private String name;   		//名字
	private int uid;			//管理工人界面处需要用到
	private float money;

	public NameData_Glgrxx(){

	}

	public NameData_Glgrxx(String name){
		if(!(name==null))
			this.name=name;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	

}
