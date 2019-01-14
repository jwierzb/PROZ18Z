<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="/resources/css/style.css">
    <title>Device list</title>
</head>
<body>
<h1>Here are your Devices</h1>
<hr>


<table class="demo-wrapper">
    <tr>
        <td>Name</td><td>description</td><td>Last activity</td><td>Created</td>
    </tr>
<c:forEach items="${devices}" var="device">
    <tr>
        <td><c:out value="${device.deviceName} "></c:out></td>
        <td><c:out value="${device.description} "></c:out></td>
        <td><c:out value="${device.lastActivity.toLocaleString()} "></c:out></td>
        <td><c:out value="${device.createdAtDate.toLocaleString()} "></c:out></td>
        <td><a href="/devices/${device.id}">Device details</a> </td>
        <td><a href="/devices/${device.id}/variables">Device variables</a> </td>
    </tr>

</c:forEach>
</table>
<form action="/logout"><input type="submit" value="Logout"></form>

</body>
</html>