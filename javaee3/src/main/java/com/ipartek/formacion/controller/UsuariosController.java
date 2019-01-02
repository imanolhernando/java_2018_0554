package com.ipartek.formacion.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import com.ipartek.formacion.modelo.daos.UsuarioDAO;
import com.ipartek.formacion.modelo.pojos.Usuario;

/**
 * Servlet implementation class UsuariosController
 */
@WebServlet("/privado/usuarios")
public class UsuariosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = Logger.getLogger(UsuariosController.class);

	private static final String VIEW_INDEX = "usuarios/index.jsp";
	private static final String VIEW_FORM = "usuarios/form.jsp";
	private String vista;

	public static final String OP_LISTAR = "1";
	public static final String OP_IR_FORMULARIO = "2";
	public static final String OP_GUARDAR = "3"; // id==-1 insert, id>0 update
	public static final String OP_ELIMINAR = "4";

	private String alerta = "";

	// parametros
	private String op;
	private String id;
	private String email;
	private String password;

	private static UsuarioDAO dao = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = UsuarioDAO.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doProcess(request, response);

		/*
		 * request.setAttribute("usuarios", dao.getAll() );
		 * request.getRequestDispatcher("usuarios/index.jsp").forward(request,
		 * response);
		 */
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		vista= VIEW_INDEX;
		alerta = "";
		try {

			// recoger parametros
			getParametros(request);
			// realizar operacion
			switch (op) {
			case OP_IR_FORMULARIO:
				irFormulario(request);
				break;
			case OP_GUARDAR:
				guardar(request);
				break;
			case OP_ELIMINAR:
				eliminar(request);
				break;
			default:
				listar(request);
				break;
			}

			// enviar atributos

		} catch (Exception e) {
			LOG.error(e);
			alerta = "Error inesperado, sentimos las molestias";
		} finally {

			// mensaje para el usuario
			request.setAttribute("alerta", alerta);
			// ir a una vista
			request.getRequestDispatcher(vista).forward(request, response);
		}
	}

	private void listar(HttpServletRequest request) {
		// alerta = "Lista de Usuarios";
				request.setAttribute("usuarios", dao.getAll());
	}

	private void eliminar(HttpServletRequest request) {
		//request.setAttribute("usuarios", dao.eliminar());

	}

	private void guardar(HttpServletRequest request) throws SQLException {
		Usuario u = new Usuario();
		int identificador = Integer.parseInt(id);
		
		// u.setId((long)identificador);
				u.setEmail(email);
				u.setPassword(password);
				
				//TODO validar POJO
				
				//si validacion no correcta
				   
				  // alerta al usuario
				
				  // volver al formulario, cuidado que no se pierdan los valores en el form
		
				// Si validacion correcta
				if ( identificador > 0 ) {
					alerta = "UPDATE Usuario ";
					// TODO dao.update	
					
				}else {
					alerta = "Crear un nuevo Usuario";
					dao.insert(u);
				}
			
				listar(request);
	}

	private void irFormulario(HttpServletRequest request) {
		vista = VIEW_FORM;
		
		Usuario u = new Usuario();
		
		int identificador = Integer.parseInt(id);
		if(identificador > 0) {
			//alerta = "Detalle de un Usuario"+ identificador;
			u = dao.getById(identificador);
			
		}else {
			alerta ="Crear nuevo Usuario" ;
		}
		
		request.setAttribute("usuario", u);
		
	}

	
	


	private void getParametros(HttpServletRequest request) {
		op= request.getParameter("op");
		if(op == null) {
			op= OP_LISTAR;
		}
		
		id = request.getParameter("id");
		email = request.getParameter("email");
		password = request.getParameter("password");
		
		LOG.debug(String.format("parametros: op=%s id=%s email=%s password=%s ", op, id, email, password));
	}
}