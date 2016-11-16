package com.fengjie.model.activity.addTea;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fengjie.model.R;
import com.fengjie.model.adapter.fragment.FragmentViewPagerAdapter;
import com.fengjie.model.userdefinedview.CustomBottomButton;
import com.fengjie.model.userdefinedview.Menu;
import com.fengjie.model.userdefinedview.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class AddTeaActivity extends AppCompatActivity
{

	private Menu menu;
	private CustomBottomButton mCustomBottomButton;

	private FragmentManager fragmentManager;
	private FragmentViewPagerAdapter mFragmentViewPagerAdapter;
	private List< Fragment > mFragments = new ArrayList< Fragment >();
	private AddInfoFragmentAddTea mAddTeaFragment_addTea = new AddInfoFragmentAddTea();
	private TeaInfoFragmentAddTea mTeaInfoFragment_addTea = new TeaInfoFragmentAddTea();
	private NoScrollViewPager mViewPager;

	private int FRAGMENT_MOVE_FLAG = 0;
	private final int ADD_TEA_FRAGMENT_MOVE = 0;
	private final int TEA_INFO_FRAGMENT_MOVE = 1;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addtea);
		initView();
		initMenu();
		initViewPager();
		initBottomButton();
	}

	/**
	 * 初始化界面
	 */
	private void initView ()
	{
		mCustomBottomButton = ( CustomBottomButton ) findViewById(R.id.bottomButton_addTea);
	}

	/**
	 * 菜单初始化方法，后期再配置对应适配器
	 */
	private void initMenu ()
	{
		menu = ( Menu ) findViewById(R.id.menu_view_addTea);
		menu.setBackgroundResource(R.color.color_addTeaLayout);
		menu.setTitle_textView_menu("添加茶叶");
		menu.setRightButtonVisibility(false);        //隐藏右边按钮
	}


	/**
	 * 初始化ViewPagerFragment
	 */
	private void initViewPager ()
	{
		mViewPager = ( NoScrollViewPager ) findViewById(R.id.viewPager_tjcy);
		fragmentManager = getSupportFragmentManager();
		mFragments.add(mAddTeaFragment_addTea);
		mFragments.add(mTeaInfoFragment_addTea);
		mFragmentViewPagerAdapter = new FragmentViewPagerAdapter(fragmentManager, mFragments);
		mViewPager.setAdapter(mFragmentViewPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			@Override
			public void onPageScrolled ( int position, float positionOffset, int positionOffsetPixels )
			{

			}

			@Override
			public void onPageSelected ( int position )
			{

				mCustomBottomButton.setLeftButtonBackground(position == 0 ? R.color.color_addTeaLayout : R.color.gray);//设置Button颜色
				mCustomBottomButton.setRightButtonBackground(position == 0 ? R.color.gray : R.color.color_addTeaLayout);
				if ( position == 0 )
				{
					FRAGMENT_MOVE_FLAG = ADD_TEA_FRAGMENT_MOVE;
					menu.setTitle_textView_menu("添加茶叶");
				} else if ( position == 1 )
				{
					FRAGMENT_MOVE_FLAG = TEA_INFO_FRAGMENT_MOVE;
					menu.setTitle_textView_menu("茶叶列表");
				}
			}

			@Override
			public void onPageScrollStateChanged ( int state )
			{

			}
		});
	}

	/**
	 * 初始化底部按钮方法
	 */
	private void initBottomButton ()
	{

		mCustomBottomButton.init("添加茶叶", "茶叶列表", new View.OnClickListener()
		{
			@Override
			public void onClick ( View v )
			{
				switch ( v.getId() )
				{
					case R.id.left_button_bottomButton_moudule:    //
						if ( FRAGMENT_MOVE_FLAG == ADD_TEA_FRAGMENT_MOVE )
							return;
						mViewPager.setCurrentItem(ADD_TEA_FRAGMENT_MOVE);
						break;
					case R.id.right_button_bottomButton_moudule:
						if ( FRAGMENT_MOVE_FLAG == TEA_INFO_FRAGMENT_MOVE )
							return;
						mViewPager.setCurrentItem(TEA_INFO_FRAGMENT_MOVE);
						break;
					default:
						break;
				}
			}
		});
		mCustomBottomButton.setLeftButtonBackground(R.color.color_addTeaLayout);//设置Button颜色
		mCustomBottomButton.setRightButtonBackground(R.color.gray);
	}

}
