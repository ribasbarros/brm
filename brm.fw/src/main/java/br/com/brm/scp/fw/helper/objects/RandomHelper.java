package br.com.brm.scp.fw.helper.objects;

import java.util.Random;
import java.util.UUID;

public class RandomHelper {

	private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		Random rnd = new Random();
		for (int i = 0; i < len; i++)
			sb.append(CHARS.charAt(rnd.nextInt(CHARS.length())));
		return sb.toString();
	}
	
	public static synchronized long UUID(){
		return UUID.randomUUID().getMostSignificantBits();
	}
	
}
