<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<layout:layout>
    <div class="row">
        <div class="span4">
            <input id="search" type="text" placeholder="search" autocomplete="off" />
        </div>
    </div>
    <div class="row">
        <div class="span12">
            <iframe id="search-result" src="${pageContext.request.contextPath}/solr/core1/xslt?q=*:*"></iframe>
        </div>
    </div>
</layout:layout>