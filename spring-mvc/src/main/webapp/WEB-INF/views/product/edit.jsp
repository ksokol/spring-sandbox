<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<layout:layout>
    <c:if test="${fn:length(flash) > 0}">
        <div class="alert alert-success">
            ${flash}
        </div>
    </c:if>

    <c:if test="${product.id eq 0}">
        <h2>Add product</h2>
    </c:if>
    <c:if test="${product.id > 0}">
        <h2>Edit product</h2>
    </c:if>

    <sf:form modelAttribute="product" action="edit.do" method="post">
        <sf:input path="id" type="hidden"/>

        <div class="row">
            <div class="span2"><span>Name</span></div>
            <div class="span4"><sf:input path="name"/></div>
            <div class="span4"><sf:errors cssClass="error" path="name"/></div>
        </div>

        <div class="row">
            <div class="span2"><span>Description</span></div>
            <div class="span4"><sf:textarea path="description" rows="5" cols="30"/></div>
            <div class="span4"><sf:errors cssClass="error" path="description"/></div>
        </div>

        <div class="row">
            <div class="span2"><span>Color</span></div>
            <div class="span4">
                <sf:select path="color">
                                       <sf:option value="RED" />
                                       <sf:option value="BLACK" />
                                       <sf:option value="WHITE" />
                </sf:select>
            </div>
            <div class="span4"><sf:errors cssClass="error" path="color"/></div>
        </div>

        <input class="btn btn-primary" type="submit" value="save">

    </sf:form>

    <c:if test="${product.id > 0}">
        <form id="deleteForm" action="delete" method="post">
            <input type="hidden" name="id" value="${product.id}">

            <button id="deleteButton" data-toggle="modal" href="#deleteModal" class="btn btn-danger">delete</button>
        </form>
    </c:if>


    <div class="modal hide" id="deleteModal">
        <div class="modal-header">
            <button class="close" data-dismiss="modal">×</button>
            <h3>Delete</h3>
        </div>
        <div class="modal-body">
            <p>Delete <b>${product.name}</b>?</p>
        </div>
        <div class="modal-footer">
            <a id="nein" href="#" class="btn btn-primary">no</a>
            <a id="ja" href="#" class="btn btn-danger">yes</a>
        </div>
    </div>
</layout:layout>