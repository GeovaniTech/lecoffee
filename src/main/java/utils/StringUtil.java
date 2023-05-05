package utils;

public class StringUtil {
	public static boolean isNotNull(String string) {
		if(string != null && !string.equals("")) {
			return true;
		}
		
		return false;
	}
}
