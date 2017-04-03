<%-- 
    Document   : index
    Created on : Feb 7, 2017, 10:20:22 PM
    Author     : Palmer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Web App</title>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <h1>Pick an Administrative Task</h1>
        <ol>
            <li><a href="AuthorController?action=list">View all Authors</a></li>
            <li><a href="AuthorController?action=updateShow">Update Author</a></li>
            <li><a href="AuthorController?action=addShow">Add Author</a></li>
            <li><a href="AuthorController?action=deleteShow">Delete Author</a></li>            
        </ol>
        <br>
        <br>
        <ol>
            <li><a href="BookController?action=list">View all Books</a></li>
            <li><a href="BookController?action=updateShow">Update Book</a></li>
            <li><a href="BookController?action=addShow">Add Book</a></li>
            <li><a href="BookController?action=deleteShow">Delete Book</a></li>
        </ol>
        
    </body>
</html>
