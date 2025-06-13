let dataAtual = new Date();
let mesAtual = dataAtual.getMonth();
let ano = dataAtual.getFullYear();
let urlAction =  document.querySelector('#formMovimentacao').action;

document.addEventListener("DOMContentLoaded", function () {
  carregarMovimentacoes();
  $("#data").inputmask("99/99/9999");
  calcularTotaisTabela();
});

async function carregarMovimentacoes(){

  try {

     const url = urlAction;
     const params = new URLSearchParams({
	    mes: mesAtual,
	    ano: ano,
	    acao: 'carregarFluxoCaixa'
     }).toString();

     const response = await fetch(`${url}?${params}`,{
	    method:'GET',
     });

     if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

     const json = await response.json();

     const tbody = document.querySelector('#tabelaFluxoCaixa tbody');

     tbody.innerHTML = '';

json.forEach(item =>{
	const row = document.createElement('tr');
	row.innerHTML = `<td>${item.dataMovimento.split('-').reverse().join('/').slice(0, 5)}</td>
			<td >${item.descricao}</td>
			<td >R$ ${Number(item.valorMovimento).toFixed(2)}</td>
			<td style="text-align: center;">
			   <button data-id="${item.id}" onclick="openModal(this, 'editar')" class="icon-button"><i class="fa-solid fa-pen-to-square"></i></button>	
			</td>
			<td style="text-align: center;">
			   <button data-id="${item.id}" onclick="excluirMovimentacao(this, 'deletar')" class="icon-button"><i class="fa-solid fa-trash-can"></i></button>
			</td>`;
        tbody.appendChild(row);
});


  }catch(error){
	alert('Erro: '+error.message);
	console.error(error);
  }
}




const openModalButton = document.querySelector("#open-modal-button");//o botão que vai abrir meu modal é o cadastrar
const closeModalButton = document.querySelector("#close-modal");
const modal = document.querySelector("#modal");
const fade = document.querySelector("#fade");

const toggleModal = () => {
    [modal, fade].forEach((el)=> el.classList.toggle("hide"));
};

[openModalButton, closeModalButton, fade].forEach((el)=>{
    el.addEventListener("click", ()=> toggleModal());
})





document.getElementById("amount").addEventListener("input", (event)=>{
  const inputAmount = event.target.value;

  if(inputAmount.includes(".")){
    const partes = inputAmount.split(".");
    if(partes[1].length>2){
      event.target.value  = partes[0] + "." + partes[1].substring(0,2);
    }
  }
})

//======================== Evento de Validação dos campos do form ===================================


 function regMovimentacao(){
  

  let inputType = document.querySelector("#type");
  let inputData = document.querySelector("#data");
  let inputAmount = document.querySelector("#amount");
  let inputDesc = document.querySelector("#desc");
  
  
  if(
  inputType.value.trim() !== "" &&
  inputData.value.trim() !== "" &&
  inputAmount.value.trim() !== "" &&
  inputDesc.value.trim() !== ""
  ){

    document.querySelector("#formMovimentacao").submit();
  
  } else {
    alert("Por favor, preencha todos os campos.");
  }

}

function calcularTotaisTabela (){
  let totalReceitas = 0;
  let totalDespesas = 0;

  const linhas = document.querySelectorAll("#tabelaFluxoCaixa tbody tr");

linhas.forEach((linha)=>{
  const valorTexto = linha.childre[2].textContent.replace("R$","").trim();
  const valor = parseFloat(valorTexto);

  if(!isNaN(valor)){
    if(valor > 0){
      totalReceitas += valor;
    }else if(valor < 0){
      totalDespesas += valor;
    }	
  }

});

document.querySelector(".total.incomes").textContent = `R$ ${totalReceitas.toFixed(2)}`;
document.querySelector(".total.expenses").textContent = `R$ ${Math.abs(totalDespesas).toFixed(2)}`;
document.querySelector(".total").textContent = `R$ ${(totalReceitas + totalDespesas).toFixed(2)}`;

}
