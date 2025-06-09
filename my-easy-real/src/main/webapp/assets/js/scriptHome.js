let dataAtual = new Date();
let mesAtual = dataAtual.getMonth();
let ano = dataAtual.getFullYear();
let urlAction =  document.querySelector('#formMovimentacao').action;

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
	row.innerHTML = `<td>${item.dataMovimento}</td>
			<td>${item.descricao}</td>
			<td>R$ ${Number(item.valorMovimento).toFixed(2)}</td>
			<td>
			   <button data-id="${item.id}" onclick="editarMovimentacao(this, 'editar')"><i class="fa-solid fa-pen-to-square"></i></button>	
			</td>
			<td>
			   <button data-id="${item.id}" onclick="excluirMovimentacao(this, 'deletar')">icone lixei excluir : <i class="fa-solid fa-trash"></i></button>
			</td>`;
        tbody.appendChild(row);
});


  }catch(error){
	alert('Erro: '+error.message);
	console.error(error);
  }
}


const modal = document.querySelector('.modal-container');
const tbody = document.querySelector('.tbody');
const data = document.querySelector('#data');
const descricao = document.querySelector('#desc');
const valor = document.querySelector('#amount');
const type = document.querySelector('#type');
const btnSalvar = document.querySelector('#btnSalvar');

let itens;
let id;

function openModal(edit = false, index = 0){
    modal.classList.add('active');

    modal.onclick =  function(e) {
        if(e.target.className.indexOf('modal-container')!== -1){
            modal.classList.remove('active');
        }
    }

    if(edit){
        data.value = itens[index].data;
        descricao.value = itens[index].desc;
        valor.value = itens[index].amount;
        type.value = itens[index].type;
        id = index;
    }else{
        data.value = '';
        descricao.value = '';
        valor.value = '';
        type.value = '';
    }
}


