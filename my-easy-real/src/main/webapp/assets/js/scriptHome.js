// ==========================
// Variáveis Globais
// ==========================
let dataAtual = new Date();
let mesAtual = dataAtual.getMonth();
let ano = dataAtual.getFullYear();
let urlAction = document.querySelector('#formMovimentacao').action;

const closeModalButton = document.querySelector("#close-modal");
const modal = document.querySelector("#modal");
const fade = document.querySelector("#fade");

// ==========================
// Eventos iniciais (DOMContentLoaded)
// ==========================
document.addEventListener("DOMContentLoaded", () => {
  carregarMovimentacoes();
  $("#data").inputmask("99/99/9999");
});

// ==========================
// Controle de Modal
// ==========================
const toggleModal = () => {
  [modal, fade].forEach((el) => el.classList.toggle("hide"));
};

[closeModalButton, fade].forEach((el) => {
  el.addEventListener("click", () => toggleModal());
});

// ==========================
// Máscara e Limitação de Casas Decimais
// ==========================
document.getElementById("amount").addEventListener("input", (event) => {
  const inputAmount = event.target.value;
  if (inputAmount.includes(".")) {
    const partes = inputAmount.split(".");
    if (partes[1].length > 2) {
      event.target.value = partes[0] + "." + partes[1].substring(0, 2);
    }
  }
});

