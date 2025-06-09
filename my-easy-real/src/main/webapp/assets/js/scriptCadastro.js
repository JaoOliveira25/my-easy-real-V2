//========================== Evento de ver senha ==========================

let olho1 = document.querySelector('#olho1');
let olho2 = document.querySelector('#olho2');

olho1.addEventListener('click', () => {
    let inputSenha = document.querySelector('#senha');

        if(inputSenha.getAttribute('type')=='password'){
        /*o método getAttribute() retornar o valor do atributo passado como parametro 
        no caso o 'type' */
            inputSenha.setAttribute('type', 'text');
        }else{
            inputSenha.setAttribute('type', 'password');
        }
    }
);

olho2.addEventListener('click', () => {
    let inputConfSenha = document.querySelector('#confirmSenha');

        if(inputConfSenha.getAttribute('type')=='password'){
        /*o método getAttribute() retornar o valor do atributo passado como parametro 
        no caso o 'type' */
            inputConfSenha.setAttribute('type', 'text');
        }else{
            inputConfSenha.setAttribute('type', 'password');
        }
    }
);

//======================== Evento de Validação ===================================

let inputNome = document.querySelector('#nome');
let labelNome = document.querySelector('#labelNome');
let validNome = false;

let inputEmail = document.querySelector('#email');
let labelEmail = document.querySelector('#labelEmail');
let validEmail = false;

let inputSenha = document.querySelector('#senha');
let labelSenha = document.querySelector('#labelSenha');
let validSenha = false;

let inputConfSenha = document.querySelector('#confirmSenha');
let labelConfSenha = document.querySelector('#labelConfSenha');
let validConfSenha = false;


inputNome.addEventListener('keyup',()=>{
    if (inputNome.value.length < 3) {
        labelNome.setAttribute('style', 'color: #F44336');
        labelNome.innerHTML = 'Nome  *Insira no mínimo 3 caracteres';
        inputNome.setAttribute('style', 'border-color: #F44336');
        validNome = false;
    } else {

        labelNome.setAttribute('style', 'color: black');
        labelNome.innerHTML = 'Nome';
        inputNome.setAttribute('style', 'border-color: rgb(98, 92, 92)');
        validNome = true;
    }
});

inputEmail.addEventListener('keyup', () => {
    // Expressão regular simples para verificar formato de e-mail
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailRegex.test(inputEmail.value)) {
        labelEmail.setAttribute('style', 'color: #F44336');
        labelEmail.innerHTML = 'Email *Insira um email válido';
        inputEmail.setAttribute('style', 'border-color: #F44336');
        validEmail = false;
    } else {
        labelEmail.setAttribute('style', 'color: black');
        labelEmail.innerHTML = 'Email';
        inputEmail.setAttribute('style', 'border-color: rgb(98, 92, 92)');
        validEmail = true;
    }
});



inputSenha.addEventListener('keyup',()=>{
    if (inputSenha.value.length < 6) {
        labelSenha.setAttribute('style', 'color: #F44336');
        labelSenha.innerHTML = 'Senha  *Insira no mínimo 6 caracteres';
        inputSenha.setAttribute('style', 'border-color: #F44336');
        validSenha = false;
    } else {

        labelSenha.setAttribute('style', 'color: black');
        labelSenha.innerHTML = 'Senha';
        inputSenha.setAttribute('style', 'border-color: rgb(98, 92, 92)');
        validSenha = true;
    }
});

inputConfSenha.addEventListener('keyup',()=>{
    if (inputConfSenha.value != inputSenha.value ) {
        labelConfSenha.setAttribute('style', 'color: #F44336');
        labelConfSenha.innerHTML = 'Confirmar Senha *As senhas não conferem';
        inputConfSenha.setAttribute('style', 'border-color: #F44336');
        validConfSenha = false;
    } else {

        labelConfSenha.setAttribute('style', 'color: black');
        labelConfSenha.innerHTML = 'Confirmar Senha';
        inputConfSenha.setAttribute('style', 'border-color: rgb(98, 92, 92)');
        validConfSenha = true;
    }
});

//=========================== Função Modal ======================================

const closeModalButton = document.querySelector("#close-modal");
const modal = document.querySelector("#modal");
const fade = document.querySelector("#fade");

const toggleModal = () => {
    [modal, fade].forEach((el)=> el.classList.toggle("hide"));
};

[closeModalButton, fade].forEach((el)=>{
    el.addEventListener("click", ()=> {
        toggleModal();

        setTimeout(() => {
            window.location.href = "/my-easy-real/index.jsp"; 
        }, 300);
    });
})

function exibirModalSucesso(mensagem){
    const mensagemModal = document.querySelector("#modalMensagem");
    mensagemModal.textContent = mensagem;

    toggleModal();

}



//=========================== Função de cadastro ==================================

let msgErrorCad = document.querySelector('#msgErrorCad');
let msgSuccesCad = document.querySelector('#msgSuccesCad');
const btnCadastrar = document.querySelector('#btnCadastrar');
const formCadastro = document.querySelector('#formCadastro');

btnCadastrar.addEventListener('click', async ()=>{
    
   if(validNome && validEmail && validSenha && validConfSenha){
    
           msgSuccesCad.style ='display: block;';
           msgSuccesCad.innerHTML = '<strong>Cadastrando usuário ...</strong>';
           msgErrorCad.style ='display: none;';
           msgErrorCad.innerHTML = '';

           //Monta os dados do fomulario 
           const formDados = new URLSearchParams(new FormData(formCadastro)); 

           try {
                const response = await fetch(formCadastro.action, {
                    method: "POST",
                    headers:{
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: formDados
                });

                if(response.ok){
                    const data = await response.json();
                    const mensagem = data.mensagem;
                    exibirModalSucesso(mensagem)
                    //pega o data.mensagem e insere dentro da área de texto do motdal e então exibe o modal 
                }else{
                    msgErrorCad.style ='display: block;';
                    msgErrorCad.innerHTML = '<strong>Erro ao cadastrar. Tente novamente.</strong>';
                }

           } catch (error) {
                msgErrorCad.style ='display: block;';
                msgErrorCad.innerHTML = '<strong>Preencha todos os campos corretamente.</strong>';
                msgSuccesCad.style = 'display: none;';
                msgSuccesCad.innerHTML = '';
           }

     }else{
           msgErrorCad.style = 'display: block;'
           msgErrorCad.innerHTML = '<strong>Preencha todos os campos corretamente!</strong>';
           msgSuccesCad.style ='display: none;';
           msgSuccesCad.innerHTML = '';
     }
})