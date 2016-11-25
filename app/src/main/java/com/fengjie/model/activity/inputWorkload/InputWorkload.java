package com.fengjie.model.activity.inputWorkload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.fengjie.model.R;
import com.fengjie.model.activity.inputWorkload.presenter.InputWorkloadPresenter;
import com.fengjie.model.activity.inputWorkload.view.IInputWorkloadView;
import com.fengjie.model.helper.suggestion.Suggestion;
import com.fengjie.model.helper.suggestion.TeaSuggestion;
import com.fengjie.model.userdefinedview.Menu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author Created by FengJie on 2016/11/11-10:12.
 * @brief
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

//	private TextView name_textView_dialog_inputWorkload, category_textView_dialog_inputWorkload, unitPrice_textView_dialog_inputWorkload,
//			weight_textView_dialog_inputWorkload, money_textView_dialog_inputWorkload;
//	private Button submit_button_dialog_inputWorkload, cancel_button_dialog_inputWorkload;

	/** userDefined View */
	@BindView ( R.id.menu_view_inputWorkload )
	protected Menu menu;
	@BindView ( R.id.searchName_view_inputWorkload )
	protected FloatingSearchView searchName_view_inputWorkload;
	@BindView ( R.id.searchTea_view_inputWorkload )
	protected FloatingSearchView searchTea_view_inputWorkload;
//	private CustomDialog mCustomDialog;
	/** parameters */
	private final String TAG = "InputWorkload";
	private String mName = "", mTeaCategory = "";
	private Suggestion mNameSuggestion = new Suggestion("", 0);
	private TeaSuggestion mTeaSuggestion = new TeaSuggestion("", 0, 0f);
	/** Presenter */
	InputWorkloadPresenter mPresenter;

	@Override
	protected void onCreate ( @Nullable Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inputworkload);
		ButterKnife.bind(this);
		initMenu();
//		initDialag();
		initFloatingSearchView();
		mPresenter = new InputWorkloadPresenter(this, this);
	}

	@Override
	protected void onDestroy ()
	{
		super.onDestroy();
	}

	@OnClick ( { R.id.change_button_inputWorkload, R.id.save_button_inputWorkload ,R.id.print_button_inputWorkload ,R.id.cancel_button_inputWorkload} )
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
				mPresenter.printAndSaveData();
				break;
//			case R.id.cancel_button_inputWorkload:
//				break;
			default:
				return;
		}
	}

	//  @OnTextChanged(value = R.id.weight_EditText_inputWorkload, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
