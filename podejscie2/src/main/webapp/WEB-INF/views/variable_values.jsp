<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta http-equiv="refresh" content="5" />
    <link rel="stylesheet" href="/resources/css/style.css">
    <title>History of variable</title>
</head>
<body>
<h1>History of variable</h1>
<hr>

<div class="demo-wrapper">
<table class="content">
    <tr>
        <td>Time</td><td>Value</td><td>Variable Id</td><td>Unit</td>
    </tr>
    <c:forEach items="${values}" var="value">
        <tr>
            <td><c:out value="${value.timestamp.toLocaleString()} "></c:out></td>
            <td><c:out value="${value.value} "></c:out></td>
            <td><c:out value="${value.variableId} "></c:out></td>
            <td><c:out value="${value.unit} "></c:out></td>
        </tr>

    </c:forEach>
</table>
</div>
<form  class="bottom" action="/logout"><input type="submit" value="Logout"></form>

</body>
</html>