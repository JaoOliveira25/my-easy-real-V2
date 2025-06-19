<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <!DOCTYPE html>
        <html lang="pt-br">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="<c:url value='/assets/css/styleHome.css' />">
            <link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css' rel='stylesheet'>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.inputmask/5.0.9/jquery.inputmask.min.js"></script>
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
                        <button id="open-modal-button" onclick="openModalCadastrar()">
                            <i class="fa-solid fa-square-plus"></i>
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



                <div id="fade" class="hide"></div>
                <div id="modal" class="hide">
                    <div class="modal-header">
                        <h2 id="titleModal">Incluir Movimentação</h2>
                        <button id="close-modal">
                            <i class="fa-solid fa-circle-xmark"></i>
                        </button>
                    </div>
                    <form id="formMovimentacao" action="${pageContext.request.contextPath}/ServletFluxoCaixaController" method="post" >
                        <div class="modal-body">
                            <div class="newItem">

                                <div class="divType">
                                    <label for="type">Tipo</label>
                                    <select id="type" name="tipoMovimento" required>
                                        <option value="E">Entrada</option>
                                        <option value="S">Saída</option>
                                    </select>
                                </div>

                                <div class="divData">
                                    <label for="data">Data</label>
                                    <input type="text" id="data" placeholder="dd/mm/aaaa" name="dataMovimento" maxlength="10" required>
                                </div>
                                <div class="divAmount">
                                    <label for="amount">Valor</label>
                                    <input type="number" id="amount" name="valorMovimento" step="0.01" min="0" placeholder="Ex: 10.50" required>
                                </div>
                                <div class="divDesc">
                                    <label for="desc">Descrição</label>
                                    <input type="text" id="desc" name="descricao" required>
                                </div>

                                <input type="hidden" id="idMovimentacao" name="idMovimentacao">

                                

                                <button type="button" id="btnSalvar">Lançar</button>
                            </div>
                        </div>
                    </form>

                </div>

            </main>
        </body>

        </html>