<%-- 
    Document   : updateAuthors
    Created on : Feb 8, 2017, 4:31:20 PM
    Author     : Palmer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Author</title>
    </head>
    <body>
        <h1>Update Author</h1>
        
        <select> 
            <c:forEach var="a" items="${authorUpdate}">
                <option value="${a.authorName}">${a.authorName}</option>                
            </c:forEach>
        </select>   
        
        <br><a href="index.jsp">Back to Home</a>
    </body>
</html>
