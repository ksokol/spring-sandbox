<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE HTML>
<html>
    <head>
        <title>Spring Sandbox</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css">
    </head>
    <body>
        <div class="navbar">
          <div class="navbar-inner">
            <div class="container">
              <ul class="nav">
                <security:authorize access="isAuthenticated()">
                <li><a href="${pageContext.request.contextPath}/home.do">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/search.do">Search (iframe)</a></li>
                <li><a target="_blank" href="${pageContext.request.contextPath}/solr/core1/select?q=*:*">Search (Solr)</a></li>
                <li><a href="${pageContext.request.contextPath}/j_spring_security_logout">Logout</a></li>
                </security:authorize>
              </ul>
            </div>
          </div>
        </div>

        <div class="container">
            <jsp:doBody />
        </div>

        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.tablesorter.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/script.js"></script>
    </body>
</html>