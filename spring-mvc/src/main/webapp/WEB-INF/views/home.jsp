<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://sandbox.local/common.tld" prefix="common" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<layout:layout>
    <c:if test="${fn:length(flash) > 0}">
        <div class="alert alert-success">
            <common:message code="flash.${flash}" />
        </div>
    </c:if>

    <c:if test="${fn:contains(param, 'messagekeys')}">
        <common:message code="messagekeys.${param.messagekeys}" arguments="${param.messagekeys}" /><br />
    </c:if>

    <c:choose>
        <c:when test="${param.messagekeys eq 'enabled'}">
            <a href="?messagekeys=disabled"><common:message code="messagekeys.off" /></a><br />
        </c:when>
        <c:otherwise>
            <a href="?messagekeys=enabled"><common:message code="messagekeys.on" /></a><br />
        </c:otherwise>
    </c:choose>
    <br />
    <h2><common:message code="title" /></h2>

    <a href="country/edit.do"><common:message code="add.country" /></a><br />
    <a href="product/edit.do"><common:message code="add.product" /></a>

    <table id="list" class="table table-bordered table-striped tablesorter">
        <thead>
            <tr>
                <th></th>
                <th><common:message code="table.name" /></th>
                <th><common:message code="table.description" /></th>
                <th><common:message code="table.created" /></th>
                <th><common:message code="table.updated" /></th>
                <th><common:message code="table.createdBy" /></th>
                <th><common:message code="table.color" /></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${list}" var="elem">
                <tr>
                    <td><a href="product/edit.do?id=${elem.id}"><common:message code="table.edit" /></a></td>
                    <td>${elem.name}</td>
                    <td>${elem.description}</td>
                    <td>${elem.createdAt}</td>
                    <td>${elem.updatedAt}</td>
                    <td>${elem.createdBy}</td>
                    <td>${elem.color}</td>
                    <td><a href="${pageContext.request.contextPath}/rest/product/${elem.id}.json" target="_blank">json</a></td>
                    <td><a href="${pageContext.request.contextPath}/rest/product/${elem.id}.xml" target="_blank">xml</a></td>
                    <td><a href="${pageContext.request.contextPath}/rest/product/${elem.id}.html" target="_blank">html/xslt</a></td>
                    <td><a href="${pageContext.request.contextPath}/rest/product/jsp/${elem.id}.html" target="_blank">html/jsp</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</layout:layout>