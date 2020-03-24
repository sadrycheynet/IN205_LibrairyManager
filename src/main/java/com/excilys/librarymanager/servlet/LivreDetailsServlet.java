package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;

import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Emprunt;

import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.LivreServiceImpl;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.EmpruntServiceImpl;

public class LivreDetailsServlet extends HttpServlet {	
	
	/*!
	 * Cette méthode redirige sur la JSP livre_details contenant les informations du livre que l'on souhaite observer.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Récupération de l'id du livre observé (si il y en a un)		
		String inputId = request.getParameter("id");
		int id = -1;
		
		Livre livre;
		List<Emprunt> listEmprunts = new ArrayList<Emprunt>();
		
		LivreService livreService = LivreServiceImpl.getInstance();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		
		try{
			id = Integer.parseInt(inputId);
			livre = livreService.getById(id);
			listEmprunts = empruntService.getListCurrentByLivre(id);
		} catch(ServiceException e) {
			livre = new Livre();
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		} catch(NumberFormatException ebis){
			livre = new Livre();
			throw new ServletException("Erreur lors du parsing : id="+inputId,ebis);
		} catch(Exception e){
			throw new ServletException("Erreur au niveau du servlet : ",e);
		}
		
		request.setAttribute("livre",livre);
		request.setAttribute("emprunts",listEmprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/livre_details.jsp");
		dispatcher.forward(request, response);
	}
	
	/*!
	 * Cette méthode redirige vers la JSP livre_details permettant d'observer les informations modifiées du livre observé.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String inputId = request.getParameter("id");
		String inputTitre = request.getParameter("titre");
		String inputAuteur = request.getParameter("auteur");
		String inputIsbn = request.getParameter("isbn");
		
		int id = -1;
		
		Livre livre;
		List<Emprunt> listEmprunts = new ArrayList<Emprunt>();
		
		LivreService livreService = LivreServiceImpl.getInstance();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		try {
			id = Integer.parseInt(inputId);
						
			//Modification du livre
			livre = livreService.getById(id);
			livre.setTitre(inputTitre);
			livre.setAuteur(inputAuteur);
			livre.setIsbn(inputIsbn);
			
			livreService.update(livre);
			listEmprunts = empruntService.getListCurrentByLivre(id);
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			livre = new Livre();
		} catch(NumberFormatException ebis){
			livre = new Livre();		//Je ne suis pas sûr que cela soit utile, mais dans le doute, ça coûte pas tant que ça d'initialiser "livre"
			throw new ServletException("Erreur lors du parsing : id="+inputId,ebis);
		} catch(Exception e){
			throw new ServletException("Erreur au niveau du servlet : ",e);
		}
		
		request.setAttribute("emprunts",listEmprunts);
		request.setAttribute("livre", livre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/livre_details.jsp");
		dispatcher.forward(request, response);
	}
}