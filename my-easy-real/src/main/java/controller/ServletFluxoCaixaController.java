package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dao.DAOFluxoCaixaRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelFluxoCaixa;


@WebServlet("/ServletFluxoCaixaController")
public class ServletFluxoCaixaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DAOFluxoCaixaRepository daoFluxoCaixa = new DAOFluxoCaixaRepository();
	
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			if(acao != null && !acao.trim().isEmpty() && acao.equalsIgnoreCase("carregarFluxoCaixa")) {
				//Esse único método serve para se caso o usuario somente clicar na seta que altera o mês
				Long usuarioPaiId = (long) request.getSession().getAttribute("usuarioLogadoId");
				int mesParam = Integer.parseInt(request.getParameter("mes"));
				int mesSql = mesParam +1;
				int ano = Integer.parseInt(request.getParameter("ano"));
			
				List<ModelFluxoCaixa> fluxoCaixa = daoFluxoCaixa.carregarMovimentacoes(usuarioPaiId,mesSql,ano);
				
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JavaTimeModule());
				objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

				
				String jsonFluxoCaixa = objectMapper.writeValueAsString(fluxoCaixa);
				response.getWriter().write(jsonFluxoCaixa);
		
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("error.jsp");
			request.setAttribute("msg", "Houve um erro inesperado por favor tente novamente mais tarde");
			redirecionar.forward(request, response);
		}
		
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			
			if(acao != null && !acao.trim().isEmpty()) {
				if("cadastrar".equalsIgnoreCase(acao.trim())) {
					
					String dataParam = request.getParameter("dataMovimento");
					String valorParam = request.getParameter("valorMovimento");
					String tipoParam = request.getParameter("tipoMovimento");	
					
					
					LocalDate dataMovimento = LocalDate.parse(dataParam);
					BigDecimal  valorMovimento = new BigDecimal(valorParam);
					char tipoMovimento = tipoParam.charAt(0);
					String descricao = request.getParameter("descricao");
					Long usuarioPaiId = (Long) request.getSession(false).getAttribute("usuarioLogadoId");

					ModelFluxoCaixa modelFluxoCaixa = new ModelFluxoCaixa();
					modelFluxoCaixa.setDataMovimento(dataMovimento);
					modelFluxoCaixa.setDescricao(descricao);
					modelFluxoCaixa.setTipoMovimento(tipoMovimento);
					modelFluxoCaixa.setValorMovimento(valorMovimento);
					modelFluxoCaixa.setUsuarioPaiId(usuarioPaiId);
					
					
				    daoFluxoCaixa.registrarMovimentacao(modelFluxoCaixa);
					
				    request.getRequestDispatcher("jsp/principal/home.jsp").forward(request, response);
				    
				}else if ("editar".equalsIgnoreCase(acao.trim())) {
					//btn editar abre form modal com inpu hidden
					String idParam = request.getParameter("idMovimentacao");//param do hidden 
					String dataParam = request.getParameter("dataMovimento");
					String valorParam = request.getParameter("valorMovimento");
					String tipoParam = request.getParameter("tipoMovimento");	
					
					Long idMovimentacao = Long.parseLong(idParam);
					LocalDate dataMovimento = LocalDate.parse(dataParam);
					BigDecimal  valorMovimento = new BigDecimal(valorParam);
					char tipoMovimento = tipoParam.charAt(0);
					String descricao = request.getParameter("descricao");
					Long usuarioPaiId = (Long) request.getSession(false).getAttribute("usuarioLogadoId");
					
					ModelFluxoCaixa modelFluxoCaixa = new ModelFluxoCaixa();
					modelFluxoCaixa.setId(idMovimentacao);
					modelFluxoCaixa.setDataMovimento(dataMovimento);
					modelFluxoCaixa.setDescricao(descricao);
					modelFluxoCaixa.setValorMovimento(valorMovimento);
					modelFluxoCaixa.setTipoMovimento(tipoMovimento);
					modelFluxoCaixa.setUsuarioPaiId(usuarioPaiId);
					
					daoFluxoCaixa.editarMovimentacao(modelFluxoCaixa);
					
					request.getRequestDispatcher("jsp/principal/home.jsp").forward(request, response);
					
				}else if("deletar".equalsIgnoreCase(acao.trim())) {
					//lógica de editar no bd método que deleta pelo ID 
					String idParam = request.getParameter("idMovimentacao");
					Long idMovimentacao = Long.parseLong(idParam);
					
					daoFluxoCaixa.deletarMovimentacao(idMovimentacao);
					
					request.getRequestDispatcher("jsp/principal/home.jsp").forward(request, response);
				
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	
		
		
		
		
		
		
		
		
	}

}
