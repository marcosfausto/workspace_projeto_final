<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<c:url value="/" var="contextPath" />

<tags:pageTemplate titulo="${usuario.nome}">


<jsp:body>
<div class="container">
<h1>Cadastro de permissão para ${usuario.nome}</h1>

 <form:form action="${s:mvcUrl('UC#atualizarRoles').build() }" method="post" modelAttribute="usuario">
            <div class="form-group">
                <form:label path="roles">Permissões: </form:label>
                <form:checkboxes path="roles"  items="${roles}" cssClass="form-control"/>
                <form:errors path="roles" />
                
            </div>
            <form:hidden path="id" value="${usuario.id}"/>

            <button type="submit" class="btn btn-primary">Atualizar</button>
        </form:form> 

 
</div>
</jsp:body>

</tags:pageTemplate>