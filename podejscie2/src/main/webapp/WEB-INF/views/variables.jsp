<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="/resources/css/style.css">
    <title>Spring Boot</title>
</head>
<body>
<h1>List of variables</h1>
<hr>


<table class="demo-wrapper">
    <tr>
        <td>Name</td><td>description</td><td>Last activity</td><td>Created</td>
    </tr>
    <c:forEach items="${variables}" var="variable">
        <tr>
            <td><c:out value="${variable.id} "></c:out></td>
            <td><c:out value="${variable.description} "></c:out></td>
            <td><c:out value="${variable.tags} "></c:out></td>
            <form action="/variables/${variable.id}"><td><input type="submit" value="History"></td></form>
        </tr>

    </c:forEach>
</table>
<form action="/logout"><input type="submit" value="Logout"></form>

</body>
</html>