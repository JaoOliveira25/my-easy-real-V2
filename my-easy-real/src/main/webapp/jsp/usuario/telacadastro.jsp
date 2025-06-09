<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="<c:url value='/assets/css/styleCadastro.css' />">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="<c:url value='/assets/js/scriptCadastro.js'/>" defer></script>
<title>Página Cadastro</title>
</head>


<body>
	<div class="container">
		<div class="card">
			<h1>Cadastra-se</h1>
			<div id="msgErrorCad"></div>
			<div id="msgSuccesCad"></div>

			<form id="formCadastro" action="<%=request.getContextPath()%>/ServletCadastro"
				method="post">
				<div class="label-float">
					<input type="text" id="nome" name="nome" placeholder='' required>
					<label for="nome" id="labelNome">Nome</label>
				</div>

				<div class="label-float">
					<input type="email" id="email" name="email" placeholder=" "
						required> <label for="email" id="labelEmail">Email</label>
				</div>

				<div class="label-float">
					<input type="password" id="senha" name="senha" placeholder=''
						required> <label for="senha" id="labelSenha">Senha</label>
					<i class="fa fa-eye" aria-hidden="true" id="olho1"></i>
				</div>

				<div class="label-float">
					<input type="password" id="confirmSenha" placeholder='' required>
					<label for="confirmSenha" id="labelConfSenha">Confirmar
						Senha</label> <i class="fa fa-eye" aria-hidden="true" id="olho2"></i>
				</div>

				<div class="justify-center">
					<button type="button" id="btnCadastrar">Cadastrar</button>
				</div>
			</form>


		</div>
	</div>

	    
    <div id="fade" class="hide">
        <div id="modal" class="hide">
            <div class="modal-header">
                <h2>Confirme seu cadastro!</h2>
                <button id="close-modal">fechar</button>
            </div>
    
            <div class="modal-body">
                <p id="modalMensagem"></p>
            </div>
    
        </div>
       
    </div>



</body>
</html>