let dataAtual = new Date();
let mesAtual = dataAtual.getMonth();
let ano = dataAtual.getFullYear();
let urlAction =  document.querySelector('#formMovimentacao').action;

document.addEventListener("DOMContentLoaded", function () {
  carregarMovimentacoes();
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
	row.innerHTML = `<td>${item.dataMovimento}</td>
			<td>${item.descricao}</td>
			<td>R$ ${Number(item.valorMovimento).toFixed(2)}</td>
			<td>
			   <button data-id="${item.id}" onclick="editarMovimentacao(this, 'editar')"><i class="fa-solid fa-pen-to-square"></i></button>	
			</td>
			<td>
			   <button data-id="${item.id}" onclick="excluirMovimentacao(this, 'deletar')"><i class="fa-solid fa-trash"></i></button>
			</td>`;
        tbody.appendChild(row);
});


  }catch(error){
	alert('Erro: '+error.message);
	console.error(error);
  }
}





