package br.com.brm.scp.fw.helper;

public class MathBrmHelper {

	public static double desvioPadraoTest(Double[] objetos, Double mediaAritimetica) {
		if (objetos.length == 1) {
			return 0.0;
		} else {
			double somatorio = 0D;
			for (int i = 0; i < objetos.length; i++) {
				double result = objetos[i] - mediaAritimetica;
				somatorio += Math.pow(Math.abs(result), 2);
			}

			double raiz = Math.sqrt(somatorio / objetos.length);

			return raiz;
		}
	}

	public static Double mediaAritmetica(Double[] list) {

		int size = list.length;
		double sun = 0D;
		for (Double line : list) {
			sun += line;
		}
		double media = sun / size;
		return media;
	}
	
}
