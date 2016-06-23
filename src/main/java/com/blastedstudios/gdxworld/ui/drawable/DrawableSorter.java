package com.blastedstudios.gdxworld.ui.drawable;

import java.util.Comparator;

/**
 * Simple sorter to place backgrounds in order, probably want to write your own
 */
public class DrawableSorter implements Comparator<Drawable> {
	@Override public int compare(Drawable o1, Drawable o2) {
		if(o1 instanceof BackgroundDrawable && o2 instanceof BackgroundDrawable)
			return Float.compare(
					((BackgroundDrawable)o2).background.getDepth(),
					((BackgroundDrawable)o1).background.getDepth());
		if(o1 instanceof BackgroundDrawable && !(o2 instanceof BackgroundDrawable))
			return Float.compare(1f, ((BackgroundDrawable)o1).background.getDepth());
		if(!(o1 instanceof BackgroundDrawable) && o2 instanceof BackgroundDrawable)
			return Float.compare(((BackgroundDrawable)o2).background.getDepth(), 1f);
		return 0;
	}

}
