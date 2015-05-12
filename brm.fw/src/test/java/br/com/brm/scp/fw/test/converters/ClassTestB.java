package br.com.brm.scp.fw.test.converters;

import java.util.Collection;

public class ClassTestB {
	private String a;
	private Collection<Object> c;

	private Collection<ClassTestD> listC;

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public Collection<Object> getC() {
		return c;
	}

	public void setC(Collection<Object> c) {
		this.c = c;
	}

	public Collection<ClassTestD> getListC() {
		return listC;
	}

	public void setListC(Collection<ClassTestD> listC) {
		this.listC = listC;
	}

}