<%-- 
    Document   : index
    Created on : Feb 7, 2017, 9:47:16 PM
    Author     : Palmer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Author List</title>
    </head>
    <body>
        <h1>Author List</h1>
        <table>
            <tr>
                <th align="left">ID</th>
                <th align="left">Author Name</th>
                <th align="left">Date Added</th>               
            </tr>
            
        <c:forEach var="a" items="${authors}" varStatus="rowCount">
            <tr>
                <td align="left">${a.authorId}</td>
                <td align="left"><c:out value="${a.authorName}"/></td>
                <td align="right">
                    <fmt:formatDate pattern="M/d/yyyy" value="${a.dateAdded}"></fmt:formatDate>
                </td>                                
            </tr>            
        </c:forEach>                        
        </table>
        <br><a href="index.jsp">Back to Home</a>
        
    </body>
</html>
