package br.com.brm.scp.api.pages;

import java.io.Serializable;

public class SearchPageableVO implements Serializable {

	private static final long serialVersionUID = -2018517185290157566L;

	private String searchTerm;
	private int pageIndex;
	private int size;

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
