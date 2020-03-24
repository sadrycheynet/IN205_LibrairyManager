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

public class MembreAddServlet extends HttpServlet {	
	
	/*!
	 * Cette méthode redirige sur la JSP membre_add permettant d'ajouter un membre à la bibliothèque
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/membre_add.jsp");
		dispatcher.forward(request, response);
	}
	
	/*!
	 * Cette méthode redirige vers la JSP membre_details permettant d'observer les informations concernant le membre venant d'être créé
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreService membreService = MembreServiceImpl.getInstance();
		String inputNom = request.getParameter("nom");
		String inputPrenom = request.getParameter("prenom");
		String inputAdresse = request.getParameter("adresse");
		String inputEmail = request.getParameter("email");
		String inputTelephone = request.getParameter("telephone");
		
		String inputId ="-1";
		int id = -1;
		Membre newMembre;
		try {
			//Création du membre (les vérifications et modifications sont gérées par la couche "Service", et non par le servlet)
			//le choix de l'abonnement est géré par le dao
			id = membreService.create(inputNom, inputPrenom, inputAdresse, inputEmail, inputTelephone);
			inputId = String.valueOf(id);
			newMembre = membreService.getById(id);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			newMembre = new Membre();
		}
		
		request.setAttribute("id",inputId);
		request.setAttribute("membre", newMembre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/membre_details.jsp");
		dispatcher.forward(request, response);
	}
}