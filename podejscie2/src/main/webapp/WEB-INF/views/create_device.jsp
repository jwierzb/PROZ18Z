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
<h1>Create device</h1>
<hr>

<div class="form">
    <form action="devices" method="post" >
        <table>
            <tr>
                <td>Enter device name</td>
                <td><input id="name" name="name"></td>
            </tr>
            <tr>
                <td>Enter enabled</td>
                <td><input id="enabled" name="enabled"></td>
            </tr>
            <tr>
                <td>Enter tags</td>
                <td><input id="tags" name="tags"></td>
            </tr>
            <tr>
                <td>Enter description</td>
                <td><input id="description" name="description"></td>
            </tr>
            <tr>
                <td><input type="submit" value="Submit"></td>
                <td><button>some funny button</button></td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>