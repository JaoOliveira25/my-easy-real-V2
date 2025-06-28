package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.mindrot.jbcrypt.BCrypt;

import com.google.gson.JsonObject;

import dao.DAOUsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelUsuario;
import utils.EnviaEmail;

@MultipartConfig
@WebServlet("/ServletCadastro")
public class ServletCadastro extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			
			if(acao != null && !acao.trim().isEmpty() && acao.equalsIgnoreCase("carregarFoto")) {
				Long usuarioPaiId = (long) request.getSession().getAttribute("usuarioLogadoId");
				ModelUsuario modelUsuario = daoUsuarioRepository.consultarUsuarioById(usuarioPaiId);
				String fotoBase64 = modelUsuario.getFotoUser();
				String nome = modelUsuario.getNome().split(" ")[0];
				
				response.setContentType("application/json");
				
				response.setCharacterEncoding("UTF-8");
				
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("status", "success");
				jsonObject.addProperty("nome", nome);
				
				if(fotoBase64 != null && !fotoBase64.trim().isEmpty()) {
					
					jsonObject.addProperty("src", fotoBase64);
		
					String json = jsonObject.toString();
			    	response.getWriter().write(json);
				}else {
					
					String contextPath = request.getContextPath();
					String imgPadraoPath = contextPath + "/assets/img/perfil.png";
					jsonObject.addProperty("src", imgPadraoPath );
					String json = jsonObject.toString();
			    	response.getWriter().write(json);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();

		    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");

		    String json = "{\"status\":\"error\", \"message\":\"Ocorreu um erro no servidor.\"}";
		    response.getWriter().write(json);
		
		}
	
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		
		if(acao != null && !acao.trim().isEmpty() && acao.equalsIgnoreCase("editarFoto") ) {
			
			Part part = request.getPart("fileFoto");
			
			if (part != null) {
				
				ModelUsuario modelUsuario = new ModelUsuario();
				
				Long usuarioLogadoId = (long) request.getSession().getAttribute("usuarioLogadoId");

				if (part.getSize() > 0 && part.getSize() < (2 * 1024 * 1024) ) {
					
					InputStream inputStream = part.getInputStream();
					
					Tika tika = new Tika();
					
					String detectedType = tika.detect(inputStream);
					
					List<String> tiposPermitidos = Arrays.asList("image/png", "image/jpeg", "image/gif", "image/webp");
					
					if (!tiposPermitidos.contains(detectedType)) {
					    throw new ServletException("Tipo de arquivo inválido!");
					} 
					
					byte[] foto = part.getInputStream().readAllBytes();
					
					String imagemBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64,"
							+ Base64.getEncoder().encodeToString(foto);
					
					
					modelUsuario.setId(usuarioLogadoId);
					
					modelUsuario.setFotoUser(imagemBase64);
					
					modelUsuario.setExtensaoFotoUser(part.getContentType().split("\\/")[1]);

					
					try {
						
						boolean cadastroOk = daoUsuarioRepository.editarFotoUser(modelUsuario);
						
						response.setContentType("application/json");
						
						response.setCharacterEncoding("UTF-8");
						
						if(cadastroOk ) {
					    	String json = "{\"status\":\"success\", \"message\":\"Foto atualizada com sucesso!\"}";
					    	response.getWriter().write(json);
					    }else {
					    	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					    	String json = "{\"status\":\"error\", \"message\":\"Erro ao atualizar a foto.\"}";
					    	response.getWriter().write(json);
					    }
					} catch (Exception e) {
						
						 e.printStackTrace();

						    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						    response.setContentType("application/json");
						    response.setCharacterEncoding("UTF-8");

						    String json = "{\"status\":\"error\", \"message\":\"Ocorreu um erro no servidor.\"}";
						    response.getWriter().write(json);
						
					}
					
				}else { // Limite de 2MB
					throw new ServletException("Arquivo muito grande! O tamanho máximo permitido é 2MB.");
				}
				

			}
		}else if (acao != null && !acao.trim().isEmpty() && acao.equalsIgnoreCase("cadastrarUsuario")) {
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

}
