package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;

import com.excilys.librarymanager.model.Membre;

import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.MembreServiceImpl;

public class MembreDeleteServlet extends HttpServlet {	
	
	/*!
	 * Cette méthode redirige sur la JSP membre_delete permettant de supprimer le membre observé
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreService membreService = MembreServiceImpl.getInstance();
		
		String inputId = request.getParameter("id");
		int id = -1;
		
		Membre membre;
		
		try{
			id = Integer.parseInt(inputId);
			membre = membreService.getById(id);
			
		} catch(ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			membre = new Membre();
			
		} catch(NumberFormatException ebis){
			membre = new Membre();		//Je ne suis pas sûr que cela soit utile, mais dans le doute, ça coûte pas tant que ça d'initialiser "membre"
			throw new ServletException("Erreur lors du parsing : id="+inputId,ebis);
		} catch(Exception e){
			throw new ServletException("Erreur au niveau du servlet : ",e);
		}
		
		request.setAttribute("membre", membre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/membre_delete.jsp");
		dispatcher.forward(request, response);
	}
	
	/*!
	 * Cette méthode redirige sur la JSP membre_list permettant d'observer la liste des membres
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreService membreService = MembreServiceImpl.getInstance();
		
		String inputId = request.getParameter("id");
		int id = -1;
		
		try{
			id = Integer.parseInt(inputId);
			membreService.delete(id);
			
		} catch(ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		} catch(NumberFormatException ebis){
			throw new ServletException("Erreur lors du parsing : id="+inputId,ebis);
		} catch(Exception e){
			throw new ServletException("Erreur au niveau du servlet : ",e);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/membre_list.jsp");
		dispatcher.forward(request, response);
	}
}