package com.fengjie.model.activity.inputWorkload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.fengjie.model.R;
import com.fengjie.model.activity.inputWorkload.presenter.InputWorkloadPresenter;
import com.fengjie.model.activity.inputWorkload.view.IInputWorkloadView;
import com.fengjie.model.userdefinedview.CommonDialog;
import com.fengjie.model.userdefinedview.Menu;
import com.fengjie.model.utils.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author Created by FengJie on 2016/11/11-10:12.
 * @brief input workload View.
 * @attention
 */

public class InputWorkload extends AppCompatActivity implements IInputWorkloadView
{
	/*** Widget */
	@BindView ( R.id.unitPrice_EditText_inputWorkload )
	protected EditText unitPrice_EditText_inputWorkload;

	@BindView ( R.id.weight_EditText_inputWorkload )
	protected EditText weight_EditText_inputWorkload;

	@BindView ( R.id.money_textView_inputWorkload )
	protected TextView money_textView_inputWorkload;
	/** userDefined View */
	@BindView ( R.id.menu_view_inputWorkload )
	protected Menu menu;
	@BindView ( R.id.searchName_view_inputWorkload )
	protected FloatingSearchView searchName_view_inputWorkload;
	@BindView ( R.id.searchTea_view_inputWorkload )
	protected FloatingSearchView searchTea_view_inputWorkload;
	private CommonDialog mCommonDialog;
	/** parameters */
	private final String TAG = "InputWorkload";
	private String mName = "", mTeaCategory = "";       //return to presenter parameter.
	/** Presenter */
	InputWorkloadPresenter mPresenter;

	@Override
	protected void onCreate ( @Nullable Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inputworkload);
		ButterKnife.bind(this);
		initMenu();
		initDialag();
		initFloatingSearchView();
		mPresenter = new InputWorkloadPresenter(this, this);
	}

	@Override
	protected void onDestroy ()
	{
		super.onDestroy();
		mPresenter.closePrinter();
	}

	@OnClick ( { R.id.change_button_inputWorkload, R.id.save_button_inputWorkload, R.id.print_button_inputWorkload, R.id.cancel_button_inputWorkload } )
	public void OnClickListerner ( View view )
	{
		switch ( view.getId() )
		{
			case R.id.change_button_inputWorkload:
				unitPrice_EditText_inputWorkload.setEnabled(unitPrice_EditText_inputWorkload.isEnabled() ? false : true);   //change unitPrice EditText's enable.
				break;
			case R.id.save_button_inputWorkload:
				mPresenter.saveTeaUnitPrice();
				break;
			case R.id.print_button_inputWorkload:
				mCommonDialog.setText(R.id.name_textView_dialog_inputWorkload,getName());
				mCommonDialog.setText(R.id.category_textView_dialog_inputWorkload,getCategory());
				mCommonDialog.setText(R.id.unitPrice_textView_dialog_inputWorkload,getUnitPrice()+"");
				mCommonDialog.setText(R.id.weight_textView_dialog_inputWorkload,getWeight()+"");
				mCommonDialog.setText(R.id.money_textView_dialog_inputWorkload,(getWeight()*getUnitPrice())+"");
				mCommonDialog.show();
				break;
			case R.id.cancel_button_inputWorkload:
				finish();
				break;
			default:
				return;
		}
	}

	//  @OnTextChanged(value = R.id.weight_EditText_inputWorkload, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
