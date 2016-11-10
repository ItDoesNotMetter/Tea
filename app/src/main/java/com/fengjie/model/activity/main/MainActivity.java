package com.fengjie.model.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.fengjie.model.R;
import com.fengjie.model.activity.addTea.AddTeaActivity;
import com.fengjie.model.activity.addWorker.AddWorkerActivity;
import com.fengjie.model.userdefinedview.Menu;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AutoLayoutActivity
{
	/** 注释框架 **/
	@BindView ( R.id.menu_main )
	protected Menu menu_main;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);             //bind this activity.
		initMenu();                         //init top menu.
	}

	private void initMenu ()
	{
		List< String > groups = new LinkedList< String >(Arrays.asList("TestTest1", "TestTest2"));
		menu_main.setBackgroundResource(R.color.forestgreen);
		menu_main.setTitle_textView_menu("采茶助手");
		menu_main.init("采茶助手", groups, new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick ( AdapterView< ? > parent, View view, int position, long id )
			{

			}
		});

//        mMenuMain.setLeftButtonBackground(R.mipmap.barcode_scan);

	}

	@OnClick ( { R.id.addWorker_button_main, R.id.addTea_button_main } )
	protected void OnClick ( View view )
	{
		Intent intent = new Intent();
		switch ( view.getId() )
		{
			case R.id.addWorker_button_main:
				intent.setClass(MainActivity.this, AddWorkerActivity.class);
				break;
			case R.id.addTea_button_main:
				intent.setClass(MainActivity.this, AddTeaActivity.class);
				break;
			default:
				return; //other situation exit this method , not intent.
		}
		startActivity(intent);
	}

//    @OnClick({R.id.AA,R.id.BB})
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id:
//                break;
//            default:break;
//        }
//    }

}
