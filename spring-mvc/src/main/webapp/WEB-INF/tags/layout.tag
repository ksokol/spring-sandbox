<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://sandbox.local/common.tld" prefix="common" %>

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
                <li><a href="${pageContext.request.contextPath}/home.do"><common:message code="nav.home" /></a></li>
                <li><a href="${pageContext.request.contextPath}/search.do"><common:message code="nav.solr.iframe" /></a></li>
                <li><a href="${pageContext.request.contextPath}/approval/confirm.do"><common:message code="nav.approval" /></a></li>
                <li><a target="_blank" href="${pageContext.request.contextPath}/solr/core1/select?q=*:*"><common:message code="nav.solr" /></a></li>
                <li><a href="${pageContext.request.contextPath}/j_spring_security_logout"><common:message code="nav.logout" /></a></li>
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