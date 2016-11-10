package com.fengjie.model.helper.pinyinComparator;


import com.fengjie.model.baseClass.SortNameData;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<SortNameData>
{		//只要继承SortNameData都可以复用此类

	public int compare( SortNameData o1, SortNameData o2) {
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}


