<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/11/14 0014
  Time: 下午 9:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Save Product</title>
</head>
<br>
<h4>The product has bean saved.</h4>
<h5>Details:</h5>
Product Name: ${product.name}<br/>
Description: ${product.description}<br/>
Price: $${product.price}
<p>
    Following files are uploaded successfully.
</p>
<ol>
    <c:forEach items="${product.images}" var="image">
        <li>${image.originalFilename}</li>
        <img width="200" src="<c:url value="/image/"/>${image.originalFilename}" alt="还没出来"/>
    </c:forEach>
</ol>
</body>
</html>
