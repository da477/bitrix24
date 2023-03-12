<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Bitrix 24 Time Management</title>
</head>
<h2>${time} current status: ${status}</h2>
<body>
<form method="POST" action="">
    <fieldset>
        <legend>Select operation:</legend>
        <div>
            <input type="radio" id="1" name="operation" value="0">
            <label for="1">Open day</label>
        </div>
        <div>
            <input type="radio" id="2" name="operation" value="1">
            <label for="2">Pause day</label>
        </div>
        <div>
            <input type="radio" id="3" name="operation" value="2">
            <label for="2">Close day</label>
        </div>
        <div>
            <input type="radio" id="4" name="operation" value="3">
            <label for="2">Current status</label>
        </div>
        <div>
            <input type="radio" id="5" name="operation" value="4">
            <label for="2">Auto command</label>
        </div>
    </fieldset>
    <br>
    <div>
        <button type="submit">Update</button>
    </div>
</form>
</body>
</html>



