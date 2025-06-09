package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import dao.DAOUsuarioRepository;

@WebServlet("/ServletConfirmaCadastro")
public class ServletConfirmaCadastro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
    public ServletConfirmaCadastro() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		
		try {
			String retorno = daoUsuarioRepository.confirmarCadastro(token);
			request.setAttribute("msg", retorno);
			request.getRequestDispatcher("confirmacaologin.jsp").forward(request, response);
			//da um dispatcher com o atributo msg para uma tela que sobe automaticamente um modal 
		} catch (SQLException e) {
			e.printStackTrace();
			response.getWriter().write("Erro na confirmação :"+e.getMessage());
		}	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
