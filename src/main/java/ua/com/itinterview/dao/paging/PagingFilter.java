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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + currentPage;
	result = prime * result + itemsPerPage;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	PagingFilter other = (PagingFilter) obj;
	if (currentPage != other.currentPage)
	    return false;
	if (itemsPerPage != other.itemsPerPage)
	    return false;
	return true;
    }

}
