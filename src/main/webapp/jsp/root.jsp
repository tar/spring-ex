<%@ page trimDirectiveWhitespaces="true"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<title>Article</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<%@ include file="/jsp/api.jsp"%>
</head>
<body>
            <p>Current time from server is: ${time}</p>
            <p>Current root url is: ${root_url}</p>
            <p>security path is: <a href="${root_url}/admin">Secret path</a></p>
            <p>This application is an example spring webapp</p>
</body>
</html>