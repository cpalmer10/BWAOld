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
        <title>Delete Author</title>
    </head>
    <body>
        <h1>Delete Author</h1>
        <form id="delete_author" name="delete_author" method="POST" action="AuthorController?action=delete">
            <select id="authorID" name="authorID"> 
                <c:forEach var="a" items="${authorDelete}">
                    <option value="${a.authorId}">${a.authorName}</option>                
                </c:forEach>
            </select>
            <input type="submit" name="delete" value="Delete">
            
            
            <!--
            <br><br> <h2>OR (BY ID)</h2>                       
           <select> 
                <c:forEach var="a" items="${authorDelete}">
                    <option value="${a.authorId}">${a.authorId}</option>                
                </c:forEach>
            </select>
            <input type="submit" name="delete" value="Delete">

            <br><br> <h2>OR (BY DATE ADDED)</h2>
            
            <select> 
                <c:forEach var="a" items="${authorDelete}">
                    <option value="${a.authorId}">
                        <fmt:formatDate pattern="M/d/yyyy" value="${a.dateAdded}"></fmt:formatDate>
                    </option>                
                </c:forEach>
            </select>
            <input type="submit" name="delete" value="Delete">
            -->
            
        </form>
        
        
        
        
        
        <br><a href="index.jsp">Back to Home</a>
    </body>
</html>
