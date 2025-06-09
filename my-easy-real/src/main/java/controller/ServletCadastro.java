package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

import dao.DAOUsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelUsuario;
import utils.EnviaEmail;

@WebServlet("/ServletCadastro")
public class ServletCadastro extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
    public ServletCadastro() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String senha_hash = BCrypt.hashpw(senha, BCrypt.gensalt());
		String token = UUID.randomUUID().toString();
		Timestamp dataExpiracao = Timestamp.valueOf(LocalDateTime.now().plusHours(24));
		String mensagemResponse;
		
		boolean cadastroOk = false;
		
		
		ModelUsuario modelUsuario = new ModelUsuario();
		modelUsuario.setNome(nome);
		modelUsuario.setEmail(email);
		modelUsuario.setSenha(senha_hash);
		modelUsuario.setTokenConfirmacao(token);
		modelUsuario.setDataExpiracao(dataExpiracao);
				
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
		PrintWriter saida = response.getWriter();
		
		try {
			cadastroOk = daoUsuarioRepository.cadastrarUsuario(modelUsuario);
			
			
			
			if(cadastroOk) {
				
				
				String linkConfirmacao = "http://localhost:8080/my-easy-real/ServletConfirmaCadastro?token="+modelUsuario.getTokenConfirmacao()+"&email="+modelUsuario.getEmail();
				String listaDestinatarios = modelUsuario.getEmail();
				String remetente = "My Easy Real";
				String assuntoEmail = "Confirmação de cadastro";
				StringBuilder stringBuilderHtml = new StringBuilder();
				
				stringBuilderHtml.append("<p> Clique no link para confirmar seu cadastro: <a href=\""+linkConfirmacao+"\">Confirmar cadastro</a></p><br/><br/>");
				EnviaEmail enviaEmail = new EnviaEmail(listaDestinatarios,remetente,assuntoEmail,stringBuilderHtml.toString());
				enviaEmail.enviarEmail(true);
				
				response.setStatus(HttpServletResponse.SC_OK);//status 200
				
				saida.println("{\"mensagem\": \"Cadastro iniciado! Enviamos um link de confirmação para seu email "+modelUsuario.getEmail()+", não esqueça de olhar sua caixa de span.\"}");
			
			}else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);//status 400
				mensagemResponse = "Erro ao cadastrar Usuário tente novamente!  ";
				saida.println("{\"mensagem\": "+mensagemResponse+"}");

			}
		} catch (SQLException e) {
			mensagemResponse = "Erro ao cadastrar Usuário tente novamente!  ";
			saida.println("{\"mensagem\": "+mensagemResponse+"}");
			
		    return;
		}
		
		saida.flush();

	}

}
