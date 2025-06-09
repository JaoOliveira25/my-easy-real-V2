package controller;

import java.io.IOException;
import java.sql.Connection;

import connection.ConnectionBanco;
import dao.DAOLoginRepository;
import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelUsuario;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
    private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();
    
    public ServletLogin() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String urlAutenticacao = request.getParameter("url");
		
		try (Connection conn = ConnectionBanco.getConnection()) {
			
			if(email!= null && !email.isBlank() && senha!= null && !senha.isBlank()){
				ModelUsuario modelUsuario = new ModelUsuario();
				modelUsuario.setEmail(email);
				modelUsuario.setSenha(senha);
				
				if(daoLoginRepository.validarAutenticacao(modelUsuario)) {
					modelUsuario = daoUsuarioRepository.consultarUsuarioLogado(modelUsuario.getEmail());
					request.getSession().setAttribute("usuarioLogadoId", modelUsuario.getId());
					
					if (urlAutenticacao == null || urlAutenticacao.equals("null")) {
						
						urlAutenticacao = "jsp/principal/home.jsp";
						
					}
					
					RequestDispatcher redirecionar = request.getRequestDispatcher(urlAutenticacao);
					redirecionar.forward(request, response);
					
				}else {
					RequestDispatcher redireciona = request.getRequestDispatcher("index.jsp");
					request.setAttribute("msg", "Email ou senha inválido!");
					request.setAttribute("visivel","block");
					redireciona.forward(request, response);
				}
				
			}else {
				RequestDispatcher redireciona = request.getRequestDispatcher("index.jsp");
				request.setAttribute("msg", "Email ou senha inválido!");
				redireciona.forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redireciona = request.getRequestDispatcher("error.jsp");
			request.setAttribute("msg", "Aconteceu um erro inesperado tente novamente mais tarde!");
			redireciona.forward(request, response);
		}
		
		
	}

}
