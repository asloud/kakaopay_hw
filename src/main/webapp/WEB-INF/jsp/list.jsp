<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<html>
    <head>할일 목록</head>

    <body>

    <form action="/work/completed" method="post">
        <input type="hidden" name="_method" value="put"/>

        <table border="1">
            <tr>
                <th align="center" width="20"></th>
                <th align="center" width="100">ID</th>
                <th align="center" width="300">할일</th>
                <th align="center" width="200">참조 할일ID</th>
                <th align="center" width="200">작성일시</th>
                <th align="center" width="200">최종 수정일시</th>
                <th align="center" width="200">완료일시</th>
                <th align="center" width="20"></th>
            </tr>

        <c:forEach  var="item" items="${list}">
            <tr><td><input type="checkbox" name="completedCheck${item.id}" value="${item.id}" /></td>

                <td align="center"><c:out value="${item.id}" /></td>
                <td><c:out value="${item.todo}" /></td>
                <td><c:out value="${item.refidStr}" /></td>
                <td align="center"><fmt:formatDate value="${item.createdDate}" type="date" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td align="center"><fmt:formatDate value="${item.modifiedDate}" type="date" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td align="center">
                    <c:choose>
                        <c:when test="${item.completedYn eq true}">
                            <fmt:formatDate value="${item.completedDate}" type="date" pattern="yyyy-MM-dd HH:mm:ss" />
                        </c:when>
                    </c:choose>
                </td>
                <td><a href="/work/updateWork?id=${item.id}" >할일 수정</a></td>
            </tr>
        </c:forEach>
        </table>

        <!-- 할일 추가, 완료처리 -->
        <a href="/work/insertWork">할일 추가</a>
        <input type="submit" value="선택된 할일 완료" />

        <!-- paging 처리 -->
        <div>
            <span>
                <c:forEach var="i" begin="1" end="${pageCnt}" step="1">
                    <a href="/work/list?pageNum=${i}"> ${i} </a>
                </c:forEach>
            </span>
        </div>

    </form>
    </body>
</html>