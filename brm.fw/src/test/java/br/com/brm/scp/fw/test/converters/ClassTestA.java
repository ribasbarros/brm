package br.com.brm.scp.fw.test.converters;

import java.util.Collection;

public class ClassTestA {
	
	private String a;
	private Integer b;
	private Collection<Object> c;
	
	@ConvertFor(ClassTestD.class)
	private Collection<ClassTestC> listC;

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public Integer getB() {
		return b;
	}

	public void setB(Integer b) {
		this.b = b;
	}

	public Collection<Object> getC() {
		return c;
	}

	public void setC(Collection<Object> c) {
		this.c = c;
	}

	public Collection<ClassTestC> getListC() {
		return listC;
	}

	public void setListC(Collection<ClassTestC> listC) {
		this.listC = listC;
	}

}