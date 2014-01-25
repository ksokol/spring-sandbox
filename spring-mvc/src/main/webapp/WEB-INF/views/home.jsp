<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<layout:layout>
    <c:if test="${fn:length(flash) > 0}">
        <div class="alert alert-success">
            ${flash}
        </div>
    </c:if>

    <h2>Products</h2>

    <a href="product/edit.do">add product</a>

    <table id="list" class="table table-bordered table-striped tablesorter">
        <thead>
            <tr>
                <th></th>
                <th>Name</th>
                <th>Description</th>
                <th>Created</th>
                <th>Updated</th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${list}" var="elem">
                <tr>
                    <td><a href="product/edit.do?id=${elem.id}">edit</a></td>
                    <td>${elem.name}</td>
                    <td>${elem.description}</td>
                    <td>${elem.createdAt}</td>
                    <td>${elem.updatedAt}</td>
                    <td><a href="${pageContext.request.contextPath}/rest/product/${elem.id}.json" target="_blank">json</a></td>
                    <td><a href="${pageContext.request.contextPath}/rest/product/${elem.id}.xml" target="_blank">xml</a></td>
                    <td><a href="${pageContext.request.contextPath}/rest/product/${elem.id}.html" target="_blank">html/xslt</a></td>
                    <td><a href="${pageContext.request.contextPath}/rest/product/jsp/${elem.id}.html" target="_blank">html/jsp</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</layout:layout>