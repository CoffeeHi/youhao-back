<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/11/13 0013
  Time: 下午 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Form</title>
</head>
<body>
<form:form commandName="product" action="/admin/product_save" method="post" enctype="multipart/form-data">
    <fieldset>
        <legend>Add a product</legend>
        <p>
            <label for="name">Product Name:</label>
            <form:input path="name" id="name" cssErrorClass="error"/>
        <p>
            <label for="description">Description:</label>
            <form:input path="description" id="description" cssErrorClass="error"/>
        </p>
        <p>
            <label for="images">Product Images:</label>
            <input type="file" id="images" name="images" multiple  value="上传图片"/>
        </p>
        <p>
            <form id="reset" type="reset" tabindex="4"/>
            <input id="submit" type="submit" tabindex="5" value="Add Product"/>
        </p>
    </fieldset>
</form:form>
</body>
</html>
