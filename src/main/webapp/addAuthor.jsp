<%-- 
    Document   : addAuthor
    Created on : Feb 8, 2017, 4:39:33 PM
    Author     : Palmer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Author</title>
    </head>
    <body>
        <h1>Add Author</h1>
        <form id="add_author" name="add_author" method="POST" action="AuthorController?action=add">
            Author Name:<input type="text" id="author_name" name="author_name">
            <input type="submit" name="submit" value="Submit">
        </form>
        
        <br><a href="index.jsp">Back to Home</a>
    </body>
</html>
