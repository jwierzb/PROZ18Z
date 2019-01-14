<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <!-- Static content -->
    <link rel="stylesheet" href="/resources/css/style.css">
    <script type="text/javascript" src="/resources/js/app.js"></script>

    <title>Login screen</title>
</head>
<body>
<h1>Login screen</h1>
<hr>

    <form action="login" method="post" >
        <table class="demo-wrapper">
            <tr>
                <td>Enter Your name</td>
                <td><input id="name" name="name"></td>
            </tr>
            <tr>
                <td>Enter Your password</td>
                <td><input id="password" name="password"></td>
            </tr>
            <tr>
                <td><input type="submit" value="Submit"></td>
            </tr>
        </table>
    </form>

</body>
</html>