package com.ipartek.formacion.controller;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

import com.ipartek.formacion.modelo.daos.UsuarioDAO;
import com.ipartek.formacion.modelo.pojo.Usuario;




@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioDAO dao;
	private ValidatorFactory factory;
	private Validator validator;
	
	public static final String VIEW_LOGIN = "index.jsp";
	public static final String CONTROLLER_VIDEOS = "privado/videos";
	
	private static final String ES_ES="es_ES";
	private static final String EU_ES="eu_ES";
	
	private final static Logger LOG = Logger.getLogger(LoginController.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
    	dao = UsuarioDAO.getInstance();
    	//Crear Factoria y Validador
    	factory  = Validation.buildDefaultValidatorFactory();
    	validator  = factory.getValidator();
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("mail");
		String pass = request.getParameter("pass");
		String idioma = request.getParameter("idioma");
		
		if(idioma.equals("es")) {
			idioma=ES_ES;
		}else if(idioma.equals("eu")) {
			idioma=EU_ES;
		}else {
			idioma=ES_ES;
		}
		
		String view = VIEW_LOGIN;
		boolean redirect = false;
		
		try {
			HttpSession session = request.getSession();
			
			//IDIOMA
			Locale locale = new Locale(EU_ES);
			ResourceBundle messages = ResourceBundle.getBundle("i18nmessages", locale );
			LOG.debug("idioma="+idioma);
			
			//guardar cookie
			Cookie cIdioma = new Cookie("cIdioma",idioma);
			cIdioma.setMaxAge(60 * 60 * 24 * 365 * 10);
			response.addCookie(cIdioma);
			
			
			
			// validar
			Usuario usuario = new Usuario();
			usuario.setEmail(email);
			usuario.setPassword(pass);
			
			Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
			
			
			
			if ( violations.size() > 0) {			// validacion NO PASA
				
				 String errores = "<ul>"; 
				 for (ConstraintViolation<Usuario> violation : violations) {					 	
					 errores += String.format("<li> %s : %s </li>" , violation.getPropertyPath(), violation.getMessage() );					
				 }
				 errores += "</ul>";				 
				request.setAttribute("error", errores);				
				
			}else {                                // validacion OK
			
				usuario = dao.login(email, pass);
				
				if ( usuario == null ) {
					
					//request.setAttribute("error", "Credenciales incorrectas");
					request.setAttribute("error", messages.getString("login.incorrecto"));
				}else {
					
					
					session.setMaxInactiveInterval(60*10);
					session.setAttribute("usuario_logeado", usuario);
					session.setAttribute("language", idioma);
					redirect = true;					
				}
			}	
				
			
		}catch (Exception e) {
			
			LOG.error(e);
		}finally {
			
			if(redirect) {				
				response.sendRedirect(CONTROLLER_VIDEOS);
			}else {
				request.getRequestDispatcher(view).forward(request, response);
			}
		}
			
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher(VIEW_LOGIN).forward(request, response);
	}

}
