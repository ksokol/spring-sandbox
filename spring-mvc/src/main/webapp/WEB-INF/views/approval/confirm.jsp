<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<layout:layout>
    <h2>Enter your username/password to approve this request</h2>

     <form class="pull-left" method="POST">
        <div class="row">
   			<div class="span2"> <input type="text" name="username" placeholder="Username"></div>
    	</div>

        <div class="row">
   			<div class="span2"> <input type="text" name="password" placeholder="Password"></div>
    	</div>

        <div class="row">
            <div class="span2"> <input type="text" name="itemId" placeholder="Item id (optional)"></div>
        </div>

        <input class="btn btn-primary" type="submit" value="login">
     </form>
</layout:layout>
