package com.fengjie.model.userdefinedview;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CustomDialog extends android.support.v7.app.AlertDialog
{

	public final static boolean SHOW = true;
	public final static boolean HIDE = false;
	private TextView message_textView_dialog_moudule;
	private Button positiveBtn;    //右边确认按钮
	private Button negativeBtn;    //左边取消按钮
	private ImageView mBarcodeView;
	private LinearLayout LayoutBackGround;
	private View mView;
//	private Context mContext;

	public CustomDialog ( Context context )
	{
		super(context);
//		mContext = context;
//        init();       //在Fragment中无法调用.
	}

//    private void init(){
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.module_dialog_ensure,null);
//        message_textView_dialog_moudule = (TextView) view.findViewById(R.id.message_textView_dialog_module);
//        positiveBtn = (Button) view.findViewById(R.id.positive_button_dialog_module);
//        negativeBtn = (Button) view.findViewById(R.id.negative_button_dialog_module);
//        LayoutBackGround = (LinearLayout) view.findViewById(R.id.ensureLayout_module);
//        mBarcodeView = (ImageView) view.findViewById(R.id.barcode_imageView_module);
//
//        positiveBtn.setOnClickListener(new closeDialogButtonClickListener());
//        negativeBtn.setOnClickListener(new closeDialogButtonClickListener());
//
//    super.requestWindowFeature(Window.FEATURE_NO_TITLE);    //design no title before must set view.
//    super.setContentView(view);
//    super.setCancelable(false); //use superFather's method,touch outside can't lose dialog.
//    }

	/**
	 * init method
	 */
//	public void initDialog ()
//	{
//		View view = LayoutInflater.from(getContext()).inflate(R.layout.view_dialog_ensure, null);
//		message_textView_dialog_moudule = ( TextView ) view.findViewById(R.id.message_textView_dialog_module);
//		positiveBtn = ( Button ) view.findViewById(R.id.positive_button_dialog_module);
//		negativeBtn = ( Button ) view.findViewById(R.id.negative_button_dialog_module);
//		LayoutBackGround = ( LinearLayout ) view.findViewById(R.id.ensureLayout_module);
//		mBarcodeView = ( ImageView ) view.findViewById(R.id.barcode_imageView_module);
//
//		positiveBtn.setOnClickListener(new closeDialogButtonClickListener());
//		negativeBtn.setOnClickListener(new closeDialogButtonClickListener());
//		//call superFather's method
//		super.requestWindowFeature(Window.FEATURE_NO_TITLE);    //design no title before must set view.
//		super.setContentView(view);
//		super.setCancelable(false); //touch outside can't lose dialog.
//	}

	/** init method
	 * @param id
	 * @param leftButtonListener
	 * @param rightButtonListener
	 */
	public void initDialog ( final int id, final String title, View.OnClickListener leftButtonListener, View.OnClickListener rightButtonListener )
	{
		 mView = LayoutInflater.from(getContext()).inflate(id, null);
//        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        positiveBtn =mView.findViewById();
		super.setTitle(title);
		super.setView(mView);
//        if(leftButtonListener==null)
	}

	public void setText( int viewId, String text)
	{
		TextView tv = (TextView ) mView.findViewById(viewId);
		tv.setText(text);
	}

	/**
	 * 重载确定按钮点击事件
	 *
	 * @param setPosotiveButtonOnClickListener
	 */
	public void setPositiveOnClickListener ( View.OnClickListener setPosotiveButtonOnClickListener )
	{
		positiveBtn.setOnClickListener(setPosotiveButtonOnClickListener);
	}

	/**
	 * 重载所有按钮点击事件
	 *
	 * @param setButtonOnClickListener
	 */
	public void setAllOnClickListener ( View.OnClickListener setButtonOnClickListener )
	{
		positiveBtn.setOnClickListener(setButtonOnClickListener);
		negativeBtn.setOnClickListener(setButtonOnClickListener);
	}

	/**
	 * 赋入关闭对话框方法
	 */
	private class closeDialogButtonClickListener implements View.OnClickListener
	{

		@Override
		public void onClick ( View v )
		{
			dismiss();
		}
	}

	/**
	 * 隐藏Button方法
	 *
	 * @param flag 为真则显示,反之隐藏
	 */
	public void setLeftButtonVisibility ( boolean flag )
	{
		positiveBtn.setVisibility(flag ? View.VISIBLE : View.GONE);
	}

	public void setRightButtonVisibility ( boolean flag )
	{
		negativeBtn.setVisibility(flag ? View.VISIBLE : View.GONE);
	}

	public void setMessageVisibility ( boolean flag )
	{
		message_textView_dialog_moudule.setVisibility(flag ? View.VISIBLE : View.GONE);
	}

	public void setImageViewVisibility ( boolean flag )
	{
		setMessageVisibility(flag ? HIDE : SHOW);
		mBarcodeView.setVisibility(flag ? View.VISIBLE : View.GONE);
	}

	public void setImageBitmap ( Bitmap bitmap )
	{
		mBarcodeView.setImageBitmap(bitmap);
	}

	/**
	 * 赋入字符串形参,重载内容
	 *
	 * @param message
	 */
	public void setMessage ( String message )
	{
		message_textView_dialog_moudule.setText(message);
	}

	public void setLayoutBackGround ( int Color )
	{
		LayoutBackGround.setBackgroundResource(Color);
	}


	public void setLeftButtonText ( String str )
	{
		negativeBtn.setText(str);
	}

	public void setRightButtonText ( String str )
	{
		positiveBtn.setText(str);
	}
	/**
	 * use example
	 */
//    CustomDialog mCustomDialog = new CustomDialog(MainActivity.this);
//    mCustomDialog.setContentView(R.layout.ensuredialog);
//    mCustomDialog.setTitle("标题");
//    mCustomDialog.setMessage("内容");
//    mCustomDialog.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            mCustomDialog.show();
//        }
//    });


}
