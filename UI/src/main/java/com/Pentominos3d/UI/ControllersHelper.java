package com.Pentominos3d.UI;

import java.util.regex.Pattern;

/**
 * General shared controller logic.
 *
 */
public class ControllersHelper {
	public static boolean validNumber(String text) {
		Pattern digit = Pattern.compile("(?<![\\d-])\\d+");
		return digit.matcher(text).matches();
	}
}
