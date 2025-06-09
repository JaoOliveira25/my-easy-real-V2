let olho = document.querySelector('.fa-eye');

olho.addEventListener('click', () => {
	let inputSenha = document.querySelector('#senha');
	if (inputSenha.getAttribute('type') == 'password') {

		inputSenha.setAttribute('type', 'text');
	} else {
		inputSenha.setAttribute('type', 'password');
	}
}
);

const emailInput = document.getElementById("email");
const senhaInput = document.getElementById("senha");
const msg = document.getElementById("msg");

function esconderMsgErro() {
	if (msg) {
		msg.style.display = "none";
	}
}

emailInput.addEventListener("input", esconderMsgErro);
senhaInput.addEventListener("input", esconderMsgErro);







