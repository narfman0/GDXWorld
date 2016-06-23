package com.blastedstudios.gdxworld.util;

import java.util.Comparator;

public class LoadPriorityComparator implements Comparator<IMode>{
	@Override public int compare(IMode o1, IMode o2) {
		return ((Integer)o1.getLoadPriority()).compareTo(o2.getLoadPriority());
	}
}
