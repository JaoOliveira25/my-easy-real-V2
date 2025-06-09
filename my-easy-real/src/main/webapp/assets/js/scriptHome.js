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