//	void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//	@OnTextChanged(value = R.id.weight_EditText_inputWorkload, callback = OnTextChanged.Callback.TEXT_CHANGED)
//	void onTextChanged(CharSequence s, int start, int before, int count) {}
	@OnTextChanged ( value = R.id.weight_EditText_inputWorkload, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED )
	void afterTextChanged ( Editable s )
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
//	private void initDialag ()
//	{
//		mCustomDialog = new CustomDialog(this);
//		mCustomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);    //必须在加载前出示话没有标题
//		View view = LayoutInflater.from(this).inflate(R.layout.view_dialog_inputworkload, null);
//		mCustomDialog.setContentView(view);
//		name_textView_dialog_inputWorkload = ( TextView ) view.findViewById(name_textView_dialog_inputWorkload);
//		category_textView_dialog_inputWorkload = ( TextView ) view.findViewById(category_textView_dialog_inputWorkload);
//		unitPrice_textView_dialog_inputWorkload = ( TextView ) view.findViewById(unitPrice_textView_dialog_inputWorkload);
//		weight_textView_dialog_inputWorkload = ( TextView ) view.findViewById(weight_textView_dialog_inputWorkload);
//		money_textView_dialog_inputWorkload = ( TextView ) view.findViewById(money_textView_dialog_inputWorkload);
//
//		submit_button_dialog_inputWorkload = ( Button ) view.findViewById(submit_button_dialog_inputWorkload);
//		cancel_button_dialog_inputWorkload = ( Button ) view.findViewById(cancel_button_dialog_inputWorkload);
//	}

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
				if ( ! oldQuery.equals("") && newQuery.equals("") )
				{
//					searchName_view_inputWorkload.clearSuggestions();
					searchName_view_inputWorkload.showProgress();
					searchName_view_inputWorkload.swapSuggestions(mPresenter.filterWorkers());
					searchName_view_inputWorkload.hideProgress();
				} else
				{                                /**文本不为空时监听**/
					//this shows the top left circular progress
					//you can call it where ever you want, but
					//it makes sense to do it when loading something in
					//the background.
					searchName_view_inputWorkload.showProgress();
					searchName_view_inputWorkload.swapSuggestions(mPresenter.filterWorkers());
					searchName_view_inputWorkload.hideProgress();

					//simulates a query call to a data source
					//with a new query.
				}

				Log.d(TAG, "onSearchTextChanged()" + " oldQuery:" + oldQuery + " newQuery:" + newQuery);
			}
		});
		/**onClick Listener.**/
		searchName_view_inputWorkload.setOnSearchListener(new FloatingSearchView.OnSearchListener()
		{
			@Override
			public void onSuggestionClicked ( final SearchSuggestion searchSuggestion )
			{
				mNameSuggestion = ( Suggestion ) searchSuggestion;
				mName = searchSuggestion.getBody();
			}

			@Override
			public void onSearchAction ( String query )
			{
//			mLastQuery = query;
				Log.d(TAG, "onSearchAction() " + query);
			}
		});
		/**focus and leave Listener.**/
		searchName_view_inputWorkload.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener()
		{
			@Override
			public void onFocus ()
			{
				searchName_view_inputWorkload.showProgress();
				searchName_view_inputWorkload.swapSuggestions(mPresenter.filterWorkers());
				searchName_view_inputWorkload.hideProgress();
				Log.d(TAG, "onFocus()");
			}
			
			@Override
			public void onFocusCleared ()
			{
				Log.d(TAG, "onFocusCleared() mTeaSuggestion.getBody() " + mTeaSuggestion.getBody() + " " + mTeaSuggestion.getId());
			}
		});

		/*******/

		/**text input Listener.*/
		searchTea_view_inputWorkload.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener()
		{
			
			@Override
			public void onSearchTextChanged ( String oldQuery, final String newQuery )
			{
				mTeaCategory = newQuery;
				if ( ! oldQuery.equals("") && newQuery.equals("") )
				{
					searchTea_view_inputWorkload.showProgress();
					searchTea_view_inputWorkload.swapSuggestions(mPresenter.filterTea());
					searchTea_view_inputWorkload.hideProgress();
				} else
				{                                /**文本不为空时监听**/
					searchTea_view_inputWorkload.showProgress();
					searchTea_view_inputWorkload.swapSuggestions(mPresenter.filterTea());
					searchTea_view_inputWorkload.hideProgress();
				}
			}
		});
		/**onClick Listener.**/
		searchTea_view_inputWorkload.setOnSearchListener(new FloatingSearchView.OnSearchListener()
		{
			@Override
			public void onSuggestionClicked ( final SearchSuggestion searchSuggestion )
			{
				mTeaSuggestion = ( TeaSuggestion ) searchSuggestion;
				mTeaCategory = searchSuggestion.getBody();
				unitPrice_EditText_inputWorkload.setText(mTeaSuggestion.getUnitPrice() + "");     //update unitPrice
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

				//			show suggestions when search bar gains focus (typically hist ory suggestions)
//				searchTea_view_inputWorkload.swapSuggestions(DataHelper.getHistory(getActivity(), 3));

				searchTea_view_inputWorkload.showProgress();
				searchTea_view_inputWorkload.swapSuggestions(mPresenter.filterTea());
				searchTea_view_inputWorkload.hideProgress();

				Log.d(TAG, "onFocus()");
			}

			@Override
			public void onFocusCleared ()
			{

				//set the title of the bar so that when focus is returned a new query begins.
//				searchTea_view_inputWorkload.setSearchBarTitle(mTeaCategory);

				//you can also set setSearchText(...) to make keep the query there when not focused and when focus returns.
//				searchTea_view_inputWorkload.setSearchText(mTeaSuggestion.getBody());

				Log.d(TAG, "onFocusCleared() mTeaSuggestion.getBody() " + mTeaSuggestion.getBody() + " " + mTeaSuggestion.getId());
			}
		});
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
		return Float.parseFloat(weight_EditText_inputWorkload.getText().toString().equals("")?"0":weight_EditText_inputWorkload.getText().toString());
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
	public void showToastInView ( Flag_Toast flag )
	{
		switch ( flag )
		{
			/**printAndSaveData() method use*/
			case ADD_WORKLOAD_SUCCEED:
				Toast.makeText(getApplicationContext(), "添加成功!", Toast.LENGTH_SHORT).show();
				break;
			case ADD_WORKLOAD_FAILED:
				Toast.makeText(getApplicationContext(), "添加失败!", Toast.LENGTH_SHORT).show();
				break;
			case WORKER_NOT_EXIST:
				Toast.makeText(getApplicationContext(), "不存在该工人,请重新选择!", Toast.LENGTH_SHORT).show();
				break;
			case TEA_NOT_EXIST:
				Toast.makeText(getApplicationContext(), "不存在该茶叶!", Toast.LENGTH_SHORT).show();
				break;
			case UNIT_PRICE_CANNOT_ZERO:
				Toast.makeText(getApplicationContext(), "单价不能为0!", Toast.LENGTH_SHORT).show();
				break;
			case WEIGHT_CANNOT_ZERO:
				Toast.makeText(getApplicationContext(), "重量不能为0!", Toast.LENGTH_SHORT).show();
				break;
			case SAVE_UNIT_PRICE_SUCCEED:
				Toast.makeText(getApplicationContext(), "茶叶价格更改成功!", Toast.LENGTH_SHORT).show();
				break;
			case SAVE_UNIT_PRICE_FAILED:
				Toast.makeText(getApplicationContext(), "您录入的信息有误,茶叶价格更改失败!", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
		}
	}


}
