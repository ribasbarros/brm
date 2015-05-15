package br.com.brm.scp.fw.helper.objects;

public class StringHelper {

	public static String toFirstProperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
}
