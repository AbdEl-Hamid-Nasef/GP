<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>NEWS Page</title>
    </head>
    <body>
            <h1>Hello World!</h1>
            <%
                RequestDispatcher rd = request.getRequestDispatcher("operationsServlet");                  
                rd.forward(request, response);
            %>
    </body>
</html>
