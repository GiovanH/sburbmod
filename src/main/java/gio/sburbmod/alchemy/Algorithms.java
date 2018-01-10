package gio.sburbmod.alchemy;

public class Algorithms {
	// public static long hashString(String string, long M) {
	// long h = 1125899906842597L; // prime
	// int len = string.length();
	// for (int i = 0; i < len; i++) {
	// h = 31*h + string.charAt(i);
	// }
	// return h % M;
	// }

	public static long hashString(String string, long M) {
		return Math.abs(string.hashCode()) % M;
	}
	// public static long hashString(String s, long M) {
	// int intLength = s.length() / 4;
	// long sum = 0;
	// for (int j = 0; j < intLength; j++) {
	// char c[] = s.substring(j * 4, (j * 4) + 4).toCharArray();
	// long mult = 1;
	// for (int k = 0; k < c.length; k++) {
	// sum += c[k] * mult;
	// mult *= 256;
	// }
	// }
	// char c[] = s.substring(intLength * 4).toCharArray();
	// long mult = 1;
	// for (int k = 0; k < c.length; k++) {
	// sum += c[k] * mult;
	// mult *= 256;
	// }
	// return (Math.abs(sum) % M);
	// }
}
