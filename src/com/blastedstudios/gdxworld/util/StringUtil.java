package com.blastedstudios.gdxworld.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StringUtil {
	public static List<String> split(String string, String regex){
		return Arrays.asList(string.split(regex));
	}
	
	public static List<String> splitOnComma(String string){
		return split(string, ",\\s+");
	}
	
	public static String join(List<String> strings, String insert){
		StringBuilder result = new StringBuilder();
		for(Iterator<String> iter = strings.iterator(); iter.hasNext();){
			result.append(iter.next());
			if(iter.hasNext())
				result.append(insert);
		}
		return result.toString();
	}
	
	public static String joinWithComma(List<String> strings){
		return join(strings, ",");
	}
}
