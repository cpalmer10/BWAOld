<%-- 
    Document   : deleteBook
    Created on : Apr 2, 2017, 9:38:15 PM
    Author     : Palmer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Book</title>
    </head>
    <body>
        <h1>Delete Book</h1>
        <form id="delete_author" name="delete_book" method="POST" action="BookController?action=delete">
            <select id="bookID" name="bookID"> 
                <c:forEach var="b" items="${bookDelete}">
                    <option value="${b.bookId}">${b.title}</option>                
                </c:forEach>
            </select>
            <input type="submit" name="delete" value="Delete">                                                
        </form>                                        
        <br><a href="index.jsp">Back to Home</a>
    </body>
</html>

