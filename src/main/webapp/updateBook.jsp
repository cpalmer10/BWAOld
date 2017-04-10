<%-- 
    Document   : updateBook
    Created on : Apr 2, 2017, 9:38:25 PM
    Author     : Palmer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="bookWebAppCSS.css" media="screen" /> 
        <title>Update Book</title>
    </head>
    <body>
        <h1>Update Book</h1>
        <form id="update_book" name="update_book" method="POST" action="BookController?action=update">
            <table>
                <tr>
                    <td>                        
                        <select id="bookUpdate" name="bookID"> 
                            <c:forEach var="b" items="${bookUpdate}">                                                               
                                <option value="${b.bookId}">${b.title}</option>                                                                     
                            </c:forEach>                                
                        </select>                            
                    </td>
                    <td>
                        <input type="text" id="title" name="title" placeholder="Title">
                    </td>
                </tr>
                <tr>
                    <td><input type="text" value="${b.isbn}" id="isbn1"></td>
                    <td><input type="text" id="isbn" name="isbn" placeholder="ISBN"></td>
                    
                    
                </tr>
                <tr>
                    <td><input type="text" value="${b.authorEntity.authorName}" id="authorName"></td>
                    <td><input type="text" id="authorName" placeholder="Author Name"</td>
                </tr>
                
                    <td>
                        <input type="submit" name="update" value="Update">
                    </td>
                                           
            </table>
        </form>                                               
        <br><br><a href="index.jsp">Back to Home</a>
    </body>
</html>
