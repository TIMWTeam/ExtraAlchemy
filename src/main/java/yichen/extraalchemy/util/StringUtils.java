package yichen.extraalchemy.util;

public class StringUtils {

	public static String merge(String[] array) {
		String ret = "";
		for (String s : array) {
			ret += s;
		}
		return ret;
	}

	public static String mergeWithUpper(String[] array) {
		String ret = "";
		for (String s : array) {
			ret += replaceFirstToUpperCase(s);
		}
		return ret;
	}

	public static String mergeWithUnderline(String[] array) {
		String ret = "";
		for (String s : array) {
			ret += s + "_";
		}
		return ret.substring(0, ret.length() - 1);
	}

	public static String replaceFirstToUpperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

}
