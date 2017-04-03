<%-- 
    Document   : addBook
    Created on : Apr 2, 2017, 9:37:56 PM
    Author     : Palmer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Book</title>
    </head>
    <body>
        <h1>Add Book</h1>
        <form id="add_book" name="add_book" method="POST" action="BookController?action=add">
            Book Title:<input type="text" id="title" name="title">
            Book ISBN: <input type="text" id="isbn" name="isbn">
            Author Name: 
            <select id="authorName" name="authorName"> 
                <c:forEach var="a" items="${authors}">
                    <option value="${a.authorId}"><c:out value="${a.authorName}"/></option>                                                                  
                </c:forEach>                                
            </select>               
            <input type="submit" name="submit" value="Submit">
        </form>
        
        
        <br><a href="index.jsp">Back to Home</a>
    </body>
</html>