const form = document.getElementById("cadastro-form");
const mensagem = document.getElementById("mensagem");

function limparErros() {
    mensagem.hidden = true;
    mensagem.textContent = "";
    document.querySelectorAll("[data-error-for]").forEach((elemento) => {
        elemento.textContent = "";
    });
}

form.addEventListener("input", (event) => {
    const campo = event.target.name;
    const erro = document.querySelector(`[data-error-for="${campo}"]`);
    if (erro) {
        erro.textContent = "";
    }
    if ([...document.querySelectorAll(".field-error")].every((item) => !item.textContent)) {
        mensagem.hidden = true;
    }
});

form.addEventListener("submit", async (event) => {
    event.preventDefault();
    limparErros();

    const dados = Object.fromEntries(new FormData(form).entries());
    try {
        const resposta = await fetch("/api/servidores", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dados)
        });
        const resultado = await resposta.json();

        if (!resposta.ok) {
            mensagem.textContent = resultado.mensagem || "Não foi possível concluir o cadastro.";
            mensagem.hidden = false;
            Object.entries(resultado.campos || {}).forEach(([campo, erro]) => {
                const elemento = document.querySelector(`[data-error-for="${campo}"]`);
                if (elemento) {
                    elemento.textContent = erro;
                }
            });
            return;
        }

        window.location.assign("/login?cadastro=sucesso");
    } catch {
        mensagem.textContent = "Não foi possível conectar ao servidor. Tente novamente.";
        mensagem.hidden = false;
    }
});
