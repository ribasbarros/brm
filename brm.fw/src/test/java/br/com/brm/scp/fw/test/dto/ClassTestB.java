package br.com.brm.scp.fw.test.dto;

import java.util.Collection;

public class ClassTestB {
	private String a;
	private Collection<Object> c;

	private Collection<ClassTestD> listC;

	private ClassTestD objectNoList;

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

	public ClassTestD getObjectNoList() {
		return objectNoList;
	}

	public void setObjectNoList(ClassTestD objectNoList) {
		this.objectNoList = objectNoList;
	}

}