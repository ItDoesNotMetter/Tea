package com.fengjie.model.activity.inputWorkload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.fengjie.model.R;
import com.fengjie.model.activity.inputWorkload.view.IInputWorkloadView;
import com.fengjie.model.dbhelper.Staff.StaffDBUtil;
import com.fengjie.model.dbhelper.Staff.StaffInfo;
import com.fengjie.model.helper.suggestion.Suggestion;
import com.fengjie.model.userdefinedview.Menu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Created by FengJie on 2016/11/11-10:12.
 * @brief
 * @attention
 */

public class InputWorkload extends AppCompatActivity implements IInputWorkloadView
{
	/*** Widget */
//	@BindView ( R.id.name_EditText_inputWorkload )
//	protected EditText name_EditText_inputWorkload;
//	@BindView ( R.id.category_EditText_inputWorkload )
//	protected EditText category_EditText_inputWorkload;
//	@BindView ( R.id.unitPrice_EditText_inputWorkload )
//	protected EditText unitPrice_EditText_inputWorkload;
//	@BindView ( R.id.weight_EditText_inputWorkload )
//	protected EditText weight_EditText_inputWorkload;
//	@BindView ( R.id.money_EditText_inputWorkload )
//	protected EditText money_EditText_inputWorkload;

//	private TextView name_textView_dialog_inputWorkload, category_textView_dialog_inputWorkload, unitPrice_textView_dialog_inputWorkload,
//			weight_textView_dialog_inputWorkload, money_textView_dialog_inputWorkload;
//	private Button submit_button_dialog_inputWorkload, cancel_button_dialog_inputWorkload;
//	/** userDefined*View */
	@BindView ( R.id.menu_view_inputWorkload )
	protected Menu menu;
	@BindView ( R.id.searchName_view_inputWorkload )
	protected FloatingSearchView mSearchView;
//	private CustomDialog mCustomDialog;
//	DropDownWindow< StaffInfo > mWorkerDropDownWindow;
//	DropDownWindow< TeaInfo > mTeaDropDownWindow;
//	/** parameter */
	private final String TAG = "InputWorkload";
	private List< Suggestion > mStringList = new ArrayList<Suggestion>();
//	/** Presenter */
//	InputWorkloadPrensenter mPrensenter;

	@Override
	protected void onCreate ( @Nullable Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inputworkload);
		ButterKnife.bind(this);
		initMenu();
//		initDialag();
		initFloatingSearchView();
//		mPrensenter = new InputWorkloadPrensenter(this, this);
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
//		final List< Suggestion > mStringList = new ArrayList< Suggestion >(new ArrayList<>(Arrays.asList(
//				new Suggestion("green"),
//				new Suggestion("blue"),
//				new Suggestion("pink"),
//				new Suggestion("purple"),
//				new Suggestion("brown"),
//				new Suggestion("gray"),
//				new Suggestion("red"),
//				new Suggestion("white"),
//				new Suggestion("black") )));
		StaffDBUtil staffDBUtil =StaffDBUtil.getInstance(this);

		for(StaffInfo staffInfo : staffDBUtil.selectAllStaff())
			mStringList.add(new Suggestion(staffInfo.getStaff_Name()));

		mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener()
		{

			@Override
			public void onSearchTextChanged ( String oldQuery, final String newQuery )
			{

				if ( ! oldQuery.equals("") && newQuery.equals("") )
				{
					mSearchView.clearSuggestions();
				} else
				{                                /**文本不为空时监听**/

					//this shows the top left circular progress
					//you can call it where ever you want, but
					//it makes sense to do it when loading something in
					//the background.
					mSearchView.showProgress();

					mSearchView.swapSuggestions(mStringList);

					mSearchView.hideProgress();

					//simulates a query call to a data source
					//with a new query.
				}

				Log.d(TAG, "onSearchTextChanged()" + " oldQuery:" + oldQuery + " newQuery:" + newQuery);
			}
		});

		mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener()
		{
			@Override
			public void onSuggestionClicked ( final SearchSuggestion searchSuggestion )
			{/**点击事件**/

//			Suggestion colorSuggestion = (Suggestion) searchSuggestion;
//			DataHelper.findColors(this, colorSuggestion.getBody(),
//					new DataHelper.OnFindColorsListener() {
//
//						@Override
//						public void onResults(List<ColorWrapper> results) {

//				mSearchResultsAdapter.swapData(results);
//						}
//
//					});
				Log.d(TAG, "onSuggestionClicked()" + searchSuggestion.getBody());

//			mLastQuery = searchSuggestion.getBody();
			}

			@Override
			public void onSearchAction ( String query )
			{
//			mLastQuery = query;
//
//			DataHelper.findColors(getActivity(), query,
//					new DataHelper.OnFindColorsListener() {
//
//						@Override
//						public void onResults(List<ColorWrapper> results) {

//				mSearchResultsAdapter.swapData(results);
//						}
//
//					});
				Log.d(TAG, "onSearchAction() " + query);
			}
		});
	}


//	@OnClick ( { R.id.name_imageView_inputWorkload } )
//	public void OnClickListerner ( View view )
//	{
//		switch ( view.getId() )
//		{
//			case R.id.name_imageView_inputWorkload:
//				showOrHideWorkerInfoWindow();
//				break;
//
//			default:
//				return;
//		}
//	}


	@Override
	public String getName ()
	{
//		return name_EditText_inputWorkload.getText().toS
		return null;
	}

	@Override
	public String getCategory ()
	{
//		return category_EditText_inputWorkload.getText().toString();
		return null;
	}

	@Override
	public float getUnitPrice ()
	{
//		return Float.parseFloat(unitPrice_EditText_inputWorkload.getText().toString());
		return 0;
	}

	@Override
	public float getWeight ()
	{
//		return Float.parseFloat(weight_EditText_inputWorkload.getText().toString());
		return 0;
	}

	@Override
	public float getMoney ()
	{
//		return Float.parseFloat(money_EditText_inputWorkload.getText().toString());
		return 0;
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
//		mWorkerDropDownWindow.updateListView(staffInfoList);
//		mWorkerDropDownWindow.show();
	}


}
