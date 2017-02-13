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
        <h1>Pick an Administrative Task</h1>
        <ol>
            <li><a href="AuthorController?action=list">View all Authors</a></li>
            <li><a href="AuthorController?action=updateShow">Update Author</a></li>
            <li><a href="AuthorController?action=addShow">Add Author</a></li>
            <li><a href="AuthorController?action=deleteShow">Delete Author</a></li>            
            <li>Soon™</li>
        </ol>
        
    </body>
</html>
