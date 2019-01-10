<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <!-- Static content -->
    <link rel="stylesheet" href="/resources/static/css/style.css">
    <script type="text/javascript" src="/resources/js/app.js"></script>

    <title>MAIN MENU</title>
</head>
<body>
<h1>Welcome screen</h1>
<hr>


        <table>
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

<c:forEach var = "i" begin = "1" end = "5">
    Item <c:out value = "${i}"/><p>

</c:forEach>

    <c:forTokens var = "name" items= "Ala,Ma,Kota" delims=",">
    Item <c:out value = "${name}"/><p>

    </c:forTokens>

</body>
</html>