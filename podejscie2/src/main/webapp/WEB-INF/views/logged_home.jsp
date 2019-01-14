<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <!-- Static content -->
    <link rel="stylesheet" href="/resources/css/style.css">
    <script type="text/javascript" src="/resources/js/app.js"></script>

    <title>Successfully logged in</title>
</head>
<body>
<h1>Hello ${name}!</h1>
<hr>

    <table class="demo-wrapper">
        <form action="devices" method="get">
            <tr>
                <td><input type="submit" value="See your devices!"></td>
            </tr>
        </form>
        <form action="createdevice">
            <tr>
                <td><input type="submit" value="Create new device"></td>
            </tr>
        </form>
            <tr>
                <td>
                    <form action="/logout"><input type="submit" value="Logout"></form>
                </td>
            </tr>

    </table>


</body>
</html>