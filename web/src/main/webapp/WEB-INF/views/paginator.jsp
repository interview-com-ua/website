<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- paginator -->
<div class="paginator">
    <ul>
        <c:if test="${pagingFilter.currentPage != 0}">
            <li><a href="${linkMask}0">&laquo;</a></li>
        </c:if>
        <c:if test="${pagingFilter.currentPage != pagingFilter.prev}">
            <li><a href="${linkMask}${pagingFilter.prev}">&lt;</a></li>
        </c:if>
        <c:forEach begin="${pagingFilter.from}" end="${pagingFilter.to}" step="1" varStatus="pageN">
            <c:choose>
                <c:when test="${pageN.index == pagingFilter.currentPage}">
                    <li><b>${pageN.index + 1}</b></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${linkMask}${pageN.index}">${pageN.index + 1}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${pagingFilter.currentPage != pagingFilter.next}">
            <li><a href="${linkMask}${pagingFilter.next}">&gt;</a></li>
        </c:if>
        <c:if test="${pagingFilter.currentPage != pagingFilter.maxPageN}">
            <li><a href="${linkMask}${pagingFilter.maxPageN}">&raquo;</a></li>
        </c:if>
    </ul>
</div>
<!-- end paginator -->