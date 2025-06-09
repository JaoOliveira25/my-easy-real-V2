<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="<c:url value='/assets/css/styleConfirmCad.css' />">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="assets/js/scriptConfirmacaoLogin.js" defer></script>
<title>Página De Confirmação</title>
</head>
<body>
	   <div id="fade" class="hide">
        <div id="modal" class="hide">
            <div class="modal-header">
                <button id="close-modal">fechar</button>
            </div>
    
            <div class="modal-body">
            
                <p id="modalMensagem">${msg}</p>
                <div>
					<button id="logar">logar</button>                
                </div>
            </div>
    
        </div>
       
    </div>
	
</body>
</html>