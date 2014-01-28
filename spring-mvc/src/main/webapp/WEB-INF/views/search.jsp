<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<layout:layout>

    <h2>Search</h2>


    <input id="search" type="text" placeholder="search" />

    <iframe src="${pageContext.request.contextPath}/solr/core1/select?q=*:*"></iframe>

</layout:layout>