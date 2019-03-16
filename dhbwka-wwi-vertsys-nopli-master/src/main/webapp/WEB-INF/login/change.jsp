<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Daten ändern
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/dashboard.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>
        
        <div class="menuitem">
            <a href="<c:url value="/app/tierarten/list/"/>">Liste</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/tierarten/tierart/new/"/>">Tierart anlegen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/tierarten/spezies/"/>">Spezies bearbeiten</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">
        
        <div>
            <form method="POST">
                <input type="text" name="change_vorname" placeholder="${current_user.vorname}">
                <input type="text" name="change_nachname" placeholder="${current_user.nachname}">
                <input type="text" name="change_username" placeholder="${current_user.username}">
            
                <button type="submit">Send</button>
            </form>
        </div>
    </jsp:attribute>
</template:base>