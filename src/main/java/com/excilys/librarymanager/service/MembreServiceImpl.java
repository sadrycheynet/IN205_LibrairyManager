package com.excilys.librarymanager.service;

import java.util.List;

import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.model.Abonnement;

import com.excilys.librarymanager.dao.MembreDao;
import com.excilys.librarymanager.dao.MembreDaoImpl;

import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.EmpruntServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class MembreServiceImpl implements MembreService{
	/*!
	 * ATTRIBUTS
	 */
	private static MembreServiceImpl instance;
	
	/*!
	 * CONSTRUCTEUR
	 */
	private MembreServiceImpl(){}
	
	public static MembreServiceImpl getInstance() {
		if(instance==null) {
			instance = new MembreServiceImpl();
		}
		return instance;
	}
	
	/*!
	 *	REQUETES
	 */
	
	/*!
	 *	Permet de récupérer un membre par son id passé en paramètre.
	 *	@param id	Id du membre que l'on souhaite récupérer.
	 */
	@Override
	public Membre getById(int id) throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		Membre membre = new Membre();
		try {
			membre = membreDao.getById(id);
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
		return(membre);
	}
	
	/*!
	 *	Permet de récupérer la liste de tous les membres inscrits.
	 */
	@Override
	public List<Membre> getList() throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		List<Membre> membres = new ArrayList<Membre>();
		try{
			membres = membreDao.getList();
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return(membres);
	}
	
	/*!
	 *	Permet de récupérer la liste des membres pouvant encore emprunter des livres.
	 */
	@Override
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		
		List<Membre> membres = new ArrayList<Membre>();
		List<Membre> membresEmpruntPossible = new ArrayList<Membre>();
		try{
			membres = membreDao.getList();
			for(Membre m : membres){
				if(empruntService.isEmpruntPossible(m)){
					membresEmpruntPossible.add(m);
				}
			}
		}
		catch(DaoException e){
			System.out.println(e.getMessage());
		}
		
		return(membresEmpruntPossible);
	}
	
	/*!
	 *	Permet de créer un membre à partir des informations le concernant. Renvoie l'id du membre ainsi créé.
	 *	@param nom			Nom du membre à ajouter à la table.
	 *	@param prenom		Prénom du membre à ajouter à la table.
	 *	@param adresse		Adresse du membre à ajouter à la table.
	 *	@param email		Email du membre à ajouter à la table.
	 *	@param telephone	Téléphone du membre à ajouter à la table.
	 *
	 *	@return	Identifiant du membre créé
	 */	
	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException{
		int id = -1;
		
		///Vérifions que le membre dispose d'un nom et d'un prénom non-vide
		if(nom.isEmpty()||prenom.isEmpty()){
			/*Notons qu'on n'a pas précisé dans les specs si le prénom et le nom du membre devait contenir uniquement des caractères alphanumériques,
			traits-d'unions ou espaces*/
			throw new ServiceException("Les membres de peuvent pas avoir de prénom ou de nom vide !");
		}
		else{
			MembreDao membreDao = MembreDaoImpl.getInstance();
			try{
				//On passe le nom du membre en majuscules
				id = membreDao.create(nom.toUpperCase(), prenom, adresse, email, telephone);
			} catch(DaoException e){
				System.out.println(e.getMessage());
			}
		}
		
		return(id);
	}
	
	
	/*!
	 *	Met à jour les informations du membre passé en paramètre.
	 *	@param membre	Membre à modifier.
	 */
	@Override
	public void update(Membre membre) throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		String nom = membre.getNom();
		if(nom.isEmpty()||(membre.getPrenom()).isEmpty()){
			/*Notons qu'on n'a pas précisé dans les specs si le prénom et le nom du membre devait contenir uniquement des caractères alphanumériques,
			traits-d'unions ou espaces*/
			throw new ServiceException("Les membres de peuvent pas avoir de prénom ou de nom vide !");
		}
		else{
			try{
				membre.setNom(nom.toUpperCase());
				membreDao.update(membre);
			} catch(DaoException e){
				System.out.println(e.getMessage());
			}
		}
	}
	
	/*!
	 *	Supprime le membre dont l'id est passé en paramètre.
	 *	@param id	Id du membre à supprimer.
	 */
	@Override
	public void delete(int id) throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		try{
			membreDao.delete(id);
		} catch(DaoException e){
			System.out.println(e.getMessage());
		}
	}
	
	/*!
	 *	Renvoie le nombre de membres de notre librairie.
	 *	@return	Nombre de membres de notre librairie.
	 */
	@Override
	public int count() throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		int counter = -1;
		try{
			counter = membreDao.count();
		} catch(DaoException e){
			System.out.println(e.getMessage());
		}
		
		return(counter);
	}
}