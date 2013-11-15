package ua.com.itinterview.dao.paging;

public class PagingFilter {
    private int currentPage = 0;
    private int itemsPerPage = 10;
    private int itemsTotal = 0;
    private int pagesShowTotal = 5;

    public PagingFilter() {
    }

    public PagingFilter(int currentPage, int itemsPerPage) {
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
    }

    public PagingFilter(int currentPage, int itemsPerPage, int itemsTotal) {
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.itemsTotal = itemsTotal;
    }

    public PagingFilter(int currentPage, int itemsPerPage, int itemsTotal, int pagesShowTotal) {
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.itemsTotal = itemsTotal;
        this.pagesShowTotal = pagesShowTotal;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        int max = getMaxPageN();
        if (currentPage > max) {
            this.currentPage = max;
        } else if (currentPage < 0) {
            this.currentPage = 0;
        } else {
            this.currentPage = currentPage;
        }
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public int getItemsTotal() {
        return itemsTotal;
    }

    public void setItemsTotal(int itemsTotal) {
        this.itemsTotal = itemsTotal;
    }

    public int getPagesShowTotal() {
        return pagesShowTotal;
    }

    public void setPagesShowTotal(int pagesShowTotal) {
        this.pagesShowTotal = pagesShowTotal;
    }

    public int getFrom() {
        int from = currentPage - pagesShowTotal / 2;
        return from < 0 ? 0 : from;
    }

    public int getTo() {
        int to = getFrom() + pagesShowTotal - 1;
        int max = getMaxPageN();
        return to > max ? max : to;
    }

    public int getPrev() {
        int prev = currentPage - 1;
        return prev < 0 ? 0 : prev;
    }

    public int getNext() {
        int prev = currentPage + 1;
        int max = getMaxPageN();
        return prev > max ? max : prev;
    }

    public int getMaxPageN() {
        int maxPages = itemsTotal / itemsPerPage - 1;
        if (itemsTotal % itemsPerPage != 0) {
            maxPages++;
        }
        return maxPages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PagingFilter that = (PagingFilter) o;

        if (currentPage != that.currentPage) return false;
        if (itemsPerPage != that.itemsPerPage) return false;
        if (itemsTotal != that.itemsTotal) return false;
        if (pagesShowTotal != that.pagesShowTotal) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = currentPage;
        result = 31 * result + itemsPerPage;
        result = 31 * result + itemsTotal;
        result = 31 * result + pagesShowTotal;
        return result;
    }

    @Override
    public String toString() {
        return "PagingFilter{" +
                "currentPage=" + currentPage +
                ", itemsPerPage=" + itemsPerPage +
                ", itemsTotal=" + itemsTotal +
                ", pagesShowTotal=" + pagesShowTotal +
                '}';
    }
}
