package com.fengjie.model.activity.inputWorkload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fengjie.model.R;
import com.fengjie.model.activity.inputWorkload.precenter.InputWorkloadPrensenter;
import com.fengjie.model.activity.inputWorkload.view.IInputWorkloadView;
import com.fengjie.model.dbhelper.Staff.StaffInfo;
import com.fengjie.model.dbhelper.Tea.TeaInfo;
import com.fengjie.model.userdefinedview.CustomDialog;
import com.fengjie.model.userdefinedview.DropDownWindow;
import com.fengjie.model.userdefinedview.Menu;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Created by FengJie on 2016/11/11-10:12.
 * @brief
 * @attention
 */

public class InputWorkload extends AutoLayoutActivity implements IInputWorkloadView
{
	/*** Widget */
	@BindView ( R.id.name_EditText_inputWorkload )
	protected EditText name_EditText_inputWorkload;
	@BindView ( R.id.category_EditText_inputWorkload )
	protected EditText category_EditText_inputWorkload;
	@BindView ( R.id.unitPrice_EditText_inputWorkload )
	protected EditText unitPrice_EditText_inputWorkload;
	@BindView ( R.id.weight_EditText_inputWorkload )
	protected EditText weight_EditText_inputWorkload;
	@BindView ( R.id.money_EditText_inputWorkload )
	protected EditText money_EditText_inputWorkload;

	private TextView name_textView_dialog_inputWorkload, category_textView_dialog_inputWorkload, unitPrice_textView_dialog_inputWorkload,
			weight_textView_dialog_inputWorkload, money_textView_dialog_inputWorkload;
	private Button submit_button_dialog_inputWorkload, cancel_button_dialog_inputWorkload;
	/** userDefined*View */
	@BindView ( R.id.menu_view_inputWorkload )
	protected Menu menu;
	private CustomDialog mCustomDialog;
	DropDownWindow< StaffInfo > mWorkerDropDownWindow ;
	DropDownWindow< TeaInfo > mTeaDropDownWindow;
	/** parameter */

	/** Presenter */
	InputWorkloadPrensenter mPrensenter;
	@Override
	protected void onCreate ( @Nullable Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inputworkload);
		ButterKnife.bind(this);
		initMenu();
		initDialag();
//		initDropDownWindow();
		mPrensenter = new InputWorkloadPrensenter(this,this);
	}
	

	@Override
	protected void onDestroy ()
	{
		super.onDestroy();
	}

	/**
	 * init menu method.
	 */
	private void initMenu ()
	{
		menu = ( Menu ) findViewById(R.id.menu_view_inputWorkload);
		menu.setBackgroundResource(R.color.color_inputWorkloadLayout);
		menu.setTitle_textView_menu("录入采茶信息");
		menu.setRightButtonVisibility(Menu.HIDE);   //Hide right button
//		menu.setBackOnClickListener(new ButtonListener());
	}

	/**
	 * init dialog.
	 **/
	private void initDialag ()
	{
		mCustomDialog = new CustomDialog(this);
		mCustomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);    //必须在加载前出示话没有标题
		View view = LayoutInflater.from(this).inflate(R.layout.view_dialog_inputworkload, null);
		mCustomDialog.setContentView(view);
		name_textView_dialog_inputWorkload = ( TextView ) view.findViewById(R.id.name_textView_dialog_inputWorkload);
		category_textView_dialog_inputWorkload = ( TextView ) view.findViewById(R.id.category_textView_dialog_inputWorkload);
		unitPrice_textView_dialog_inputWorkload = ( TextView ) view.findViewById(R.id.unitPrice_textView_dialog_inputWorkload);
		weight_textView_dialog_inputWorkload = ( TextView ) view.findViewById(R.id.weight_textView_dialog_inputWorkload);
		money_textView_dialog_inputWorkload = ( TextView ) view.findViewById(R.id.money_textView_dialog_inputWorkload);

		submit_button_dialog_inputWorkload = ( Button ) view.findViewById(R.id.submit_button_dialog_inputWorkload);
		cancel_button_dialog_inputWorkload = ( Button ) view.findViewById(R.id.cancel_button_dialog_inputWorkload);
	}

	/**
	 * init drop down window
	 */
//	private void initDropDownWindow ()
//	{
////		List<String> list = new ArrayList<String>(Arrays.asList("fengJie","fengJie","fengJie","fengJie"));
//		mWorkerDropDownWindow = new DropDownWindow< StaffInfo >(this,name_EditText_inputWorkload, getWindowManager().getDefaultDisplay().getWidth(),
//				                                                    getWindowManager().getDefaultDisplay().getHeight(),new ArrayList<StaffInfo>());
////		mTeaDropDownWindow = new DropDownWindow< String >(this,category_EditText_inputWorkload, getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight());
////		mWorkerDropDownWindow.updateListView(list);
//	name_EditText_inputWorkload.addTextChangedListener(new TextWatcher()
//	{
//		@Override
//		public void beforeTextChanged ( CharSequence s, int start, int count, int after )
//		{
//
//		}
//
//		@Override
//		public void onTextChanged ( CharSequence s, int start, int before, int count )
//		{
//
//		}
//
//		@Override
//		public void afterTextChanged ( Editable s )
//		{
//			mPrensenter.showWorkers(s.toString());
//		}
//	});
//	}


	@OnClick ( { R.id.name_imageView_inputWorkload } )
	public void OnClickListerner ( View view )
	{
		switch ( view.getId() )
		{
			case R.id.name_imageView_inputWorkload:
				showOrHideWorkerInfoWindow();
				break;

			default:
				return;
		}
	}


	@Override
	public String getName ()
	{
		return name_EditText_inputWorkload.getText().toString();
	}

	@Override
	public String getCategory ()
	{
		return category_EditText_inputWorkload.getText().toString();
	}

	@Override
	public float getUnitPrice ()
	{
		return Float.parseFloat(unitPrice_EditText_inputWorkload.getText().toString());
	}

	@Override
	public float getWeight ()
	{
		return Float.parseFloat(weight_EditText_inputWorkload.getText().toString());
	}

	@Override
	public float getMoney ()
	{
		return Float.parseFloat(money_EditText_inputWorkload.getText().toString());
	}

	@Override
	public void showOrHideWorkerInfoWindow ()
	{
		mWorkerDropDownWindow.showOrHideWindow();
	}

	@Override
	public void showOrHideTeaInfoWindow ()
	{
		mTeaDropDownWindow.showOrHideWindow();
	}

	@Override
	public void showOrHideDialog ()
	{
		if ( mCustomDialog.isShowing() )
		{
			name_textView_dialog_inputWorkload.setText(getName());
			category_textView_dialog_inputWorkload.setText(getCategory());
			unitPrice_textView_dialog_inputWorkload.setText(getUnitPrice()+"");
			weight_textView_dialog_inputWorkload.setText(getWeight()+"");
			money_textView_dialog_inputWorkload.setText(getMoney()+"");
			mCustomDialog.show();
		}
		else
			mCustomDialog.dismiss();
	}

	@Override
	public void showAddSucceed ()
	{
		Toast.makeText(getApplicationContext(), "添加成功!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void showAddFailed ()
	{
		Toast.makeText(getApplicationContext(), "添加失败!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void showWorkerInfoWindow ( List< StaffInfo > staffInfoList )
	{
		mWorkerDropDownWindow.updateListView(staffInfoList);
		mWorkerDropDownWindow.show();
	}


}
