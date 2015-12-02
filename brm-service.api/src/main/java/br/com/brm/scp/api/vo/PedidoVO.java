package br.com.brm.scp.api.vo;

public class PedidoVO {

	private String origem;;
	private Long quantidade;

	private int day;
	private int month;
	private int year;

	private double ddv;

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getDdv() {
		return ddv;
	}

	public void setDdv(double ddv) {
		this.ddv = ddv;
	}

}
