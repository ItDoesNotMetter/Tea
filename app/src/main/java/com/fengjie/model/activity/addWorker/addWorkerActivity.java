package com.fengjie.model.activity.addWorker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fengjie.model.R;
import com.fengjie.model.activity.addWorker.presenter.AddWorkerPresenter;
import com.fengjie.model.activity.addWorker.view.IAddWorkerView;
import com.fengjie.model.dbhelper.Staff.StaffInfo;
import com.fengjie.model.userdefinedview.CustomDialog;
import com.fengjie.model.userdefinedview.Menu;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Created by FengJie on 2016/11/9-20:12.
 * @brief
 * @attention
 */

public class AddWorkerActivity extends AutoLayoutActivity implements IAddWorkerView
{
	/*** Widget */
	@BindView ( R.id.name_EditText_addWorker )
	protected EditText name_EditText_addWorker;
	@BindView ( R.id.phoneNumber_EditText_addWorker )
	protected EditText phoneNumber_EditText_addWorker;
	@BindView ( R.id.idNumber_EditText_addWorker )
	protected EditText idNumber_EditText_addWorker;
	@BindView ( R.id.man_radioButton_addWorker )
	protected RadioButton man_radioButton_addWorker;
	/** userDefined*View */
	private Menu menu;
	private CustomDialog mCustomDialog;
	private TextView name_dialog_addWorker, sex_dialog_addWorker, phoneNumber_dialog_addWorker, idNumber_dialog_addWorker;
	private Button submit_button_dialog_addWorker, cancel_button_dialog_addWorker;
	/** Presenter */
	private AddWorkerPresenter presenter;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addworker);
		ButterKnife.bind(this);
		initMenu();
		initDialag();
		presenter = new AddWorkerPresenter(AddWorkerActivity.this, this);
	}

	/**
	 * init menu method.
	 */
	private void initMenu ()
	{
		menu = ( Menu ) findViewById(R.id.menu_addWorker);
		menu.setBackgroundResource(R.color.color_addWorkerLayout);
		menu.setTitle_textView_menu("添加工人");
		menu.setRightButtonVisibility(false);   //Hide right button
//		menu.setBackOnClickListener(new ButtonListener());
	}

	/**
	 * init dialog.
	 **/
	private void initDialag ()
	{
		mCustomDialog = new CustomDialog(this);
		mCustomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);    //必须在加载前出示话没有标题
		View view = LayoutInflater.from(this).inflate(R.layout.view_dialog_addworker, null);
		mCustomDialog.setContentView(view);
		name_dialog_addWorker = ( TextView ) view.findViewById(R.id.name_textView_dialog_addWorker);
		sex_dialog_addWorker = ( TextView ) view.findViewById(R.id.sex_textView_dialog_addWorker);
		phoneNumber_dialog_addWorker = ( TextView ) view.findViewById(R.id.phoneNumber_textView_dialog_addWorker);
		idNumber_dialog_addWorker = ( TextView ) view.findViewById(R.id.idNumber_textView_dialog_addWorker);

		submit_button_dialog_addWorker = ( Button ) view.findViewById(R.id.submit_button_dialog_addWorker);
		cancel_button_dialog_addWorker = ( Button ) view.findViewById(R.id.cancel_button_dialog_addWorker);

		submit_button_dialog_addWorker.setOnClickListener(new ButtonListener());
		cancel_button_dialog_addWorker.setOnClickListener(new ButtonListener());
	}


	@OnClick ( { R.id.submit_button_addWorker, R.id.cancel_button_addWorker, R.id.back_button_menu } )
	protected void OnClick ( View view )
	{
		switch ( view.getId() )
		{
			case R.id.submit_button_addWorker:
				presenter.CheckDataAndShowDialog();
				break;
			case R.id.cancel_button_addWorker:
				presenter.exit();
				break;
			case R.id.back_button_menu:
				presenter.exit();
				break;
			default:
				break;
		}
	}

	private class ButtonListener implements View.OnClickListener
	{

		@Override
		public void onClick ( View v )
		{
			switch ( v.getId() )
			{
				case R.id.submit_button_dialog_addWorker:
					presenter.addWorker(new StaffInfo(getName(), getSex(), getPhoneNumber(), getIdNumber()));
					break;
				case R.id.cancel_button_dialog_addWorker:
					cloneCustomDialog();
					break;
				default:
					break;
			}
		}
	}

	@Override
	public String getName ()
	{
		return name_EditText_addWorker.getText().toString();
	}

	@Override
	public String getPhoneNumber ()
	{
		return phoneNumber_EditText_addWorker.getText().toString();
	}

	@Override
	public String getIdNumber ()
	{
		return idNumber_EditText_addWorker.getText().toString();
	}

	@Override
	public String getSex ()
	{
		return man_radioButton_addWorker.isChecked() ? "男" : "女";
	}

	@Override
	public void showCustomDialog ( boolean flag )
	{
		if ( flag )
		{
			name_dialog_addWorker.setText(getName());
			sex_dialog_addWorker.setText(getSex());
			phoneNumber_dialog_addWorker.setText(getPhoneNumber());
			idNumber_dialog_addWorker.setText(getIdNumber());
			mCustomDialog.show();
		}

	}

	@Override
	public void cloneCustomDialog ()
	{
		mCustomDialog.dismiss();
	}

	@Override
	public void promptExit ()
	{
		if ( ! name_EditText_addWorker.getText().toString().equals("") || ! phoneNumber_EditText_addWorker.getText().toString().equals("")
				     || ! idNumber_EditText_addWorker.getText().toString().equals("") )
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(" 提示");
			builder.setMessage(" 您有信息尚未保存是否退出？");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick ( DialogInterface dialog, int which )
				{
					finish();
				}
			});
			builder.setNeutralButton("取消", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick ( DialogInterface dialog, int which )
				{
					dialog.dismiss();
				}
			});
			AlertDialog Dialog = builder.create();
			Dialog.setCancelable(false);        //设置外部点击失效
			Dialog.show();
		} else
		{
			finish();
		}

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
	public void clearAllEditText ()
	{
		name_EditText_addWorker.setText("");
		phoneNumber_EditText_addWorker.setText("");
		idNumber_EditText_addWorker.setText("");
	}

	@Override
	public void showNameIsNull ()
	{
		Toast.makeText(getApplicationContext(), "姓名已存在，录入失败!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void showNameExist ()
	{
		Toast.makeText(getApplicationContext(), "姓名已存在，录入失败!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void showPhoneNumberError ()
	{
		Toast.makeText(getApplicationContext(), "手机号码填写错误,录入失败", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void showIdNumberError ()
	{
		Toast.makeText(getApplicationContext(), "身份证号码填写错误,录入失败", Toast.LENGTH_SHORT).show();
	}


}
