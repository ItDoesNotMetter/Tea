package com.fengjie.model.activity.addTea;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.fengjie.model.R;
import com.fengjie.model.adapter.recyclerview.adapter.CommonAdapter;
import com.fengjie.model.adapter.recyclerview.base.ViewHolder;
import com.fengjie.model.dbhelper.Tea.TeaDBUtil;
import com.fengjie.model.dbhelper.Tea.TeaInfo;
import com.fengjie.model.flag.addTea.FlagTeaInfoFragment;

import java.util.List;


public class TeaInfoFragmentAddTea extends Fragment
{

	/** 适配 */
	private Context mContext;
	private RecyclerView mRecyclerView;
	private List< TeaInfo > SourceDateList;
	private CommonAdapter< TeaInfo > mAdapter;

	/** 数据库测试 */
	private TeaDBUtil teaDBUtil;

	/** 自定义控件 */
	private TextView teaCategory, date;
	private EditText price;

	@Override
	public void onCreate ( @Nullable Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
//		EventBus.getDefault().register(this);       //订阅
	}

	@Override
	public View onCreateView ( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
		mContext = getActivity();
		View view = LayoutInflater.from(mContext).inflate(R.layout.activity_recyclerview, null);
//		findViews(view);
//        setViews();

		initRecyclerView(view);
		return view;
	}

	/** 取消总线的注册 */
	@Override
	public void onDestroy ()
	{
		super.onDestroy();
//		EventBus.getDefault().unregister(this);
	}

	public void onEventMainThread ( FlagTeaInfoFragment flag )
	{
//		if ( flag.isUPDATE_FALG() == FlagTeaInfoFragment.UPDATE )
//			adapter.updateListView(SourceDateList = teaDBUtil.seleteAllTea());
	}


	private void initRecyclerView ( View view )
	{
		mRecyclerView = ( RecyclerView ) view.findViewById(R.id.id_recyclerView_commonRecyclerView);
		//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);    //传入上下文
		//        layoutManager.setReverseLayout(false);                         //设置是否反向放置
		//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
		//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, columnNumbers));

		initDatas();                    //初始化模拟数据
		initCommonAdapter();            //Base-Adapter
//		initEmptyWrapper();             //空界面Adapter包裹
//		initHeaderAndFooterWrapper();   //顶部和底部Adapter包裹
//		initLoadmoreWrapper();          //底部下拉导入Adapter包裹
//		initRefresh();                  //顶部监听事件

		mRecyclerView.setAdapter(mAdapter);//将最后包装好的适配器植入RecyclerView
	}

	private void initDatas ()
	{
		teaDBUtil = TeaDBUtil.getInstance(mContext);
		SourceDateList = teaDBUtil.seleteAllTea();
	}

	private void initCommonAdapter ()
	{
		/**commonAdapter-start**/
		mAdapter = new CommonAdapter< TeaInfo >(mContext, R.layout.item_teainfo, SourceDateList)
		{
			@Override
			protected void convert ( ViewHolder holder, TeaInfo info, int position )
			{
				holder.setText(R.id.teaCategory_textview_teaInfo_tjcy, info.getTea_Category());
				holder.setText(R.id.price_textview_teaInfo_tjcy, info.getTea_Price() + "");
				holder.setText(R.id.addDate_textview_teaInfo_tjcy, info.getTea_Datetime());
			}
		};

//		mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener(){
//
//			@Override
//			public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
////				Toast.makeText(getApplicationContext(), "pos = " + position, Toast.LENGTH_SHORT).show();
////				mAdapter.notifyItemRemoved(position);       //删除点击Item
//			}

//			@Override
//			public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
//				return false;
//			}
//
//		});
		/**commonAdapter-end**/
	}


	private void findViews ( View parent )
	{

//        characterParser = CharacterParser.getInstance();                                                //将汉字转拼音
		//mClearEditText = (ClearEditText) parent.findViewById(R.id.filter_editText_peopleSort_gr);        //搜索框

	}

	private void setViews ()
	{

//        mClearEditText.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //当输入框里面的字符串为空，更新为原来的列表，否则为过滤数据更新adapter,再使RecyclerView更新适配器
//       //         filterData(s.toString());
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

	}
//    /**
//     * 1.布局管理器 - 初始化
//     * @param reverse
//     * @param vertical
//     */
//
//    private void setLayoutWay(boolean reverse,boolean vertical){
//
//        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);    //传入上下文
//        layoutManager.setReverseLayout(reverse);                         //设置是否反向放置
//        layoutManager.setOrientation(vertical                        //设置方向
//                ? LinearLayoutManager.VERTICAL
//                : LinearLayoutManager.HORIZONTAL);
//        mRecyclerView.setLayoutManager(layoutManager);
//
//    }
//
//    /**
//     *  2.适配模式（设置Adapter-初始化
//     */
//    private void loadListData(){           //给RecyclerView加载数据，以下为步骤
//
//        teaDBUtil= TeaDBUtil.getInstance(mContext);
//        SourceDateList=teaDBUtil.seleteAllTea();
//
////       //导入数据对象
//        adapter = new TeaInfoAdapter(SourceDateList);     //数据
//
//        mRecyclerView.setAdapter(adapter);
//
//        //设置Item监听
//        adapter.setOnItemClickListener(new TeaInfoAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view) {
//
//                showDialog(
//                        ((TextView )view.findViewById(R.id.teaCategory_textview_teaInfo_tjcy)).getText().toString(),
//                        ((TextView )view.findViewById(R.id.price_textview_teaInfo_tjcy)).getText().toString(),
//                        ((TextView )view.findViewById(R.id.addDate_textview_teaInfo_tjcy)).getText().toString()
//                );
//
//            }
//        });
//
//    }


//	private void showDialog ( final String teaCategory, final String price, final String date )
//	{
//
//		final View view = LayoutInflater.from(mContext).inflate(R.layout.module_dialog_teainfo, null);
//
//		this.teaCategory = ( TextView ) view.findViewById(R.id.teaCategory_dialog_teaInfo);
//		this.price = ( EditText ) view.findViewById(R.id.price_dialog_teaInfo);
//		this.date = ( TextView ) view.findViewById(R.id.date_dialog_teaInfo);
//
//		this.teaCategory.setText(teaCategory);
//		this.price.setText(price);
//		this.date.setText(date);
//
//		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//
//		builder.setView(view);
//		builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
//		{
//			@Override
//			public void onClick ( DialogInterface dialog, int which )
//			{
//				TeaInfo teaInfo = new TeaInfo(teaCategory,      //通过种类更新
//						                             Float.parseFloat(( ( EditText ) view.findViewById(R.id.price_dialog_teaInfo) ).getText().toString()));    //取编辑框中单价
//				if ( teaDBUtil.UpdateTeaUseCategory(teaInfo) != 0 )
//				{
//					Toast.makeText(mContext, "更新茶叶价格成功", Toast.LENGTH_SHORT).show();
////                    adapter.updateListView(SourceDateList=teaDBUtil.seleteAllTea());
//				} else
//					Toast.makeText(mContext, "更新茶叶价格失败", Toast.LENGTH_SHORT).show();
//				dialog.dismiss();
//			}
//		});
//
//		builder.setNegativeButton("退出", new DialogInterface.OnClickListener()
//		{
//			@Override
//			public void onClick ( DialogInterface dialog, int which )
//			{
//				dialog.dismiss();
//			}
//		});
//
//		AlertDialog dialog = builder.create();
//		dialog.show();
//
//	}


}
