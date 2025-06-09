const accordions = document.querySelectorAll('.option-accordion');

accordions.forEach((accordion) => {
    
    accordion.addEventListener('click', () => {
        document.querySelectorAll('.accordion-body').forEach((body)=>{
            body.classList.remove('active');
        })
        const body = accordion.querySelector('.accordion-body');
        body.classList.toggle('active');
    })
});