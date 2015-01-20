<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>
    <head>
        <title>Spring Sandbox</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css">

        <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/1.7.2/jquery.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/noty/2.2.2/jquery.noty.packaged.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.tablesorter.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/script.js"></script>
    </head>
    <body>
        <div class="navbar">
          <div class="navbar-inner">
            <div class="container">
              <ul class="nav">
                <security:authorize access="isAuthenticated()">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <security:authentication property="principal.username" />
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="${pageContext.request.contextPath}/j_spring_security_logout">Logout</a></li>
                        </ul>
                    </li>
                </security:authorize>
              </ul>
            </div>
          </div>
        </div>

        <div class="container">
            <jsp:doBody />
        </div>

    </body>
</html>