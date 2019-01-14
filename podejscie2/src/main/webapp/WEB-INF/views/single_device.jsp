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
<h1>To be implemented!</h1>
<hr>

<h2>${device.deviceName}</h2>


<table>
    <tr>
        <td>Name</td><td>description</td><td>Last activity</td><td>Created</td>
    </tr>
        <tr>
            <td><c:out value="${device.deviceName} "></c:out></td>
            <td><c:out value="${device.description} "></c:out></td>
            <td><c:out value="${device.lastActivity} "></c:out></td>
            <td><c:out value="${device.createdAtDate} "></c:out></td>
            <td><a href="/devices/${device.id}/variables">back home</a> </td>
            <form action="/hello"><td><input type="submit" value="See yourself"></td></form>
        </tr>
</table>

</body>
</html>