package br.com.gft.share;

public class Pagination {

	private int quantity = 10;
	private int max = 3;
	private int pages;
	private int total;
	private int current;
	private int firstPage;
	private int lastPage;
	private int begin;
	
	public Pagination(int total, Integer current) {
		this.total = total;
		this.current = current == null ? 1 : current;
		
		pages = (int) Math.ceil(this.total / (double) quantity);
		firstPage = Math.max(1, this.current - 3);
		lastPage = Math.min(pages, this.current + 3);
		begin = (this.current - 1) * quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getMax() {
		return max;
	}

	public int getPages() {
		return pages;
	}

	public int getTotal() {
		return total;
	}

	public int getCurrent() {
		return current;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public int getBegin() {
		return begin;
	}
	
}
