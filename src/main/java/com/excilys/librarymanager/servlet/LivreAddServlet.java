package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;

import com.excilys.librarymanager.model.Livre;

import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.LivreServiceImpl;

public class LivreAddServlet extends HttpServlet {	
	
	/*!
	 * Cette méthode redirige sur la JSP livre_add permettant d'ajouter un livre à la bibliothèque
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/livre_add.jsp");
		dispatcher.forward(request, response);
	}
	
	/*!
	 * Cette méthode redirige vers la JSP livre_details permettant d'observer les informations concernant le livre venant d'être créé
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreService livreService = LivreServiceImpl.getInstance();
		String inputTitre = request.getParameter("titre");
		String inputAuteur = request.getParameter("auteur");
		String inputIsbn = request.getParameter("isbn");
		
		String inputId ="-1";
		int id = -1;
		Livre newLivre;
		try {
			//Création du livre (les vérifications et modifications sont gérées par la couche "Service", et non par le servlet)
			//le choix de l'abonnement est géré par le dao
			id = livreService.create(inputTitre, inputAuteur, inputIsbn);
			inputId = String.valueOf(id);
			newLivre = livreService.getById(id);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			newLivre = new Livre();
		}
		
		request.setAttribute("id",inputId);
		request.setAttribute("livre", newLivre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/livre_details.jsp");
		dispatcher.forward(request, response);
	}
}