// ==========================
// Carregar Movimentações
// ==========================
async function carregarMovimentacoes() {
  try {
    const url = urlAction;
    const params = new URLSearchParams({
      mes: mesAtual,
      ano: ano,
      acao: 'carregarFluxoCaixa'
    }).toString();

    const response = await fetch(`${url}?${params}`, { method: 'GET' });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const json = await response.json();
    const tbody = document.querySelector('#tabelaFluxoCaixa tbody');
    tbody.innerHTML = '';

    json.forEach(item => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td>${item.dataMovimento.split('-').reverse().join('/').slice(0, 5)}</td>
        <td>${item.descricao}</td>
        <td>R$ ${Number(item.valorMovimento).toFixed(2)}</td>
        <td style="text-align: center;">
          <button data-id="${item.id}" onclick="openModalEditar(this)" id="openModalEdit" class="icon-button">
            <i class="fa-solid fa-pen-to-square"></i>
          </button>
        </td>
        <td style="text-align: center;">
          <button data-id="${item.id}" onclick="excluirMovimentacao(this)" class="icon-button">
            <i class="fa-solid fa-trash-can"></i>
          </button>
        </td>`;
      tbody.appendChild(row);
    });

    calcularTotaisTabela();

  } catch (error) {
    alert('Erro: ' + error.message);
    console.error(error);
  }
}

// ==========================
// Calcular Totais da Tabela
// ==========================
function calcularTotaisTabela() {
  let totalReceitas = 0;
  let totalDespesas = 0;
  const linhas = document.querySelectorAll("#tabelaFluxoCaixa tbody tr");

  linhas.forEach((linha) => {
    const valorTexto = linha.children[2].textContent.replace("R$", "").trim();
    const valor = parseFloat(valorTexto);

    if (!isNaN(valor)) {
      if (valor > 0) {
        totalReceitas += valor;
      } else if (valor < 0) {
        totalDespesas += valor;
      }
    }
  });

  document.querySelector(".total.incomes").textContent = `R$ ${totalReceitas.toFixed(2)}`;
  document.querySelector(".total.expenses").textContent = `R$ ${Math.abs(totalDespesas).toFixed(2)}`;
  document.querySelector(".total").textContent = `R$ ${(totalReceitas + totalDespesas).toFixed(2)}`;
}



// ==========================
// Abrir Modal para Cadastro
// ==========================
async function openModalCadastrar() {
  let tituloModal = document.querySelector("#titleModal");
  tituloModal.textContent = "Cadastrar Movimentação";

  const btnSalvar = document.getElementById("btnSalvar");
  btnSalvar.onclick = function () {
    regMovimentacao();
  };

  toggleModal();
}

// ==========================
// Abrir Modal para Edição
// ==========================
async function openModalEditar(botao) {
  let idMovimentacao = botao.dataset.id;
  await consultarMovimentacao(idMovimentacao);

  let tituloModal = document.querySelector("#titleModal");
  tituloModal.textContent = "Edite os dados da movimentação";

  const btnSalvar = document.getElementById("btnSalvar");
  btnSalvar.onclick = function () {
    editarMovimentacao();
  };

  toggleModal();
}

// ==========================
// Consultar Movimentação por ID
// ==========================
async function consultarMovimentacao(idMovimentacao) {
  try {
    const url = urlAction;
    const params = new URLSearchParams({
      acao: 'consultarMovimentacaoId',
      idMovimentacao: idMovimentacao
    }).toString();

    const response = await fetch(`${url}?${params}`, { method: 'GET' });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const json = await response.json();
    console.log("Resposta JSON:", json);

    const form = document.querySelector('#formMovimentacao');

    form.querySelector('select[name="tipoMovimento"]').value = json.tipoMovimento;
    form.querySelector('input[name="valorMovimento"]').value = Number(json.valorMovimento).toFixed(2);
    form.querySelector('input[name="descricao"]').value = json.descricao;
    form.querySelector('input[name="idMovimentacao"]').value = json.id;

    if (json.dataMovimento) {
      const dataFormatada = json.dataMovimento.split('-').reverse().join('/');
      form.elements['dataMovimento'].value = dataFormatada;
    }

  } catch (error) {
    alert('Erro: ' + error.message);
    console.error(error);
  }
}



// ==========================
// Cadastrar Movimentação
// ==========================
async function regMovimentacao() {
  let inputType = document.querySelector("#type");
  let inputData = document.querySelector("#data");
  let inputAmount = document.querySelector("#amount");
  let inputDesc = document.querySelector("#desc");

  if (
    inputType.value.trim() !== "" &&
    inputData.value.trim() !== "" &&
    inputAmount.value.trim() !== "" &&
    inputDesc.value.trim() !== ""
  ) {
    try {
      const url = urlAction;
      const bodyParams = new URLSearchParams();

      bodyParams.append("tipoMovimento", inputType.value);
      bodyParams.append("dataMovimento", inputData.value);
      bodyParams.append("valorMovimento", inputAmount.value);
      bodyParams.append("descricao", inputDesc.value);
      bodyParams.append("acao", "cadastrar");

      const response = await fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: bodyParams.toString()
      });

      if (!response.ok) {
        throw new Error(`Erro ao cadastrar: Status ${response.status}`);
      }

      alert("Movimentação cadastrada com sucesso!");
      location.reload();

    } catch (error) {
      alert("Erro: " + error.message);
      console.error(error);
    }
  } else {
    alert("Por favor, preencha todos os campos.");
  }
}


// ==========================
// Editar Movimentação
// ==========================
async function editarMovimentacao() {
  let inputType = document.querySelector("#type");
  let inputData = document.querySelector("#data");
  let inputAmount = document.querySelector("#amount");
  let inputDesc = document.querySelector("#desc");
  let inputId = document.querySelector("#idMovimentacao");

  if (
    inputType.value.trim() !== "" &&
    inputData.value.trim() !== "" &&
    inputAmount.value.trim() !== "" &&
    inputDesc.value.trim() !== "" &&
    inputId.value.trim() !== ""
  ) {
    try {
      const url = urlAction;
      const bodyParams = new URLSearchParams();

      bodyParams.append("tipoMovimento", inputType.value);
      bodyParams.append("dataMovimento", inputData.value);
      bodyParams.append("valorMovimento", inputAmount.value);
      bodyParams.append("descricao", inputDesc.value);
      bodyParams.append("acao", "editar");
      bodyParams.append("idMovimentacao", inputId.value);

      const response = await fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: bodyParams.toString()
      });

      if (!response.ok) {
        throw new Error(`Erro ao Editar: Status ${response.status}`);
      }

      alert("Movimentação editada com sucesso!");
      location.reload();

    } catch (error) {
      alert("Erro: " + error.message);
      console.error(error);
    }
  } else {
    alert("Por favor, preencha todos os campos.");
  }
}


// ==========================
// Excluir Movimentação
// ==========================
async function excluirMovimentacao(botao) {
  if (confirm("Deseja mesmo deletar essa movimentação?")) {
    let url = document.getElementById("formMovimentacao").action;
    let idMovimentacao = botao.dataset.id;

    if (!idMovimentacao) {
      alert("ID da movimentação não encontrado!");
      return;
    }

    botao.disabled = true;

    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `idMovimentacao=${idMovimentacao}&acao=deletar`
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error('Erro ao deletar movimentação: ' + errorText);
      }

      alert("Movimentação deletada com sucesso!");

      let linha = botao.closest("tr");
      if (linha) linha.remove();

      calcularTotaisTabela();

    } catch (error) {
      alert(error.message);
    }
  }
}


// ================================
// Evento do botão de editar foto
// ================================

    function toggleMenu() {
        document.getElementById('editProfileContainer').classList.toggle('show-menu');
    }

    // Se quiser fechar o menu clicando fora:
    document.addEventListener('click', function(event) {
        const container = document.getElementById('editProfileContainer');
        if (!container.contains(event.target)) {
            container.classList.remove('show-menu');
        }
    });

function viewImg(fotoBase64, fileFoto){
	let preview = document.getElementById(fotoBase64);
	let fileFotoUser = document.getElementById(fileFoto).files[0];
	let reader = new FileReader();

	reader.onloadend = () => {
		preview.src = reader.result;
	}

	if(fileFotoUser){
	  reader.readAsDataURL(fileFotoUser);	
	}else{
	  preview.src ='${pageContext.request.contextPath}/assets/img/perfil.png';	
	}

}
