package ua.com.itinterview.dao.paging;

public class PagingFilter {
    private int currentPage = 0;
    private int itemsPerPage = 10;

    public PagingFilter() {
    }

    public PagingFilter(int currentPage, int itemsPerPage) {
	this.currentPage = currentPage;
	this.itemsPerPage = itemsPerPage;
    }

    public int getCurrentPage() {
	return currentPage;
    }

    public void setCurrentPage(int currentPage) {
	this.currentPage = currentPage;
    }

    public int getItemsPerPage() {
	return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
	this.itemsPerPage = itemsPerPage;
    }

}
