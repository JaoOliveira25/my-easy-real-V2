<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="<c:url value='/assets/css/styleLogin.css' />">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="assets/js/scriptLogin.js" defer></script>
<title>Página Login</title>
</head>
<body>
	<div class="container">
		<div class="card">
			<h1>Entrar</h1>
			<div id="msg" style = "display: ${visivel};">${msg}</div>

			<form action="<%=request.getContextPath()%>/ServletLogin" method="post">
				<input type="hidden" value="<%= request.getParameter("url")%>"
			name="url">
				<div class="label-float">
					<input type="email" id="email" placeholder='' name="email" required> <label
						id="email" for="email">E-mail</label>
				</div>

				<div class="label-float">
					<input type="password" id="senha" placeholder='' name="senha" required>
					<label id="passwordLabel" for="senha">Senha</label> <i
						class="fa fa-eye" aria-hidden="true"></i>
				</div>

				<div class="justify-center">
					<button onclick="entrar()">Entrar</button>
				</div>

			</form>
			<div class="justify-center">
				<hr>
			</div>

			<p>
				Não tem uma conta ainda? <a
					href="http://localhost:8080/my-easy-real/jsp/usuario/telacadastro.jsp">Cadastre-se</a>
			</p>


		</div>
	</div>
</body>
</html>