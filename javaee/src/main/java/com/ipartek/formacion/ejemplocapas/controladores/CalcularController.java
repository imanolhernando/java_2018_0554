package com.ipartek.formacion.ejemplocapas.controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.PortableInterceptor.ForwardRequest;

public class CalcularController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("calculadora.jsp").forward(request, response);

		
		
		
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op1 = request.getParameter("op1");
		String op2 = request.getParameter("op2");
		
		
		try {
		int sum1;
		int sum2;
		
			sum1 = Integer.parseInt(op1);
			sum2 = Integer.parseInt(op2);
			
			request.setAttribute("suma", sum1 + sum2);
		request.getRequestDispatcher("resultado.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			request.setAttribute("mensaje", "Por favor rellena los campos con numeros");
			request.getRequestDispatcher("calculadora.jsp").forward(request, response);;
		}
		
		
		
	}

}
