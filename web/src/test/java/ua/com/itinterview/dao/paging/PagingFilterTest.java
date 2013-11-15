package ua.com.itinterview.dao.paging;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PagingFilterTest {

    @Test
    public void testBaseCalculation() {
        PagingFilter pagingFilter = new PagingFilter(0, 3, 5);
        assertEquals(0, pagingFilter.getCurrentPage());
        assertEquals(0, pagingFilter.getFrom());
        assertEquals(1, pagingFilter.getTo());
        assertEquals(1, pagingFilter.getMaxPageN());
    }

    @Test
    public void testPagesCountLessThanShowPages() {
        PagingFilter pagingFilter = new PagingFilter(0, 5, 10, 5);
        assertEquals(0, pagingFilter.getFrom());
        assertEquals(1, pagingFilter.getTo());
        assertEquals(1, pagingFilter.getMaxPageN());
        assertEquals(0, pagingFilter.getPrev());
        assertEquals(1, pagingFilter.getNext());
        pagingFilter.setCurrentPage(1);
        assertEquals(0, pagingFilter.getFrom());
        assertEquals(1, pagingFilter.getTo());
        assertEquals(1, pagingFilter.getMaxPageN());
        assertEquals(0, pagingFilter.getPrev());
        assertEquals(1, pagingFilter.getNext());
    }

    @Test
    public void testPagesCountBiggerThanShowPages() {
        PagingFilter pagingFilter = new PagingFilter(0, 5, 51, 5);
        assertEquals(0, pagingFilter.getFrom());
        assertEquals(4, pagingFilter.getTo());
        assertEquals(10, pagingFilter.getMaxPageN());
        assertEquals(0, pagingFilter.getPrev());
        assertEquals(1, pagingFilter.getNext());
        pagingFilter.setCurrentPage(9);
        assertEquals(7, pagingFilter.getFrom());
        assertEquals(10, pagingFilter.getTo());
        assertEquals(10, pagingFilter.getMaxPageN());
        assertEquals(8, pagingFilter.getPrev());
        assertEquals(10, pagingFilter.getNext());
    }

    @Test
    public void testPagesCountWhenSetWrongCurrentPage() {
        PagingFilter pagingFilter = new PagingFilter(0, 5, 51, 5);
        pagingFilter.setCurrentPage(-1);
        assertEquals(0, pagingFilter.getCurrentPage());
        pagingFilter.setCurrentPage(11);
        assertEquals(10, pagingFilter.getCurrentPage());
    }
}
