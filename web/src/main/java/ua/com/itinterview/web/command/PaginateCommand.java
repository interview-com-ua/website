package ua.com.itinterview.web.command;

import org.hibernate.validator.constraints.Range;

public class PaginateCommand {

    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaginateCommand that = (PaginateCommand) o;

        if (page != that.page) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return page;
    }

    @Override
    public String toString() {
        return "PaginateCommand{" +
                "page=" + page +
                '}';
    }
}
