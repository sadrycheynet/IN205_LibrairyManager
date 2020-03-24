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

import com.excilys.librarymanager.model.Emprunt;

import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.MembreServiceImpl;
import com.excilys.librarymanager.service.LivreServiceImpl;
import com.excilys.librarymanager.service.EmpruntServiceImpl;

public class DashboardServlet extends HttpServlet {	
	/*
	 * Cette méthode redirige sur la JSP dashboard contenant les informations générales sur notre bibliothèque.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreService membreService = MembreServiceImpl.getInstance();
		LivreService livreService = LivreServiceImpl.getInstance();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		
		int nbMembres = -1;
		int nbLivres = -1;
		int nbEmprunts = -1;
		
		List<Emprunt> empruntsEnCours = new ArrayList<Emprunt>();
		try {
			nbMembres = membreService.count();
			nbLivres = livreService.count();
			nbEmprunts = empruntService.count();
			empruntsEnCours = empruntService.getListCurrent();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("nbMembres", nbMembres);
		request.setAttribute("nbLivres",nbLivres);
		request.setAttribute("nbEmprunts",nbEmprunts);
		request.setAttribute("empruntsEnCours",empruntsEnCours);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/dashboard.jsp");
		dispatcher.forward(request, response);
	}
}