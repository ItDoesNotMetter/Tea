package com.fengjie.model.helper.suggestion;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * @author Created by FengJie on 2016/11/25-11:24.
 * @brief
 * @attention
 */

public class TeaSuggestion implements SearchSuggestion
{

	private String mString;
	private int id;
	private float mUnitPrice;
	private boolean mIsHistory = false;

	public TeaSuggestion (final String suggestion ,final int id ,final float mUnitPrice)
	{
		this.mString = suggestion.toLowerCase();
		this.id = id;
		this.mUnitPrice = mUnitPrice ;
	}

	public TeaSuggestion ( Parcel source )
	{
		this.mString = source.readString();
		this.mIsHistory = source.readInt() != 0;
	}


	public static final Creator< Suggestion > CREATOR = new Creator< Suggestion >()
	{
		@Override
		public Suggestion createFromParcel ( Parcel source )
		{
			return new Suggestion(source);
		}

		@Override
		public Suggestion[] newArray ( int size )
		{
			return new Suggestion[size];
		}
	};

	public void setIsHistory ( boolean isHistory )
	{
		this.mIsHistory = isHistory;
	}

	public boolean getIsHistory ()
	{
		return this.mIsHistory;
	}

	public int getId ()
	{
		return id;
	}

	public float getUnitPrice ()
	{
		return mUnitPrice;
	}

	@Override
	public String getBody ()
	{
		return mString;
	}

	@Override
	public int describeContents ()
	{
		return 0;
	}

	@Override
	public void writeToParcel ( Parcel dest, int flags )
	{
		dest.writeString(mString);
		dest.writeInt(mIsHistory ? 1 : 0);
	}

}
