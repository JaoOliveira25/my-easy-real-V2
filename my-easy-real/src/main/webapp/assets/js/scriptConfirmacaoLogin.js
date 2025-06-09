const closeModalButton = document.querySelector("#close-modal");
const modal = document.querySelector("#modal");
const fade = document.querySelector("#fade");

const toggleModal = () => {
    [modal, fade].forEach((el)=> el.classList.toggle("hide"));
};

[closeModalButton, fade].forEach((el)=>{
    el.addEventListener("click", ()=> {
        toggleModal();
    });
})

function exibirModalSucesso(){
    toggleModal();
}

window.addEventListener("DOMContentLoaded", () =>{
	exibirModalSucesso();
});
