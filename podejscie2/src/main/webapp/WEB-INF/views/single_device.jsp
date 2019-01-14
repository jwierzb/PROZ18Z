<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="/resources/css/style.css">
    <title>Device</title>
</head>
<body>
<h1>Device</h1>
<hr>

<h2>More details about ${device.deviceName}</h2>


<table class="demo-wrapper">
    <tr>
        <td>Name</td><td>description</td><td>Last activity</td><td>Created</td>
    </tr>
        <tr>
            <td><c:out value="${device.deviceName} "></c:out></td>
            <td><c:out value="${device.description} "></c:out></td>
            <td><c:out value="${device.lastActivity} "></c:out></td>
            <td><c:out value="${device.createdAtDate} "></c:out></td>
            <td><a href="/devices/${device.id}/variables">See variables</a> </td>
        </tr>
</table>

<form action="/logout"><input type="submit" value="Logout"></form>
</body>
</html>