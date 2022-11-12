package praktikum2;

public class StringUtil {

	public static String[] toLowerCase(String[] tableNames) {

		String[] lowerCase = new String[tableNames.length];

		for (int i = 0; i < tableNames.length; i++) {
			lowerCase[i] = tableNames[i].toLowerCase();
		}

		return lowerCase;
	}

}
