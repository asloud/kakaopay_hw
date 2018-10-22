<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>할일 내용 - 등록</title>
</head>

<script>
function updateBtnClick() {
    document.updateForm.submit();
}
</script>

<body>

<form name="updateForm" action="/work/processUpdate" method="post">
    <input type="hidden" name="_method" value="put"/>
    <input type="hidden" name="id" value="${work.id}" />
    <table border="1" width="1000">
        <tr>
            <td>할일 내용 등록</td>
        </tr>
        <tr>
            <td>
                <input type="text" name="todo"  value="${work.todo}" style="width: 1000px;" />
            </td>
        </tr>
    </table>
    <br/>

    <div>
        <c:forEach var="item" items="${works}" >
            <c:choose>
                <c:when test="${item.check}" >
                    <input type="checkbox" name="chk_${item.id}" checked="checked" value="${item.id}" />
                </c:when>
                <c:otherwise>
                    <input type="checkbox" name="chk_${item.id}" value="${item.id}" />
                </c:otherwise>
            </c:choose>

            <input type="label" value="${item.todo}" /> <br/>
        </c:forEach>
    </div>

    <input type="button" name="updateBtn" value="등록" onclick="updateBtnClick();" />

</form>
</body>

</html>
