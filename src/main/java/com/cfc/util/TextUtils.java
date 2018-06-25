package com.cfc.util;


/**
 * @author yuanzhe(yuanzhe@lanxum.com)
 *
 * @date 2015年3月9日 下午1:45:49
 */
public class TextUtils {


	/**
	 * 判断是否为空字串
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}


	/**
	 * 判断是否为数字
	 */
	public static boolean isNumeric(String str){
		if(isEmpty(str)){
			return false;
		}
		int pc = 0;
		boolean isDigit = true;
		for (int i = 0; i < str.length(); i ++){
            char c = str.charAt(i);
            if(i != 0 && i != str.length() - 1 && c == '.'){
                pc ++; // 判断小数点
            } else {
                if (!Character.isDigit(c)){
                    isDigit = false;
                }
            }

		}
		if(pc > 1){
		    isDigit = false;
        }
		return isDigit;
	}


	/**
	 * 切分成为数组 并转化为int类型数组
	 */
	public static Integer[] splitToInt(String text, String split){
		if(text == null){
			return null;
		}
		if(isEmpty(text.trim())){
			return null;
		}
		Integer[] ret;
		if(text.contains(split)){
			String[] tagIds = text.split(split);
			ret = new Integer[tagIds.length];
			for(int i = 0; i < tagIds.length; i ++){
				try {
					ret[i] = Integer.valueOf(tagIds[i].trim());
				} catch (NumberFormatException ignore){}
			}
		} else {
			try {
				ret = new Integer[1];
				ret[0] = Integer.parseInt(text);
			} catch (NumberFormatException ignore){
				ret = null;
			}
		}
		return ret;

	}


	/***
	 * 数字数组转化为split
	 */
	public static String contractToString(Integer[] integers, String split){

		StringBuilder ret = new StringBuilder();
		if(isEmpty(split)){
			split = "";
		}
		if(integers != null && integers.length > 0){
			for(Integer i : integers){
				ret.append(String.valueOf(i));
				ret.append(split);
			}
		}

		if(ret.length() > 0){
			ret.substring(ret.length() - split.length(), ret.length());
		}
		return ret.toString();
	}

}
