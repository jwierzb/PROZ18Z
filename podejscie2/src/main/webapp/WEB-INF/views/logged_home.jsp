<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <!-- Static content -->
    <link rel="stylesheet" href="/resources/css/style.css">
    <script type="text/javascript" src="/resources/js/app.js"></script>

    <title>MAIN MENU</title>
</head>
<body>
<h1>Hello ${name}!</h1>
<hr>

<div class="form">
    <table>
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
    </table>
</div>

<c:forEach var = "i" begin = "1" end = "5">
Item <c:out value = "${i}"/><p>

    </c:forEach>

</body>
</html>