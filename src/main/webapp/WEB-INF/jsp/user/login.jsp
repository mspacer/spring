<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
    <form action="/login" method="post">
        <label for="userName">Имя пользователя:</label>
        <input name="userName" id="userName" type="text"/>
        <br/>
        <label for="password">Пароль:</label>
        <input name="password" id="password" type="password"/>
        <br/>
        <label for="sometext">Просто текст:</label>
        <input name="sometext" id="sometext" type="text"/>
        <br>
        <input type="submit" name="Вход">
    </form>
</body>
</html>