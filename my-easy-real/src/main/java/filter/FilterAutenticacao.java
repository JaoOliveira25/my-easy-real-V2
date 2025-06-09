package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.ConnectionBanco;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebFilter("/jsp/principal/*")
public class FilterAutenticacao extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);

		String usuarioLogado = (session != null) ? (String) session.getAttribute("usuarioLogado") : null;
		String urlParaAutenticar = req.getServletPath();

		if (usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {
			RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
			request.setAttribute("msg", "Por favor, realize o login!");
			request.setAttribute("visivel", "block");

			redireciona.forward(request, response);
			return;
		}

		Connection connection = null;

		try {
			connection = ConnectionBanco.getConnection();
			chain.doFilter(request, response);
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();

			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException rollbackEx) {
					rollbackEx.printStackTrace();
				}
			}

			request.setAttribute("msg", "Ocorreu um erro inesperado por favor tente novamente mais tarde. ");
			RequestDispatcher redireciona = request.getRequestDispatcher("error.jsp");
			redireciona.forward(request, response);

		}finally {
			
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException closeEx) {
	                closeEx.printStackTrace();
	            }
	        }
	    }

	}

}
