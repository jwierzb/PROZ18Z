<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <!-- Static content -->
    <link rel="stylesheet" href="/resources/css/style.css">
    <script type="text/javascript" src="/resources/js/app.js"></script>

    <title>Create Device</title>
</head>
<body>
<h1>Create new Device</h1>
<hr>

    <form action="devices" method="post" class="demo-wrapper">
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
            </tr>
        </table>
    </form>

</body>
</html>