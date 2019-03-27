<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<c:url value="/" var="contextPath" />

<tags:pageTemplate titulo="Usuarios">


<jsp:body>
<div class="container">
<h2><a href="/casadocodigo/usuarios/form">Novo Usuário</a></h2>
<h1>Lista de Usuarios</h1>
<p> ${sucesso} </p>
		<table class="table table-bordered table-striped table-hover">
			<tr>
				<th>Nome</th>
				<th>E-mail</th>
				<th>Roles</th>
				

			</tr>
			<c:forEach items="${usuarios }" var="usuario">
				<tr>
					<td>${usuario.nome }</td>
					<td>${usuario.email }</td>
					<td>${usuario.roles }</td>
					<td><a href="${s:mvcUrl('UC#role').arg(0, usuario.id).build() }">${usuario.id }</a></td>					
				</tr>
			</c:forEach>
		</table>
 
</div>
</jsp:body>

</tags:pageTemplate>