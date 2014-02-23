<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<layout:layout>

    <s:hasBindErrors name="country">
        <div class="alert">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <c:forEach items="${requestScope['org.springframework.validation.BindingResult.country'].allErrors}" var="error">
                <p>${error}</p>
            </c:forEach>
        </div>
    </s:hasBindErrors>

    <c:if test="${fn:length(flash) > 0}">
        <div class="alert alert-success">
            ${flash}
        </div>
    </c:if>

    <c:if test="${country.id eq 0}">
        <h2>Add product</h2>
    </c:if>
    <c:if test="${country.id > 0}">
        <h2>Edit product</h2>
    </c:if>

    <form action="edit.do" method="post">
        <div class="row country" data-idx="0">
            <div class="span2"><span class="title">locales</span></div>
            <div class="span2"><input name="locales" value="de_CH" /></div>
            <div class="span2"><button type="button" class="close">&times;</button></div>
        </div>

        <div class="row country" data-idx="1">
            <div class="span2"><span class="title">locales</span></div>
            <div class="span2"><input name="locales" value="fr_CH" /></div>
            <div class="span2"><button type="button" class="close">&times;</button></div>
        </div>

        <div class="row country" data-idx="2">
            <div class="span2"><span class="title">locales</span></div>
            <div class="span2"><input name="locales" value="it_CH" /></div>
            <div class="span2"><button type="button" class="close">&times;</button></div>
        </div>

        <input class="btn btn-primary" type="submit" value="save">
        <button class="btn" id="append">append country</button>
    </form>

    <c:if test="${country.id > 0}">
        <form id="deleteForm" action="delete" method="post">
            <input type="hidden" name="id" value="${country.id}">

            <button id="deleteButton" data-toggle="modal" href="#deleteModal" class="btn btn-danger">delete</button>
        </form>
    </c:if>


    <div class="modal hide" id="deleteModal">
        <div class="modal-header">
            <button class="close" data-dismiss="modal">×</button>
            <h3>Delete</h3>
        </div>
        <div class="modal-body">
            <p>Delete <b>${country.name}</b>?</p>
        </div>
        <div class="modal-footer">
            <a id="nein" href="#" class="btn btn-primary">no</a>
            <a id="ja" href="#" class="btn btn-danger">yes</a>
        </div>
    </div>
</layout:layout>