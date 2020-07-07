<%--
  Created by IntelliJ IDEA.
  User: kimjaenyeong
  Date: 2020/07/06
  Time: 6:18 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>events</title>
</head>
<body>
<h1>이벤트 목록</h1>
<h2>${message}</h2>
<table>
    <tr>
        <th>이름</th>
        <th>시작</th>
    </tr>
    <c:forEach items="${events}" var="event">
        <tr>
            <td>${event.name}</td>
            <td>${event.startDate}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
