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
                                Title: <option value="${b.bookId}">${b.title}</option>                                                                     
                            </c:forEach>                                
                        </select>                            
                    </td>
                    <td>
                        <input type="text" id="title" name="title">
                    </td>
                </tr>
                <tr>
                    <td>                                               
                        <input type="text" value="${b.isbn}" id="isbn">                                                                                                                                                                                                             
                    </td>
                    <td>
                        <input type="text" id="isbn" name="isbn">
                    </td>
                    
                    
                </tr>
                    <td>
                        <input type="submit" name="update" value="Update">
                    </td>
                                           
            </table>
        </form>                                               
        <br><br><a href="index.jsp">Back to Home</a>
    </body>
</html>
