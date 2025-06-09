<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <!DOCTYPE html>
        <html lang="pt-br">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="<c:url value='/assets/css/styleEditCad.css' />">
            <link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css' rel='stylesheet'>
            <link rel="stylesheet"
                href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
            <script src="${pageContext.request.contextPath}/assets/js/scriptEditCad.js" defer></script>
            <title>Editar Cadastro</title>
        </head>

        <body>
            <main>

                <div class="profile-container">
                    <div class="profile-photo">
                        <img src="${pageContext.request.contextPath}/assets/img/profile.png" alt="">
                    </div>


                </div>

                <div id="menu-container">
                    <div class="option-accordion">
                        <button class="accordion-header">
                            <span>EDITAR DADOS</span>
                            <i class="fa-regular fa-user"></i>
                        </button>

                        <div class="accordion-body.active">
                            <div class="container">
                                <form id="formEditCadastro" action="<%=request.getContextPath()%>/ServletUsuarioController"
                                method="post">
                                <div class="label-float">
                                    <input type="text" id="nome" name="nome" placeholder='' required>
                                    <label for="nome" id="labelNome">Nome</label>
                                </div>

                                <div class="label-float">
                                    <input type="email" id="email" name="email" placeholder=" " required> <label
                                        for="email" id="labelEmail">Email</label>
                                </div>

                                <div class="label-float">
                                    <input type="password" id="senha" name="senha" placeholder='' required> <label
                                        for="senha" id="labelSenha">Senha</label>
                                    <i class="fa fa-eye" aria-hidden="true" id="olho1"></i>
                                </div>

                                <div class="label-float">
                                    <input type="password" id="confirmSenha" placeholder='' required>
                                    <label for="confirmSenha" id="labelConfSenha">Confirmar
                                        Senha</label> <i class="fa fa-eye" aria-hidden="true" id="olho2"></i>
                                </div>

                                <div class="justify-center">
                                    <button type="button" id="btnCadastrar">Editar</button>
                                </div>
                            </form>
                        </div>
                            
                        </div>
                    </div>

                    <div class="option-accordion">

                        <button class="accordion-header">
                            <span>EXCLUIR CADASTRO</span>
                            <i class="fa-regular fa-user"></i>
                        </button>

                        <div class="accordion-body">
                            <p>
                                Ao deletar sua conta seus dados serão totalmente deletados do nosso banco de dados não sendo possivél recupera-los você está certo que deseja fazer isso ?
                            </p>
                        </div>
                    </div>


                </div>



            </main>
        </body>

        </html>