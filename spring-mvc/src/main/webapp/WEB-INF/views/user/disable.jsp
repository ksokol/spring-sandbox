<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<layout:layout>
    <table id="list" class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>Username</th>
            <th>is enabled</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.username}</td>
                <td>${user.enabled}</td>
                <td><a class="disable" href="#" data-username="${user.username}">disable and logout</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a href="javascript:window.location.reload()">reload page (and see whether you are still logged in)</a>

    <script>
        $(".disable").click(function(e) {
            e.preventDefault();
            var body = JSON.stringify({username: $(this).data("username") });
            $.ajax({type: "POST", url: "${pageContext.request.contextPath}/user/disable.do", data: body, contentType: 'application/json; charset=utf-8' });
        });
    </script>
</layout:layout>