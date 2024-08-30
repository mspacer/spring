<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
</head>
<body>
  <h1>Error Page</h1>
  <p>Application has encountered an error. Please contact support on ...</p>

    Failed URL: ${url}
    Exception:  ${exception.message}
        <c:forEach items="${exception.stackTrace}" var="ste">    ${ste}
    </c:forEach>

    <form action="/login" method="get">
        <input type="submit" name="To login">
    </form>
</body>
</html>