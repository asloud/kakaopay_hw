<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>할일 내용 - 등록</head>

    <body>

        <form action="/work/processInsert" method="post">
            <table border="1" width="1000">
                <tr>
                    <td>할일 내용 등록</td>
                </tr>
                <tr>
                    <td> <input type="text" name="todo" width="500" style="width: 1000px;" /> </td>
                </tr>
            </table>
            <br/>

            <div>
                <c:forEach var="item" items="${list}" >
                    <input type="checkbox" name="chk_${item.id}" value="${item.id}" /> <input type="label" value="${item.todo}" />
                    <br/>
                </c:forEach>
            </div>
            <input type="submit" value="등록" />

        </form>
    </body>

</html>