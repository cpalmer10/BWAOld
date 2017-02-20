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
        <form id="update_author" name="update_author" method="POST" action="AuthorController?action=update">
            <table>
                <tr>
                    <td>
                        <select id="authorUpdate" name="authorID"> 
                            <c:forEach var="a" items="${authorUpdate}">
                                <option value="${a.authorId}">${a.authorName}</option>                
                            </c:forEach>                                
                        </select>                       
                    </td>
                    <td>
                        <input type="text" id="author_name" name="author_name">
                    </td>
                    <td>
                        <input type="submit" name="update" value="Update">
                    </td>
                </tr>
                
<!--                <tr>
                    <td>ID</td>
                    <td>
                        <input type="text" id="author_id" name="author_id" value="<c:out value="${a.authorId}"/>">
                    </td>                
                </tr>
-->
            </table>
        </form>                                               
        <br><br><a href="index.jsp">Back to Home</a>
    </body>
</html>
