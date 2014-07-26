<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://sandbox.local/common.tld" prefix="common" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<layout:layout>
    <div class="row">
        <div class="span9">
            <h1>Account switch with Spring Security</h1>
            <p>Current user: <strong>${user.username}</strong></p>

            <h3>Your linked accounts</h3>
            <table class="table">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Username</th>
                    <th>#</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${user.linkedAccounts}" var="account" varStatus="i">
                <tr>
                    <td>${i.index +1}</td>
                    <td>${account}</td>
                    <td><a href="${pageContext.request.contextPath}/switch.do?j_switch_username=${account}">switch to</a></td>
                </tr>
                </c:forEach>
                </tbody>
            </table>

            <h3>All available accounts</h3>
            <table class="table">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Username</th>
                    <th>switchable</th>
                    <th>#</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${usernames}" var="account" varStatus="i">
                    <tr>
                        <td>${i.index +1}</td>
                        <td>${account}</td>
                        <td>
                            <c:forEach items="${user.linkedAccounts}" var="linkedAccount">
                                <c:if test="${linkedAccount eq account}">
                                    yes
                                </c:if>
                            </c:forEach>
                        </td>
                        <td><a href="${pageContext.request.contextPath}/switch.do?j_switch_username=${account}">try to switch</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</layout:layout>