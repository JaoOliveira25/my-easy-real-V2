<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <!DOCTYPE html>
        <html lang="pt-br">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="<c:url value='/assets/css/styleHome.css' />">
            <link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css' rel='stylesheet'>

            <script src="<c:url value='/assets/js/scriptHome.js' />" defer></script>
            <title>Home</title>
        </head>

        <body>
            <main>

                <div class="profile-container">
                    <div class="profile-photo">
                        <img src="${pageContext.request.contextPath}/assets/img/perfil.png" alt="">
                    </div>

                    <div class="campo-nome">
                        <span class="profile-name">Seja bem vindo ao My Rasy Real</span>
                    </div>

                    <div class="editarPerfil">
                        <a href="${pageContext.request.contextPath}/jsp/principal/editarCadastro.jsp"><i
                                class="fa-solid fa-gear gear"></i></a>
                    </div>
                </div>






                <div class="resume-container">

                    <h4>Saldo atual </h4>
                    <span class="total">R$ 0.00</span>

                    <div class="inc-exp-container">
                        <div>
                            <h4>Receitas </h4>
                            <span class="total incomes">R$ 0.00</span>
                        </div>
                        <div>
                            <h4>Despesas </h4>
                            <span class="total expenses">R$ 0.00</span>
                        </div>
                    </div>

                </div>


                <div class="tabela-container">
                    <!--Estou planejando aqui colocar no lugar do título um filtro de dadas-->
                    <div class="header">
                        <span>Fluxo de Caixa</span>
                        <button onclick="openModal()" id="new">
                            <i class="fa-solid fa-plus"></i>
                        </button>
                    </div>

                    <div id="tabelaFluxoCaixa" class="divTable"><!--Aqui vai ficar o as colunas de cada valor-->
                        <table>
                            <thead><!--Essa tag agrupa o cabeçalho de uma tabela-->
                                <tr>
                                    <th>Data</th>
                                    <th>Descrição</th>
                                    <th>Valor</th>
                                    <th class="acao">Editar</th>
                                    <th class="acao">Excluir</th>
                                </tr>
                            </thead>

                            <tbody>

                            </tbody>
                        </table>
                    </div>

                </div>



                <div id="fade" class="hide">
                    <div id="modal" class="hide">
                        <div class="modal-header">
                            <h2>Confirme seu cadastro</h2>
                            <button id="close-modal">fechar</button>
                        </div>

                        <div class="modal-body">
                            <p>Estamos quase lá enviamos no seu email <strong>em****@hotmail.com</strong> o link para
                                confirmação do seu cadastro não esqueça de olhar sua caixa de span! </p>
                        </div>
                    </div>
                </div>


            </main>
        </body>

        </html>