package br.com.brm.scp.api.pages;

import java.io.Serializable;
import java.util.Collection;

public class Pageable<T> implements Serializable {

	private static final long serialVersionUID = -2437853769045448579L;

	private Collection<T> result;
	private int numberOfElements;
	private int totalPages;
	private int pageIndex;

	public Pageable() {
		super();
	}

	public Pageable(Collection<T> result, int numberOfElements, int totalPages, int pageIndex) {
		super();
		this.result = result;
		this.numberOfElements = numberOfElements;
		this.totalPages = totalPages;
		this.pageIndex = pageIndex;
	}

	public Collection<T> getResult() {
		return result;
	}

	public void setResult(Collection<T> result) {
		this.result = result;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

}
