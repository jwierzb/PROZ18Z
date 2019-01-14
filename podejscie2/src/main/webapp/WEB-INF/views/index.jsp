<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <!-- Static content -->
    <link rel="stylesheet" href="/resources/css/style.css">

    <title>MAIN MENU</title>
</head>
<body>
<h1>${message}</h1>
<hr>

        <table class="demo-wrapper">
            <form action="/register" method="get">
                <tr>
                    <td><input type="submit" value="Register"></td>
                </tr>
            </form>
            <form action="/login" method="get">
                <tr>
                    <td><input type="submit" value="Login"></td>
                </tr>
            </form>
        </table>
</body>
</html>