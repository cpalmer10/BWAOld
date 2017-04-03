<%-- 
    Document   : bookList
    Created on : Apr 2, 2017, 9:38:05 PM
    Author     : Palmer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="bookWebAppCSS.css" media="screen" />                   
        <title>Book List</title>
    </head>
    <body>
        
        <h1 id="pageHeader">Book List</h1>
        <table width="700" border="1" cellspacing="0" cellpadding="4">
            
            <tr id="tableHeaders">
                <th align="left">ID</th>
                <th align="left">Book Title</th>
                <th align="left">Book ISBN</th>
                <th align="left">Author Name</th>
            </tr>
            
        <c:forEach var="b" items="${books}" varStatus="rowCount">
            <tr>
                <td align="left">${b.bookId}</td>
                <td align="left">${b.title}</td>   
                <td align="left">${b.isbn}</td>
                <td align="left">${b.authorEntity.authorName}</td>
            </tr>            
        </c:forEach>                        
        </table>       
        <br><a href="index.jsp">Back to Home</a>
    </body>       
</html>