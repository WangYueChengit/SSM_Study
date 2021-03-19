<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />

</head>
<body>
	<div class="container">
		<form action="${ pageContext.request.contextPath }/user/check" method="POST">
    		<input type="text" name="checktext">
    		<input type="submit" value="验证">
    			<p>${checkmsg}</p>
		</form>
	</div>
</body>
</html>