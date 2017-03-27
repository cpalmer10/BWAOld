<%-- 
    Document   : index
    Created on : Feb 7, 2017, 9:47:16 PM
    Author     : Palmer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="bookWebAppCSS.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css">                
        <title>Author List</title>
    </head>
    <body>
        
        <h1 id="pageHeader">Author List</h1>
        <table id="authorTable">
            
            <tr id="tableHeaders">
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
    
    <script type=text/javascript" src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $('#authorTable').DataTable();
        }); 
    </script>
</html>
