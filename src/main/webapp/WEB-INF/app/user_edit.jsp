<%-- 
    Document   : user_edit
    Created on : 01.03.2018, 10:38:46
    Author     : thoma
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<template:base>
    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                    <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">

                    <%-- Eingabefelder --%>
                    <label for="login_username">
                        Benutzername:
                        <span class="required">*</span>
                    </label>
                    <%-- username kann nicht bearbeitet werden --%>
                    <div class="side-by-side">
                        <input type="text" name="login_username" value="${benutzer.username}" readonly="readonly">
                    </div>
                    
                    <label for="vorname">
                        Vorname:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="login_vorname" value="${benutzer.vorname}">
                    </div>
                    
                    <label for="nachname">
                        Nachname:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="login_nachname" value="${benutzer.nachname}">
                    </div>
                    
                    <label for="email">
                        E-Mail-Adresse:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="login_email" value="${benutzer.email}">
                    </div>
                    
                    <label for="telefonnummer">
                        Telefonnummer:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="login_telefonnummer" value="${benutzer.telefonnummer}">
                    </div>
                      
                    <label for="adresse">
                        Adresse:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="login_adresse" value="${benutzer.adresse}">
                    </div>
                    
                    <label for="stadt">
                        Stadt:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="login_stadt" value="${benutzer.stadt}">
                    </div>
                    
                    <label for="postleitzahl">
                        PLZ:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="login_postleitzahl" value="${benutzer.postleitzahl}">
                    </div>
                    
                    <%-- Button zum Abschicken --%>
                    <div class="side-by-side">
                        <button class="icon-pencil" type="submit">
                            Speichern
                        </button>
                    </div>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty login_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${login_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>