//	void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//	@OnTextChanged(value = R.id.weight_EditText_inputWorkload, callback = OnTextChanged.Callback.TEXT_CHANGED)
//	void onTextChanged(CharSequence s, int start, int before, int count) {}

	@OnTextChanged ( value = R.id.unitPrice_EditText_inputWorkload, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED )
	void afterTextChanged_unitPrice ( Editable s )
	{
		money_textView_inputWorkload.setText(String.valueOf(Float.parseFloat(s.toString().equals("") ? "0" : s.toString()) * getWeight()));
	}

	@OnTextChanged ( value = R.id.weight_EditText_inputWorkload, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED )
	void afterTextChanged_weight ( Editable s )
	{
		money_textView_inputWorkload.setText(String.valueOf(Float.parseFloat(s.toString().equals("") ? "0" : s.toString()) * getUnitPrice()));
	}

	/**
	 * init menu method.
	 */
	private void initMenu ()
	{
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
		mCommonDialog = new CommonDialog(this,R.layout.view_dialog_inputworkload);
		mCommonDialog.setButtonListener(R.id.submit_button_dialog_inputWorkload, new View.OnClickListener()
		{
			@Override
			public void onClick ( View v )
			{
				mPresenter.printAndSaveData();
				mCommonDialog.dismiss();
			}
		});
		mCommonDialog.setButtonListener(R.id.cancel_button_dialog_inputWorkload, new View.OnClickListener()
		{
			@Override
			public void onClick ( View v )
			{
				mCommonDialog.dismiss();
			}
		});

	}

	/**
	 * init floating search view
	 */
	private void initFloatingSearchView ()
	{
		/**text input Listener.*/
		searchName_view_inputWorkload.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener()
		{

			@Override
			public void onSearchTextChanged ( String oldQuery, final String newQuery )
			{
				mName = newQuery;
				if ( mPresenter.isExistName(mName) )
					searchName_view_inputWorkload.clearSearchFocus();
				else
					ShowSuggestion(Flag.SHOW_NAME_SUGGESTION);
			}
		});
		/**onClick Listener.**/
		searchName_view_inputWorkload.setOnSearchListener(new FloatingSearchView.OnSearchListener()
		{
			@Override
			public void onSuggestionClicked ( final SearchSuggestion searchSuggestion )
			{
				mName = searchSuggestion.getBody();
			}

			@Override
			public void onSearchAction ( String query )
			{
				Log.d(TAG, "onSearchAction() " + query);
			}
		});
		/**focus and leave Listener.**/
		searchName_view_inputWorkload.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener()
		{
			@Override
			public void onFocus ()
			{
				ShowSuggestion(Flag.SHOW_NAME_SUGGESTION);
				Log.d(TAG, "onFocus()");
			}
			
			@Override
			public void onFocusCleared ()
			{
			}
		});

		/*** Separate ***/

		/**text input Listener.*/
		searchTea_view_inputWorkload.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener()
		{
			
			@Override
			public void onSearchTextChanged ( String oldQuery, final String newQuery )
			{
				mTeaCategory = newQuery;
				if ( mPresenter.isExistTea(newQuery) >= 0.0f )
				{
					searchTea_view_inputWorkload.clearSearchFocus();        //clear focus status.
					unitPrice_EditText_inputWorkload.setText(mPresenter.isExistTea(mTeaCategory) + "");     //update unitPrice
				} else
					ShowSuggestion(Flag.SHOW_TEA_SUGGESTION);
			}
		});
		/**onClick Listener.**/
		searchTea_view_inputWorkload.setOnSearchListener(new FloatingSearchView.OnSearchListener()
		{
			@Override
			public void onSuggestionClicked ( final SearchSuggestion searchSuggestion )
			{
				mTeaCategory = searchSuggestion.getBody();
				unitPrice_EditText_inputWorkload.setText(mPresenter.isExistTea(mTeaCategory) + "");     //update unitPrice
				Log.d(TAG, "onSuggestionClicked()" + searchSuggestion.getBody());
			}
			
			@Override
			public void onSearchAction ( String query )
			{
				mTeaCategory = query;
				Log.d(TAG, "onSearchAction() " + query);
			}
		});
		/**focus and leave Listener.**/
		searchTea_view_inputWorkload.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener()
		{
			@Override
			public void onFocus ()
			{     //focus listener.
				ShowSuggestion(Flag.SHOW_TEA_SUGGESTION);
				Log.d(TAG, "onFocus()");
			}

			@Override
			public void onFocusCleared ()
			{
			}
		});
	}

	private void ShowSuggestion ( Flag flag )
	{

		switch ( flag )
		{
			case SHOW_NAME_SUGGESTION:
				searchName_view_inputWorkload.showProgress();
				searchName_view_inputWorkload.swapSuggestions(mPresenter.filterWorkers());
				searchName_view_inputWorkload.hideProgress();
				break;
			case SHOW_TEA_SUGGESTION:
				searchTea_view_inputWorkload.showProgress();
				searchTea_view_inputWorkload.swapSuggestions(mPresenter.filterTea());
				searchTea_view_inputWorkload.hideProgress();
				break;
			default:
				return;
		}

	}


	/** View - Interface */
	@Override
	public String getName ()
	{
		return mName;
	}

	@Override
	public String getCategory ()
	{
		return mTeaCategory;
	}

	public float getUnitPrice ()
	{
		return Float.parseFloat(unitPrice_EditText_inputWorkload.getText().toString().equals("")
				                        ? "0" : unitPrice_EditText_inputWorkload.getText().toString());
	}

	@Override
	public float getWeight ()
	{
		return Float.parseFloat(weight_EditText_inputWorkload.getText().toString().equals("") ? "0" : weight_EditText_inputWorkload.getText().toString());
	}

	@Override
	public void showOrHideWorkerInfoWindow ()
	{
//		mWorkerDropDownWindow.showOrHideWindow();
	}

	@Override
	public void showOrHideTeaInfoWindow ()
	{
//		mTeaDropDownWindow.showOrHideWindow();
	}

	@Override
	public void showOrHideDialog ()
	{
//		if ( mCustomDialog.isShowing() )
//		{
//			name_textView_dialog_inputWorkload.setText(getName());
//			category_textView_dialog_inputWorkload.setText(getCategory());
//			unitPrice_textView_dialog_inputWorkload.setText(getUnitPrice() + "");
//			weight_textView_dialog_inputWorkload.setText(getWeight() + "");
//			money_textView_dialog_inputWorkload.setText(getMoney() + "");
//			mCustomDialog.show();
//		} else
//			mCustomDialog.dismiss();
	}

	@Override
	public void showToastInView ( Flag flag )
	{
		switch ( flag )
		{
			/**printAndSaveData() method use*/
			case ADD_WORKLOAD_SUCCEED:
				MyToast.showShort(getApplicationContext(),"添加成功!");
				break;
			case ADD_WORKLOAD_FAILED:
				MyToast.showShort(getApplicationContext(),"添加失败!");
				break;
			case NAME_EXIST:
				MyToast.showShort(getApplicationContext(),"已搜寻到对应工人!");
				break;
			case WORKER_NOT_EXIST:
				MyToast.showShort(getApplicationContext(),"不存在该工人,请重新选择!");
				break;
			case CATEGORY_NOT_EXIST:
				MyToast.showShort(getApplicationContext(),"不存在该茶叶,请重新选择!");
				break;
			case CATEGORY_EXIST:
				MyToast.showShort(getApplicationContext(),"已搜寻到对应茶叶!");
				break;
			case UNIT_PRICE_CANNOT_ZERO:
				MyToast.showShort(getApplicationContext(),"单价不能为0!");
				break;
			case WEIGHT_CANNOT_ZERO:
				MyToast.showShort(getApplicationContext(),"重量不能为0!");
				break;
			case SAVE_UNIT_PRICE_SUCCEED:
				MyToast.showShort(getApplicationContext(),"茶叶价格更改成功!");
				break;
			case SAVE_UNIT_PRICE_FAILED:
				MyToast.showShort(getApplicationContext(),"您录入的信息有误,茶叶价格更改失败!");
				break;
			case PRINTER_NOT_EXIST:
				MyToast.showShort(getApplicationContext(),"未检测到打印设备!");
				break;
			case PRINTER_EXIST:
				MyToast.showShort(getApplicationContext(),"已经连接到打印设备!");
				break;

			default:
				break;
		}
	}


}
